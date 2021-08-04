package ir.sharif.aminra.response.explorerPage;

import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateExplorerPageResponse extends Response {

    private final List<ViewTweet> viewTweetList;

    public UpdateExplorerPageResponse(List<ViewTweet> viewTweetList) {
        this.viewTweetList = viewTweetList;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateExplorerPage(viewTweetList);
    }
}
