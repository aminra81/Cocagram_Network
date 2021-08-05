package ir.sharif.aminra.request;

import ir.sharif.aminra.response.Response;
import lombok.Getter;
import lombok.ToString;

@ToString
public class LogoutRequest extends Request {

    @Getter
    private final boolean terminate;

    public LogoutRequest(boolean terminate) {
        this.terminate = terminate;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.logout(terminate);
    }
}
