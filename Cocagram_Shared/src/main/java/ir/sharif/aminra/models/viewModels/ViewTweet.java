package ir.sharif.aminra.models.viewModels;
import ir.sharif.aminra.models.media.Tweet;
import lombok.Getter;

public class ViewTweet {
    @Getter
    String retweetString;
    @Getter
    Tweet tweet;
    @Getter
    boolean myTweets;

    public ViewTweet(String retweetString, Tweet tweet, boolean myTweets) {
        this.retweetString = retweetString;
        this.tweet = tweet;
        this.myTweets = myTweets;
    }
}
