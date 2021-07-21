package ir.sharif.aminra.request;

import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class LogoutRequest extends Request{
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.logout();
    }
}
