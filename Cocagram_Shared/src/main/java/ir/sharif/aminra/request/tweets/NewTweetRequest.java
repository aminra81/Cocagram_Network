package ir.sharif.aminra.request.tweets;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class NewTweetRequest extends Request {
    String content;
    byte[] avatarArray;
    Integer upPost;

    public NewTweetRequest(String content, byte[] avatarArray, Integer upPost) {
        this.content = content;
        this.avatarArray = avatarArray;
        this.upPost = upPost;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.newTweet(content, avatarArray, upPost);
    }
}
