package ir.sharif.aminra.request.profileView;

import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class SwitchToProfilePageRequest extends Request {

    private final SwitchToProfileType switchToProfileType;
    private final Integer id;
    private final String username;

    public SwitchToProfilePageRequest(SwitchToProfileType switchToProfileType, Integer id, String username) {
        this.switchToProfileType = switchToProfileType;
        this.id = id;
        this.username = username;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.switchToProfilePage(switchToProfileType, id, username);
    }
}
