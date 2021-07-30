package ir.sharif.aminra.response.tweets;

import ir.sharif.aminra.models.viewModels.ViewTweet;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateTweetPageResponse extends Response {
    String tweetContent;
    String tweetDate;
    String retweetString;
    byte[] tweetImage;
    int likeNumbers;
    List<ViewTweet> viewTweetList;
    String likeButtonText;

    public UpdateTweetPageResponse(String tweetContent, String tweetDate, String retweetString, byte[] tweetImage,
                                   int likeNumbers, List<ViewTweet> viewTweetList, String likeButtonText) {
        this.tweetContent = tweetContent;
        this.tweetDate = tweetDate;
        this.retweetString = retweetString;
        this.tweetImage = tweetImage;
        this.likeNumbers = likeNumbers;
        this.viewTweetList = viewTweetList;
        this.likeButtonText = likeButtonText;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateTweetPage(tweetContent, tweetDate, retweetString, tweetImage, likeNumbers, viewTweetList,
                likeButtonText);
    }

}
