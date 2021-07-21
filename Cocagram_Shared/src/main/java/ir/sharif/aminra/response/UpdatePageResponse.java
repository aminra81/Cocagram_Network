package ir.sharif.aminra.response;

public class UpdatePageResponse extends Response{

    String pageName;
    Object[] arguments;
    public UpdatePageResponse(String pageName, Object... args) {
        this.pageName = pageName;
        arguments = args.clone();
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updatePage(pageName, arguments);
    }
}
