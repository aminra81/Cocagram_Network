package ir.sharif.aminra.request.personalPage.notificationsPage;

import ir.sharif.aminra.models.events.RequestAnswerType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class FollowRequest extends Request {
    RequestAnswerType requestAnswerType;
    Integer requesterID;

    public FollowRequest(RequestAnswerType requestAnswerType, Integer requesterID) {
        this.requestAnswerType = requestAnswerType;
        this.requesterID = requesterID;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.followRequestHandle(requestAnswerType, requesterID);
    }
}
