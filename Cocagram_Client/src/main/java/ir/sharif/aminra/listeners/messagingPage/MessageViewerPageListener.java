package ir.sharif.aminra.listeners.messagingPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.messagingPage.DeleteMessageRequest;
import ir.sharif.aminra.request.messagingPage.ForwardMessageRequest;
import ir.sharif.aminra.request.profileView.SwitchToProfilePageRequest;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.messagingPage.EditMessageFXController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageViewerPageListener {

    static private final Logger logger = LogManager.getLogger(MessageViewerPageListener.class);

    public void checkProfile(Integer messageID) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.MESSAGE, messageID, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void forward(Integer messageID) {
        Request request = new ForwardMessageRequest(messageID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void edit(Integer messageID) {
        Page page = new Page("editMessagePage");
        EditMessageFXController editMessageFXController = (EditMessageFXController) page.getFxController();
        editMessageFXController.setMessageId(messageID);
        ViewManager.getInstance().setPage(page);
    }

    public void delete(Integer messageID) {
        Request request = new DeleteMessageRequest(messageID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
