package ir.sharif.aminra.listeners.settingsPage;


import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.settingsPage.DeactivateRequest;
import ir.sharif.aminra.request.settingsPage.EditPrivacySettingsRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrivacySettingsPageListener {

    static private final Logger logger = LogManager.getLogger(PrivacySettingsPageListener.class);

    public void edit(String password, String lastSeen, boolean isPrivate) {
        Request request = new EditPrivacySettingsRequest(isPrivate, lastSeen, password);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void deactivate() {
        Request request = new DeactivateRequest();
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
