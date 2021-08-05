package ir.sharif.aminra.request.messagingPage.messageSendingPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class SaveTweetRequest extends Request {
    private final Integer tweetId;

    public SaveTweetRequest(Integer tweetId) {
        this.tweetId = tweetId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.saveTweet(tweetId);
    }
}
