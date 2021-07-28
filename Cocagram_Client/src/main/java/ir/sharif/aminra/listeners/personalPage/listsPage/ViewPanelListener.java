package ir.sharif.aminra.listeners.personalPage.listsPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.profileView.SwitchToProfilePageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewPanelListener {

    static private final Logger logger = LogManager.getLogger(ViewPanelListener.class);

    public void viewEventOccurred(Integer userToBeVisitedID) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.USER, userToBeVisitedID, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
