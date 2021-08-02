package ir.sharif.aminra.controller.personalPage.editPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.controller.DataValidator;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.database.ImageLoader;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.personalPage.editPage.EditResponse;
import ir.sharif.aminra.response.personalPage.editPage.SwitchToEditPageResponse;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;

public class EditPageController {

    static private final Logger logger = LogManager.getLogger(EditPageController.class);

    ClientHandler clientHandler;

    public EditPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getInfoToSwitch() {
        User user = clientHandler.getUser();
        return new SwitchToEditPageResponse(user.getUsername(), user.getFirstname(), user.getLastname(), user.getBio(),
                user.getBirthDate(), user.getEmail(), user.getPhoneNumber(), user.getLastSeenType(),
                user.isPrivate(), user.isPublicData());
    }

    public Response edit(String firstname, String lastname, String bio, LocalDate birthdate, String email,
                         String phoneNumber, String avatarString) {
        try {
            User user = clientHandler.getUser();
            String prevPhoneNumber = user.getPhoneNumber();
            String prevEmail = user.getEmail();

            user.setPhoneNumber("");
            user.setEmail("");
            Connector.getInstance().save(user);

            DataValidator editValidator = new DataValidator();

            String error = editValidator.validateFirstname(firstname);
            if (error.equals(""))
                error = editValidator.validateLastname(lastname);
            if (error.equals(""))
                error = editValidator.validateEmail(email);
            if (error.equals(""))
                error = editValidator.validatePhoneNumber(phoneNumber);

            if (!error.equals("")) {
                user.setEmail(prevEmail);
                user.setPhoneNumber(prevPhoneNumber);
                Connector.getInstance().save(user);
                logger.info(error);
                return new EditResponse(error);
            } else {
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setBio(bio);
                user.setBirthDate(birthdate);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);

                Integer imageID = null;
                if (avatarString != null) {
                    ImageUtils imageUtils = new ImageUtils();
                    try {
                        BufferedImage bufferedImage = imageUtils.toBufferedImage(avatarString);
                        ImageLoader imageLoader = new ImageLoader();
                        imageID = imageLoader.saveIntoDB(bufferedImage);
                    } catch (IOException e) {
                        logger.warn("can't convert byte array to buffered image");
                        e.printStackTrace();
                    }
                }
                if (imageID != null)
                    user.setAvatar(imageID);
                Connector.getInstance().save(user);
                logger.info(String.format("user %s edited his profile", user.getUsername()));
                return new EditResponse("");
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
