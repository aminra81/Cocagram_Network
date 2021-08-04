package ir.sharif.aminra.response.personalPage;

import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdatePersonalPageResponse extends Response {

    private final String avatarString;
    private final List<ViewTweet> viewTweetList;

    public UpdatePersonalPageResponse(String avatarString, List<ViewTweet> viewTweetList) {
        this.avatarString = avatarString;
        this.viewTweetList = viewTweetList;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updatePersonalPage(avatarString, viewTweetList);
    }
}
