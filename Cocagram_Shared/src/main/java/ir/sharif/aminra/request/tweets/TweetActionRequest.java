package ir.sharif.aminra.request.tweets;

import ir.sharif.aminra.models.events.TweetPageEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class TweetActionRequest extends Request {
    private final TweetPageEventType tweetPageEventType;
    private final Integer tweetId;

    public TweetActionRequest(TweetPageEventType tweetPageEventType, Integer tweetId) {
        this.tweetPageEventType = tweetPageEventType;
        this.tweetId = tweetId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.applyTweetAction(tweetPageEventType, tweetId);
    }
}
