package ir.sharif.aminra.listeners.explorerPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.profileView.SwitchToProfilePageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorerListener {

    static private final Logger logger = LogManager.getLogger(ExplorerListener.class);

    public void search(String username) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.USERNAME, null, username);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

}
