package ir.sharif.aminra.response.personalPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class UpdatePersonalPageResponse extends Response {

    byte[] avatarArray;

    public UpdatePersonalPageResponse(byte[] avatarArray) {
        this.avatarArray = avatarArray;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updatePersonalPage(avatarArray);
    }
}
