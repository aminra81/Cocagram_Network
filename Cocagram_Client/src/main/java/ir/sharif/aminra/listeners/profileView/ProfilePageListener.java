package ir.sharif.aminra.listeners.profileView;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.ProfilePageEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.profileView.ProfileRequest;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.messagingPage.messageSendingPage.MessageSendingFXController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfilePageListener {

    static private final Logger logger = LogManager.getLogger(ProfilePageListener.class);

    public void messageHandle(Integer userToVeVisited) {
        Page page = new Page("messageSendingPage");
        MessageSendingFXController messageSendingFXController = (MessageSendingFXController) page.getFxController();
        messageSendingFXController.setReceiverID(userToVeVisited);
        ViewManager.getInstance().setPage(page);
    }

    public void muteHandle(Integer userToVeVisited) {
        Request request = new ProfileRequest(ProfilePageEventType.MUTE, userToVeVisited);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void followHandle(Integer userToVeVisited) {
        Request request = new ProfileRequest(ProfilePageEventType.FOLLOW, userToVeVisited);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void blockHandle(Integer userToVeVisited) {
        Request request = new ProfileRequest(ProfilePageEventType.BLOCK, userToVeVisited);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
