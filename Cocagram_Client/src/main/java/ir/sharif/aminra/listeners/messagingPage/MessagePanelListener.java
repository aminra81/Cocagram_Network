package ir.sharif.aminra.listeners.messagingPage;


import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.messagingPage.MessageViewerFXController;

public class MessagePanelListener {

    public void view(Integer messageId) {
        Page page = new Page("messageViewerPage");
        MessageViewerFXController messageViewerFXController = (MessageViewerFXController) page.getFxController();
        messageViewerFXController.setMessageID(messageId);
        ViewManager.getInstance().setPage(page);
    }
}
