package ir.sharif.aminra.response;

public class ShowErrorResponse extends Response{
    String message;

    public ShowErrorResponse(String message) {
        this.message = message;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) { responseVisitor.showError(message); }
}
