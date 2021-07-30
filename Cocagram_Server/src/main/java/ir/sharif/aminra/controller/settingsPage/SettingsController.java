package ir.sharif.aminra.controller.settingsPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.LogoutResponse;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class SettingsController {
    static private final Logger logger = LogManager.getLogger(SettingsController.class);

    private final ClientHandler clientHandler;

    public SettingsController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response logout() {
        try {
            User user = clientHandler.getUser();
            logger.info(String.format("user %s logged out from the app.", user.getUsername()));
            user.setLastSeen(LocalDateTime.now());
            Connector.getInstance().save(user);
            clientHandler.setUser(null);
            return new LogoutResponse();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

}
