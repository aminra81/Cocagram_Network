package ir.sharif.aminra.request.messagingPage.chatGroupPage;

import ir.sharif.aminra.models.events.ChatGroupEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.Getter;
import lombok.ToString;

@ToString
public class ChatGroupEditRequest extends Request {
    @Getter
    private final ChatGroupEventType chatGroupEventType;
    @Getter
    private final String groupName;
    @Getter
    private final String username;

    public ChatGroupEditRequest(ChatGroupEventType chatGroupEventType, String groupName, String username) {
        this.chatGroupEventType = chatGroupEventType;
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.chatGroupRequestHandle(chatGroupEventType, groupName, username);
    }
}
