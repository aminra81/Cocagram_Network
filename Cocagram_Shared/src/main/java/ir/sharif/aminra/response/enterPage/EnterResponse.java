package ir.sharif.aminra.response.enterPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class EnterResponse extends Response {
    boolean success;
    String message;

    public EnterResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.enter(success, message);
    }

    public boolean getSuccess() { return success; }
}
