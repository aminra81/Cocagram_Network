package ir.sharif.aminra.response.personalPage.listsPage;

import ir.sharif.aminra.models.viewModels.ViewUser;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateGroupPageResponse extends Response {

    private final List<ViewUser> members;
    public UpdateGroupPageResponse(List<ViewUser> members) {
        this.members = members;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateGroupPage(members);
    }
}
