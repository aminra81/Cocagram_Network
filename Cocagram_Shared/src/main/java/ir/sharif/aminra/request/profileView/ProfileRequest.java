package ir.sharif.aminra.request.profileView;

import ir.sharif.aminra.models.events.ProfilePageEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class ProfileRequest extends Request {
    ProfilePageEventType profilePageEventType;
    Integer userToBeVisited;

    public ProfileRequest(ProfilePageEventType profilePageEventType, Integer userToBeVisited) {
        this.profilePageEventType = profilePageEventType;
        this.userToBeVisited = userToBeVisited;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.profileHandle(profilePageEventType, userToBeVisited);
    }
}
