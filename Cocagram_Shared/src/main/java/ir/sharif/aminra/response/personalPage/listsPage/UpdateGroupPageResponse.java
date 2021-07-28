package ir.sharif.aminra.response.personalPage.listsPage;

import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateGroupPageResponse extends Response {

    List<User> members;
    public UpdateGroupPageResponse(List<User> members) {
        this.members = members;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateGroupPage(members);
    }
}
