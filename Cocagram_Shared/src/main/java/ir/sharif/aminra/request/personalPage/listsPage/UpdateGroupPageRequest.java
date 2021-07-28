package ir.sharif.aminra.request.personalPage.listsPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class UpdateGroupPageRequest extends Request {
    Integer groupId;

    public UpdateGroupPageRequest(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateGroupPage(groupId);
    }
}
