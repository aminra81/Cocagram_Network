package ir.sharif.aminra.listeners.profileView;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.ProfilePageEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.profileView.ProfileRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfilePageListener {

    static private final Logger logger = LogManager.getLogger(ProfilePageListener.class);

    public void messageHandle(Integer userToVeVisited) {
        Request request = new ProfileRequest(ProfilePageEventType.MESSAGE, userToVeVisited);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
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
