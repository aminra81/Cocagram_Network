package ir.sharif.aminra.request.messagingPage.messageSendingPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.Getter;

public class NewMessageRequest extends Request {

    @Getter
    private final String avatarString;
    @Getter
    private final String messageContent;
    @Getter
    private final Integer receiverId;

    public NewMessageRequest(String avatarString, String messageContent, Integer receiverId) {
        this.avatarString = avatarString;
        this.messageContent = messageContent;
        this.receiverId = receiverId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.newMessage(avatarString, messageContent, receiverId);
    }

    @Override
    public String toString() {
        return "NewMessageRequest{" +
                "messageContent='" + messageContent + '\'' +
                ", receiverId=" + receiverId +
                '}';
    }
}
