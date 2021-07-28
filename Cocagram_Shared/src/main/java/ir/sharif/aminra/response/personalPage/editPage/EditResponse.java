package ir.sharif.aminra.response.personalPage.editPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class EditResponse extends Response {

    String error;

    public EditResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditResponse(error);
    }
}
