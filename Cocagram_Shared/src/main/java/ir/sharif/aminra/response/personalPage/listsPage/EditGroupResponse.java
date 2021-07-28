package ir.sharif.aminra.response.personalPage.listsPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class EditGroupResponse extends Response {
    String error;

    public EditGroupResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditGroupResponse(error);
    }
}
