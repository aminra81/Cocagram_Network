package ir.sharif.aminra.request.messagingPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class ForwardMessageRequest extends Request {
    private final Integer messageId;

    public ForwardMessageRequest(Integer messageId) {
        this.messageId = messageId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.forwardMessage(messageId);
    }
}
