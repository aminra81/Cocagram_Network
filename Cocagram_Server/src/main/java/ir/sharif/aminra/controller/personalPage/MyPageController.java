package ir.sharif.aminra.controller.personalPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.controller.tweets.TweetManager;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.database.ImageLoader;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.media.Tweet;
import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.personalPage.UpdatePersonalPageResponse;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyPageController {

    static private final Logger logger = LogManager.getLogger(MyPageController.class);

    private final ClientHandler clientHandler;

    public MyPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        User user =  clientHandler.getUser();

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage bufferedImage = imageLoader.getByID(user.getAvatar());

        ImageUtils imageUtils = new ImageUtils();
        try {
            return new UpdatePersonalPageResponse(imageUtils.toString(bufferedImage, "png"), getViewTweets(user));
        } catch (IOException e) {
            logger.warn("can't convert buffered image to byte array");
            e.printStackTrace();
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private List<ViewTweet> getViewTweets(User user) throws DatabaseDisconnectException {
        List<Tweet> tweets = new ArrayList<>();
        for (Integer tweetID : user.getTweets()) {
            Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetID);
            if(tweet.getSpamReports() > Config.getConfig("tweets").getProperty(Integer.class, "maxSpam")) //spam
                continue;
            tweets.add(tweet);
        }
        Tweet.sortByDateTime(tweets);
        return TweetManager.getInstance().getViewTweets(user, tweets, true);
    }
}
