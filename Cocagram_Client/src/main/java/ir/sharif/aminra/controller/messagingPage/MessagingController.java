package ir.sharif.aminra.controller.messagingPage;

import ir.sharif.aminra.models.viewModels.ViewChat;
import ir.sharif.aminra.models.viewModels.ViewMessage;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.messagingPage.ChatPanelFXController;
import ir.sharif.aminra.view.messagingPage.MessagePanelFXController;
import ir.sharif.aminra.view.messagingPage.MessagingFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessagingController {

    public void refresh(boolean isChanged, Integer chatId, String chatName, List<ViewChat> chats, List<ViewMessage> messages) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof MessagingFXController))
            return;
        MessagingFXController messagingFXController = (MessagingFXController) ViewManager.getInstance()
                .getCurPage().getFxController();

        Platform.runLater(() -> {
            VBox chatList = messagingFXController.getChatList();
            chatList.getChildren().clear();
            for (ViewChat chat : chats) {
                Page chatPanel = new Page("chatPanel");
                ChatPanelFXController chatPanelFXController = (ChatPanelFXController) chatPanel.getFxController();
                chatPanelFXController.setChatID(chat.getChatId());
                chatPanelFXController.setChatNameLabel(chat.getChatName());
                if (chat.getUnreadCount() > 0)
                    chatPanelFXController.setUnreadCountLabel(chat.getUnreadCount());
                messagingFXController.getChatList().getChildren().add(new AnchorPane(chatPanelFXController.getChatPanel()));
            }
        });


        //update messages
        if(chatId == null)
            return;
        if(!isChanged &&
                (messagingFXController.getSelectedChatID() == null || !messagingFXController.getSelectedChatID().equals(chatId)))
            return;
        Platform.runLater(() -> {
            messagingFXController.clear();
            messagingFXController.setSelectedChatID(chatId);
            messagingFXController.setChatNameLabel(chatName);

            if(messages == null)
                return;

            for (ViewMessage message : messages) {
                Page messagePanel = new Page("messagePanel");
                MessagePanelFXController messagePanelFXController = (MessagePanelFXController) messagePanel.getFxController();
                messagePanelFXController.setMessageID(message.getMessageId());
                messagePanelFXController.setMessageTextLabel(message.getMessageContent());
                messagePanelFXController.setMessageStateLabel(message.getMessageState());
                messagePanelFXController.setMessageDateLabel(message.getMessageDateTime().
                        format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                messagePanelFXController.setSenderLabel(message.getMessageSender());
                messagingFXController.getChatBox().getChildren().
                        add(new AnchorPane(messagePanelFXController.getMessagePanel()));
            }
            if (isChanged)
                messagingFXController.getScroll().setVvalue(messagingFXController.getScroll().getVmax());
        });
    }
}
