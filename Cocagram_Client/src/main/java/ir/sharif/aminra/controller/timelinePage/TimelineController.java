package ir.sharif.aminra.controller.timelinePage;

import ir.sharif.aminra.controller.tweets.TweetManager;
import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.timelinePage.TimelineFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class TimelineController {

    public void refresh(List<ViewTweet> viewTweetList) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof TimelineFXController))
            return;
        TimelineFXController timelineFXController = (TimelineFXController) ViewManager.getInstance().getCurPage().getFxController();
        //adding tweets to timeline page.
        Platform.runLater(() -> {
            timelineFXController.clear();
            for (ViewTweet viewTweet : viewTweetList)
                timelineFXController.getTweetBox().getChildren().add(new AnchorPane(TweetManager.getInstance().
                        makeTweetPanel(viewTweet.getRetweetString(), viewTweet.getTweetContent(),
                                viewTweet.getTweetDateTime(), viewTweet.getTweetId(), viewTweet.isMyTweets())));
        });
    }
}
