import calculator.Calculator;
import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.controller.messagingPage.MessagingController;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.database.ImageLoader;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.Chat;
import ir.sharif.aminra.models.ChatState;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.media.Message;
import ir.sharif.aminra.request.DeleteAccountRequest;
import ir.sharif.aminra.request.messagingPage.messageSendingPage.NewMessageRequest;
import ir.sharif.aminra.util.Loop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private final String USERNAME = "calculator_bot";
    private final String EMAIL = "calculator@bot.com";
    private final String PASSWORD = "calculator_bot";

    private final BotResponseSender botResponseSender;
    private final ClientHandler clientHandler;
    private final Loop loop = new Loop(0.5, this::processCommands);


    public Main() throws IOException, DatabaseDisconnectException {
        this.botResponseSender = new BotResponseSender();
        this.clientHandler = new ClientHandler(this.botResponseSender);
        this.clientHandler.start();
        addUser();
    }

    public void start()  { loop.start(); }

    private void processCommands() {
        synchronized (this) {
            try {
                User user = Connector.getInstance().getUserByUsername(USERNAME);
                for (Integer chatStateId : user.getChatStates()) {
                    ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
                    //collect unread messages.
                    List<Message> unreadMessages = new ArrayList<>();
                    Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
                    if (chat.isGroup() || chat.getUsers().size() == 1)
                        continue;
                    Integer receiverId = chat.getUsers().get(0);
                    if (user.getId().equals(receiverId))
                        receiverId = chat.getUsers().get(1);

                    for (Integer messageID : chat.getMessages()) {
                        Message message = Connector.getInstance().fetch(Message.class, messageID);
                        if (message.getDateTime().isAfter(chatState.getLastCheck()) && !message.getWriter().equals(user.getId()) &&
                                !message.isDeleted())
                            unreadMessages.add(message);
                    }
                    MessagingController messagingController = new MessagingController(clientHandler);
                    messagingController.updateChatLastCheck(user, chat.getId());
                    //answering messages
                    for (Message message : unreadMessages) {
                        Long answer = Calculator.solve(new StringBuilder(message.getContent()));
                        if(answer == null)
                            continue;;
                        String messageContent = String.valueOf(answer);
                        botResponseSender.addRequest(new NewMessageRequest(null, messageContent, receiverId));
                    }
                }
            } catch (DatabaseDisconnectException e) {
                e.printStackTrace();
            }
        }
    }

    private void addUser() throws DatabaseDisconnectException {
        if (Connector.getInstance().getUserByUsername(USERNAME) != null)
            return;
        User user = new User(USERNAME, "", "", "", null, EMAIL, "",
                PASSWORD, true, "Everyone", ImageLoader.DEFAULT_AVATAR_ID);
        Connector.getInstance().save(user);

        user.setLastSeen(null);
        Connector.getInstance().save(user);
        this.clientHandler.setUser(user);
    }

    public void remove() {
        synchronized (this) {
            loop.stop();
            botResponseSender.addRequest(new DeleteAccountRequest());
        }
    }
}
