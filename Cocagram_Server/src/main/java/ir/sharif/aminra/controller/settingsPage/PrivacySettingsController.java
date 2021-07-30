package ir.sharif.aminra.controller.settingsPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.settingsPage.SwitchToPrivacySettingsPageResponse;
import ir.sharif.aminra.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrivacySettingsController {
    static private final Logger logger = LogManager.getLogger(PrivacySettingsController.class);

    private final ClientHandler clientHandler;

    public PrivacySettingsController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getInfoToSwitch() {
        User user = clientHandler.getUser();
        return new SwitchToPrivacySettingsPageResponse(user.isPrivate(), user.getLastSeenType(), user.getPassword());
    }

    public Response deactivate() {
        try {
            User user = clientHandler.getUser();
            logger.info(String.format("user %s deactivated his/her account.", user.getUsername()));
            user.setActive(false);
            Connector.getInstance().save(user);

            SettingsController settingsController = new SettingsController(clientHandler);
            return settingsController.logout();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    public Response editPrivacySettingsResponse(boolean isPrivate, String lastSeenType, String password) {
        try {
            User user = clientHandler.getUser();
            logger.info(String.format("user %s changed his/her privacy settings", user.getUsername()));
            user.setPassword(password);
            user.setLastSeenType(lastSeenType);
            user.setPrivate(isPrivate);
            Connector.getInstance().save(user);
            SettingsController settingsController = new SettingsController(clientHandler);
            return settingsController.logout();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
