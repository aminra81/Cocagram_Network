package ir.sharif.aminra.listeners.tweets;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.models.events.TweetPageEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.profileView.SwitchToProfilePageRequest;
import ir.sharif.aminra.request.tweets.TweetActionRequest;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.tweets.NewTweetFXController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TweetPageListener {

    static private final Logger logger = LogManager.getLogger(TweetPageListener.class);

    public void addComment(Integer tweetID) {
        Page newTweetPage = new Page("newTweetPage");
        ViewManager.getInstance().setPage(newTweetPage);
        NewTweetFXController newTweetFXController = (NewTweetFXController) newTweetPage.getFxController();
        newTweetFXController.setUpPost(tweetID);
        newTweetFXController.setTitleLabel(Config.getConfig("tweets").
                getProperty(String.class, "commentTitle"));
    }

    public void checkProfile(Integer tweetID) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.TWEET, tweetID, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void like(Integer tweetID) {
        Request request = new TweetActionRequest(TweetPageEventType.LIKE, tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void report(Integer tweetID) {
        Request request = new TweetActionRequest(TweetPageEventType.REPORT_SPAM, tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void retweet(Integer tweetID) {
        Request request = new TweetActionRequest(TweetPageEventType.RETWEET, tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void save(Integer tweetID) {

    }

    public void forward(Integer tweetID) {
    }
}
