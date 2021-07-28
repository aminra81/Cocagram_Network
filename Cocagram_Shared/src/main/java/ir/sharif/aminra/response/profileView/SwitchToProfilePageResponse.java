package ir.sharif.aminra.response.profileView;

import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class SwitchToProfilePageResponse extends Response {
    SwitchToProfileType switchToProfileType;
    boolean exists;
    boolean mine;
    String error;
    Integer userToBeVisited;

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
