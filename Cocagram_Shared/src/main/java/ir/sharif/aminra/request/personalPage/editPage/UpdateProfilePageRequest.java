package ir.sharif.aminra.request.personalPage.editPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class UpdateProfilePageRequest extends Request {
    private final Integer userToBeVisited;

    public UpdateProfilePageRequest(Integer userToBeVisited) {
        this.userToBeVisited = userToBeVisited;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateProfilePage(userToBeVisited);
    }
}
