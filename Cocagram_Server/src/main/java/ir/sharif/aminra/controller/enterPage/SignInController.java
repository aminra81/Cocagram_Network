package ir.sharif.aminra.controller.enterPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.enterPage.EnterResponse;
import ir.sharif.aminra.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignInController {

    static private final Logger logger = LogManager.getLogger(SignUpController.class);

    private final ClientHandler clientHandler;

    public SignInController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response login(String username, String password) {
        try {
            Config errorsConfig = Config.getConfig("signInPage");
            User user = Connector.getInstance().getUserByUsername(username);
            if (user == null) {
                logger.info(errorsConfig.getProperty("noUserError"));
                return new EnterResponse(false, errorsConfig.getProperty("noUserError"));
            }
            if (!user.getPassword().equals(password)) {
                logger.info(errorsConfig.getProperty("passNotMatch"));
                return new EnterResponse(false, errorsConfig.getProperty("passNotMatch"));
            }
            user.setLastSeen(null);
            user.setActive(true);
            Connector.getInstance().save(user);

            clientHandler.setUser(user);

            logger.info(String.format("user %s signed in.", user.getUsername()));
            return new EnterResponse(true, "");
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
