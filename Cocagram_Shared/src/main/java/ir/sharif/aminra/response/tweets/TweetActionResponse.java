package ir.sharif.aminra.response.tweets;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class TweetActionResponse extends Response {
    String verdict;
    boolean isError;

    public TweetActionResponse(String verdict, boolean isError) {
        this.verdict = verdict;
        this.isError = isError;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyTweetActionResponse(verdict, isError);
    }
}
