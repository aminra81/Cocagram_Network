package ir.sharif.aminra.controller.explorerPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.controller.tweets.TweetManager;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.media.Tweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.explorerPage.UpdateExplorerPageResponse;
import ir.sharif.aminra.util.Config;

import java.util.ArrayList;
import java.util.List;

public class ExplorerController {
    private final ClientHandler clientHandler;

    public ExplorerController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        try {
            User user = clientHandler.getUser();
            //getting valid tweets
            List<Tweet> tweets = Connector.getInstance().fetchAll(Tweet.class);
            List<Tweet> publicTweets = new ArrayList<>();
            for (Tweet tweet : tweets)
                if (!Connector.getInstance().fetch(User.class, tweet.getWriter()).isPrivate())
                    publicTweets.add(tweet);

            List<Tweet> validatedTweets = TweetManager.getInstance().validation(user, publicTweets, true);
            Tweet.sortByLikeNumbers(validatedTweets);
            return new UpdateExplorerPageResponse(TweetManager.getInstance().getViewTweets(user, validatedTweets, false));
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
