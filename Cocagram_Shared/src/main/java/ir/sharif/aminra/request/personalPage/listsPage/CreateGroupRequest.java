package ir.sharif.aminra.request.personalPage.listsPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class CreateGroupRequest extends Request {

    private final String groupName;

    public CreateGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.createGroup(groupName);
    }
}
