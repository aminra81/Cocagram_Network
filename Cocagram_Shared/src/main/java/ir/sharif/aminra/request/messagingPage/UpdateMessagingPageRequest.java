package ir.sharif.aminra.request.messagingPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class UpdateMessagingPageRequest extends Request {

    Integer chatId;
    boolean isChatChanged;

    public UpdateMessagingPageRequest(Integer chatId, boolean isChatChanged) {
        this.chatId = chatId;
        this.isChatChanged = isChatChanged;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateMessagingPage(chatId, isChatChanged);
    }
}
