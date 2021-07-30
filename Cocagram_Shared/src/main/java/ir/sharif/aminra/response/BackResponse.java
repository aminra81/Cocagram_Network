package ir.sharif.aminra.response;

public class BackResponse extends Response{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.back();
    }
}
