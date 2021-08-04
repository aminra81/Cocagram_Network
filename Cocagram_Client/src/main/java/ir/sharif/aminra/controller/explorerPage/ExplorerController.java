package ir.sharif.aminra.controller.explorerPage;

import ir.sharif.aminra.controller.tweets.TweetManager;
import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.explorerPage.ExplorerFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class ExplorerController {

    public void refresh(List<ViewTweet> viewTweetList) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof ExplorerFXController))
            return;
        ExplorerFXController explorerFXController = (ExplorerFXController) ViewManager.getInstance().getCurPage().getFxController();
        //adding tweets to timeline page.
        Platform.runLater(() -> {
            explorerFXController.clear();
            for (ViewTweet viewTweet : viewTweetList)
                explorerFXController.getTweetBox().getChildren().add(new AnchorPane(TweetManager.getInstance().
                        makeTweetPanel(viewTweet.getRetweetString(), viewTweet.getTweetContent(), viewTweet.getTweetDateTime(),
                                viewTweet.getTweetId(), viewTweet.isMyTweets())));
        });
    }
}
