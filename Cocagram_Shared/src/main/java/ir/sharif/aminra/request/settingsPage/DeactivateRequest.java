package ir.sharif.aminra.request.settingsPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class DeactivateRequest extends Request {
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.deactivate();
    }
}
