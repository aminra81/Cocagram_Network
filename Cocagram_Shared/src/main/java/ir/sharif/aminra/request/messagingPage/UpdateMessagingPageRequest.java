package ir.sharif.aminra.request.messagingPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;


@ToString
public class UpdateMessagingPageRequest extends Request {

    private final Integer chatId;
    private final boolean isChanged;

    public UpdateMessagingPageRequest(Integer chatId, boolean isChanged) {
        this.chatId = chatId;
        this.isChanged = isChanged;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateMessagingPage(chatId, isChanged);
    }
}
