package ir.sharif.aminra.response.timelinePage;

import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateTimelinePageResponse extends Response {
    private final List<ViewTweet> viewTweetList;

    public UpdateTimelinePageResponse(List<ViewTweet> viewTweetList) {
        this.viewTweetList = viewTweetList;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateTimelinePage(viewTweetList);
    }
}
