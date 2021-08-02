package ir.sharif.aminra.controller.tweets;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.database.ImageLoader;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.media.Tweet;
import ir.sharif.aminra.response.BackResponse;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class NewTweetController {
    static private final Logger logger = LogManager.getLogger(NewTweetController.class);

    private final ClientHandler clientHandler;

    public NewTweetController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response addTweet(String content, String avatarString, Integer upPost) {
        try {
            User user = clientHandler.getUser();
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
            Tweet curTweet = new Tweet(content, user.getId(), upPost, imageID);
            Connector.getInstance().save(curTweet);

            user.addToTweets(curTweet.getId());
            Connector.getInstance().save(user);
            if (upPost != null) {
                Tweet upTweet = Connector.getInstance().fetch(Tweet.class, upPost);
                upTweet.addComment(curTweet.getId());
                Connector.getInstance().save(upTweet);
            }
            logger.info(String.format("user %s added tweet %s", user.getUsername(), curTweet.getId()));
            return new BackResponse();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
