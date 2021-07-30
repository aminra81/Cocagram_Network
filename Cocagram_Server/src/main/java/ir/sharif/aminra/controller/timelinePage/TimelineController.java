package ir.sharif.aminra.controller.timelinePage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.controller.tweets.TweetManager;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.media.Tweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.timelinePage.UpdateTimelinePageResponse;
import ir.sharif.aminra.util.Config;

import java.util.ArrayList;
import java.util.List;

public class TimelineController {
    private final ClientHandler clientHandler;

    public TimelineController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        try {
            User user = clientHandler.getUser();
            //getting valid tweets
            List<Tweet> tweets = new ArrayList<>();
            for (Integer followingID : user.getFollowings()) {
                for (Integer tweetID : Connector.getInstance().fetch(User.class, followingID).getTweets())
                    tweets.add(Connector.getInstance().fetch(Tweet.class, tweetID));
                for (Integer tweetID : Connector.getInstance().fetch(User.class, followingID).getLikedTweets())
                    tweets.add(Connector.getInstance().fetch(Tweet.class, tweetID));
            }
            List<Tweet> validatedTweets = TweetManager.getInstance().validation(user, tweets, true);
            Tweet.sortByLikeNumbers(validatedTweets);
            return new UpdateTimelinePageResponse(TweetManager.getInstance().getViewTweets(user, validatedTweets, false));
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
