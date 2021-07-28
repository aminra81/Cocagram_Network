package ir.sharif.aminra.response.personalPage.listsPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class CreateGroupResponse extends Response {
    String error;

    public CreateGroupResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyCreateGroupResponse(error);
    }
}
