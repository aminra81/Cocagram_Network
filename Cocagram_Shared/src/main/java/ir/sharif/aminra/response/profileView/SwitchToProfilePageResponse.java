package ir.sharif.aminra.response.profileView;

import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class SwitchToProfilePageResponse extends Response {
    private final SwitchToProfileType switchToProfileType;
    private final boolean exists;
    private final boolean mine;
    private final String error;
    private final Integer userToBeVisited;

    public SwitchToProfilePageResponse(SwitchToProfileType switchToProfileType, boolean exists, boolean mine, String error,
                                       Integer userToBeVisited) {
        this.switchToProfileType = switchToProfileType;
        this.exists = exists;
        this.mine = mine;
        this.error = error;
        this.userToBeVisited = userToBeVisited;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.switchToProfilePage(switchToProfileType, exists, mine, error, userToBeVisited);
    }
}
