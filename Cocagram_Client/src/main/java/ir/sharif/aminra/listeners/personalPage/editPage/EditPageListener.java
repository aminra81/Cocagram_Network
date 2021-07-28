package ir.sharif.aminra.listeners.personalPage.editPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.personalPage.editPage.EditRequest;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;

public class EditPageListener {

    static private final Logger logger = LogManager.getLogger(EditPageListener.class);

    public void eventOccurred(String firstname, String lastname, String bio, LocalDate birthdate, String email,
                              String phoneNumber, BufferedImage avatar) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            byte[] avatarArray = imageUtils.toByteArray(avatar, "png");
            Request request = new EditRequest(firstname, lastname, bio, birthdate, email, phoneNumber, avatarArray);
            logger.info(String.format("client requested %s", request));
            Client.getClient().addRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("can't convert buffered image to byte array");
        }
    }
}
