package ir.sharif.aminra.listeners.messagingPage;

import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.messagingPage.messageSendingPage.MessageSendingFXController;

public class MessagingPageListener {

    public void stringEventOccurred(String event) {
        switch (event) {
            case "sendMessage":
                Page page = new Page("messageSendingPage");
                MessageSendingFXController messageSendingFXController = (MessageSendingFXController) page.getFxController();
                messageSendingFXController.setReceiverID(null);
                ViewManager.getInstance().setPage(page);
                break;
            case "chatGroup":
                ViewManager.getInstance().setPage(new Page("chatGroupPage"));
                break;
            default:
                break;
        }
    }
}
