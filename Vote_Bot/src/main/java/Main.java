import Vote.VoteManager;
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
import ir.sharif.aminra.request.messagingPage.messageSendingPage.SendMessageRequest;
import ir.sharif.aminra.response.messagingPage.messageSendingPage.NewMessageResponse;
import ir.sharif.aminra.util.Loop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private final String USERNAME = "vote_bot";
    private final String EMAIL = "vote@bot.com";
    private final String PASSWORD = "vote_bot";

    private final BotResponseSender botResponseSender;
    private final ClientHandler clientHandler;
    private final Loop loop = new Loop(0.5, this::processCommands);
    private final VoteManager voteManager = new VoteManager();


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
                    if (!chat.isGroup())
                        continue;

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
                        System.out.println(message);
                        String newMessage = voteManager.getResponse(chat.getId(), message.getContent(), message.getWriter());
                        if(newMessage == null)
                            newMessage = "error occurred";
                        botResponseSender.addRequest(new NewMessageRequest(null,
                                newMessage, null));
                        NewMessageResponse newMessageResponse = (NewMessageResponse) botResponseSender.getResponse();
                        botResponseSender.addRequest(new SendMessageRequest(newMessageResponse.getMessageId(),
                                new ArrayList<>(List.of(chat.getId())), new ArrayList<>()));
                        botResponseSender.getResponse();
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
