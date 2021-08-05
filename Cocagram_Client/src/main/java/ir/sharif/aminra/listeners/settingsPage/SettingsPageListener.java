package ir.sharif.aminra.listeners.settingsPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.LogoutRequest;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.settingsPage.SwitchToPrivacySettingsPageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettingsPageListener {

    static private final Logger logger = LogManager.getLogger(SettingsPageListener.class);

    public void stringEventOccurred(String event) {
        Request request;
        switch (event) {
            case "deleteAccount":
                //TODO adding delete account feature.
                break;
            case "logout":
                request = new LogoutRequest(false);
                logger.info(String.format("client requested %s", request));
                Client.getClient().addRequest(request);
                break;
            case "privacySettings":
                request = new SwitchToPrivacySettingsPageRequest();
                logger.info(String.format("client requested %s", request));
                Client.getClient().addRequest(request);
                break;
        }
    }
}
