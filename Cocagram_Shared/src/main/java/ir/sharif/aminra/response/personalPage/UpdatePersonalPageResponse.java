package ir.sharif.aminra.response.personalPage;

import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdatePersonalPageResponse extends Response {

    byte[] avatarArray;
    List<ViewTweet> viewTweetList;

    public UpdatePersonalPageResponse(byte[] avatarArray, List<ViewTweet> viewTweetList) {
        this.avatarArray = avatarArray;
        this.viewTweetList = viewTweetList;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updatePersonalPage(avatarArray, viewTweetList);
    }
}
