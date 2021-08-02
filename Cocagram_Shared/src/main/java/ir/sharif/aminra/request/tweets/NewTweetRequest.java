package ir.sharif.aminra.request.tweets;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class NewTweetRequest extends Request {
    String content;
    String avatarString;
    Integer upPost;

    public NewTweetRequest(String content, String avatarString, Integer upPost) {
        this.content = content;
        this.avatarString = avatarString;
        this.upPost = upPost;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.newTweet(content, avatarString, upPost);
    }
}
