package ir.sharif.aminra.listeners.personalPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.personalPage.editPage.SwitchToEditPageRequest;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.tweets.NewTweetFXController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyPageListener {

    static private final Logger logger = LogManager.getLogger(MyPageListener.class);

    public void stringEventOccurred(String event) {
        switch (event) {
            case "newTweet":
                Page newTweetPage = new Page("newTweetPage");
                ViewManager.getInstance().setPage(newTweetPage);
                NewTweetFXController newTweetFXController = (NewTweetFXController) newTweetPage.getFxController();
                newTweetFXController.setUpPost(null);
                break;
            case "edit":
                Request request = new SwitchToEditPageRequest();
                logger.info(String.format("client requested %s", request));
                Client.getClient().addRequest(request);
                break;
            case "notifications":
                ViewManager.getInstance().setPage(new Page("notificationsPage"));
                break;
            case "lists":
                ViewManager.getInstance().setPage(new Page("listsPage"));
                break;
            default:
                break;
        }
    }
}
