package ir.sharif.aminra.request.tweets;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class UpdateTweetPageRequest extends Request {
    Integer tweetId;
    boolean myTweets;

    public UpdateTweetPageRequest(Integer tweetId, boolean myTweets) {
        this.tweetId = tweetId;
        this.myTweets = myTweets;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateTweetPage(tweetId, myTweets);
    }
}
