package ir.sharif.aminra.response.messagingPage.messageSendingPage;

import ir.sharif.aminra.models.viewModels.ViewChat;
import ir.sharif.aminra.models.viewModels.ViewGroup;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;
import lombok.Getter;

import java.util.List;

public class NewMessageResponse extends Response {

    @Getter
    private final String error;
    @Getter
    private final Integer messageId;
    @Getter
    private final List<ViewGroup> groups;
    @Getter
    private final List<ViewChat> chats;

    public NewMessageResponse(String error, Integer messageId, List<ViewGroup> groups, List<ViewChat> chats) {
        this.error = error;
        this.messageId = messageId;
        this.groups = groups;
        this.chats = chats;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyNewMessageResponse(error, messageId, groups, chats);
    }
}
