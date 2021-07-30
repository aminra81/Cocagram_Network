package ir.sharif.aminra.controller.tweets;

import ir.sharif.aminra.models.media.Tweet;
import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.util.ImageUtils;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.tweets.TweetFXController;
import ir.sharif.aminra.view.tweets.TweetPanelFXController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TweetManager {

    private static TweetManager instance;
    static private final Logger logger = LogManager.getLogger(TweetManager.class);

    public static TweetManager getInstance() {
        if (instance == null)
            instance = new TweetManager();
        return instance;
    }

    public AnchorPane makeTweetPanel(String retweetString, Tweet tweet, boolean myTweets) {
        Page tweetPanel = new Page("tweetPanel");
        TweetPanelFXController tweetPanelFXController = (TweetPanelFXController) tweetPanel.getFxController();

        tweetPanelFXController.setTweetContent(tweet.getContent());
        tweetPanelFXController.setTweetDate(tweet.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        tweetPanelFXController.setMyTweets(myTweets);
        tweetPanelFXController.setRetweetLabel(retweetString);
        tweetPanelFXController.setTweetID(tweet.getId());

        return tweetPanelFXController.getTweetPanel();
    }

    public void refresh(String tweetContent, String tweetDate, String retweetString, byte[] tweetImage, int likeNumbers,
                        List<ViewTweet> viewTweetList, String likeButtonText) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof TweetFXController))
            return;
        TweetFXController tweetFXController = (TweetFXController) ViewManager.getInstance().getCurPage().getFxController();
        ImageUtils imageUtils = new ImageUtils();
        BufferedImage image = null;
        try {
            image = imageUtils.toBufferedImage(tweetImage);
        } catch (IOException e) {
            logger.warn("can't convert byte array to buffered image");
            e.printStackTrace();
        }
        if (image != null)
            tweetFXController.getPhotoBox().setFill(new ImagePattern(SwingFXUtils.toFXImage(image, null)));

        Platform.runLater(() -> {
            tweetFXController.clear();
            tweetFXController.setTweetContent(tweetContent);
            tweetFXController.setTweetDate(tweetDate);
            tweetFXController.setRetweetLabel(retweetString);
            tweetFXController.setLikesCounter(likeNumbers);
            for (ViewTweet viewTweet : viewTweetList)
                tweetFXController.getCommentsBox().getChildren().add(new AnchorPane(TweetManager.getInstance().
                        makeTweetPanel(viewTweet.getRetweetString(), viewTweet.getTweet(), viewTweet.isMyTweets())));
            tweetFXController.setLikeButtonText(likeButtonText);
        });
    }

    public void applyTweetActionResponse(String verdict, boolean isError) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof TweetFXController))
            return;
        TweetFXController tweetFXController = (TweetFXController) ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> tweetFXController.setVerdictLabelText(verdict, isError));
    }
}
