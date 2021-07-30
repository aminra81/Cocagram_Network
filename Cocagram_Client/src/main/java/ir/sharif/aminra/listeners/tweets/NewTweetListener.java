package ir.sharif.aminra.listeners.tweets;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.tweets.NewTweetRequest;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class NewTweetListener {

    static private final Logger logger = LogManager.getLogger(NewTweetListener.class);

    public void eventOccurred(String content, BufferedImage tweetImage, Integer upPost) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            byte[] avatarArray = imageUtils.toByteArray(tweetImage, "png");
            Request request = new NewTweetRequest(content, avatarArray, upPost);
            logger.info(String.format("client requested %s", request));
            Client.getClient().addRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("can't convert buffered image to byte array");
        }
    }
}
