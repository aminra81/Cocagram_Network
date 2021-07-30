package ir.sharif.aminra.request.personalPage.listsPage;

import ir.sharif.aminra.models.events.GroupPageEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class EditGroupRequest extends Request {
    GroupPageEventType groupPageEventType;
    Integer group;
    String username;

    public EditGroupRequest(GroupPageEventType groupPageEventType, Integer group, String username) {
        this.groupPageEventType = groupPageEventType;
        this.group = group;
        this.username = username;
    }


    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.editGroup(groupPageEventType, group, username);
    }
}
