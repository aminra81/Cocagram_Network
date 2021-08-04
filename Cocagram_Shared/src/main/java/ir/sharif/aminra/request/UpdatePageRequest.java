package ir.sharif.aminra.request;

import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class UpdatePageRequest extends Request{
    private final String pageName;

    public UpdatePageRequest(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updatePage(pageName);
    }
}
