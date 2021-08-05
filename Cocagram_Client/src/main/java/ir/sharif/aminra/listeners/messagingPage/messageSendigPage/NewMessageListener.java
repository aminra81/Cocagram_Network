package ir.sharif.aminra.listeners.messagingPage.messageSendigPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.messagingPage.messageSendingPage.NewMessageRequest;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class NewMessageListener {

    static private final Logger logger = LogManager.getLogger(NewMessageListener.class);

    public void send(BufferedImage messageImage, String messageContent, Integer receiverID) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            String avatarString = imageUtils.toString(messageImage, "png");
            Request request = new NewMessageRequest(avatarString, messageContent, receiverID);
            logger.info(String.format("client requested %s", request));
            Client.getClient().addRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("can't convert buffered image to byte array");
        }
    }
}
