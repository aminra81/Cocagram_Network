package ir.sharif.aminra.response.messagingPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class EditMessageResponse extends Response {
    private final String error;

    public EditMessageResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditMessageResponse(error);
    }
}
