package ir.sharif.aminra.controller.enterPage;
import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.controller.DataValidator;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.database.ImageLoader;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.Chat;
import ir.sharif.aminra.models.ChatState;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.enterPage.EnterResponse;
import ir.sharif.aminra.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class SignUpController {

    private final ClientHandler clientHandler;

    public SignUpController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    static private final Logger logger = LogManager.getLogger(SignUpController.class);

    public Response register(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                             String email, String phoneNumber, String password, boolean publicData, String lastSeenType) {
        try {
            String error;
            DataValidator signUpValidator = new DataValidator();

            error = signUpValidator.validateUsername(username);

            if (error.equals(""))
                error = signUpValidator.validateFirstname(firstname);
            if (error.equals(""))
                error = signUpValidator.validateLastname(lastname);
            if (error.equals(""))
                error = signUpValidator.validatePassword(password);
            if (error.equals(""))
                error = signUpValidator.validateEmail(email);
            if (error.equals(""))
                error = signUpValidator.validatePhoneNumber(phoneNumber);

            if (!error.equals("")) {
                logger.info(error);
                return new EnterResponse(false, error);
            } else {

                Chat savedMessageChat = new Chat(Config.getConfig("messagingPage").getProperty("savedMessages"),
                        false);
                Connector.getInstance().save(savedMessageChat);

                User user = new User(username, firstname, lastname, bio, birthDate, email,
                        phoneNumber, password, publicData, lastSeenType, ImageLoader.DEFAULT_AVATAR_ID);
                Connector.getInstance().save(user);

                savedMessageChat.addUser(user.getId());
                Connector.getInstance().save(savedMessageChat);

                ChatState chatState = new ChatState(savedMessageChat.getId());
                Connector.getInstance().save(chatState);

                user.addChatState(chatState.getId());
                user.setLastSeen(null);
                Connector.getInstance().save(user);
                clientHandler.setUser(user);

                logger.info(String.format("user %s registered.", username));
                return new EnterResponse(true, "");
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
