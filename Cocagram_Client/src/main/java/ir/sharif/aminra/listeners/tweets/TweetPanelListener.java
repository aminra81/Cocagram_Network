package ir.sharif.aminra.listeners.tweets;

import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.tweets.TweetFXController;

public class TweetPanelListener {
    public void eventOccurred(Integer tweetID, boolean myTweets) {
        Page tweetPage = new Page("tweetPage");
        TweetFXController tweetFXController = (TweetFXController) tweetPage.getFxController();

        tweetFXController.setTweetID(tweetID);
        tweetFXController.setMyTweets(myTweets);

        ViewManager.getInstance().setPage(tweetPage);
    }
}
