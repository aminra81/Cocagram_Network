package ir.sharif.aminra.request.tweets;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class UpdateTweetPageRequest extends Request {
    private final Integer tweetId;
    private final boolean myTweets;

    public UpdateTweetPageRequest(Integer tweetId, boolean myTweets) {
        this.tweetId = tweetId;
        this.myTweets = myTweets;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateTweetPage(tweetId, myTweets);
    }
}
