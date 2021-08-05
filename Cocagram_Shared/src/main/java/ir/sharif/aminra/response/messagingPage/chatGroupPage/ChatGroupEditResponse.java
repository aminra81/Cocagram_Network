package ir.sharif.aminra.response.messagingPage.chatGroupPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;
import lombok.Getter;

public class ChatGroupEditResponse extends Response {

    @Getter
    private final String error;

    public ChatGroupEditResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditChatGroupResponse(error);
    }
}
