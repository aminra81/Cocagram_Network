package ir.sharif.aminra.response.personalPage.notificationsPage;

import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateNotificationsPageResponse extends Response {
    List<String> requestMessages;
    List<String> systemMessages;
    List<User> requests;

    public UpdateNotificationsPageResponse(List<String> requestMessages, List<String> systemMessages, List<User> requests) {
        this.requestMessages = requestMessages;
        this.systemMessages = systemMessages;
        this.requests = requests;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateNotificationsPage(requestMessages, systemMessages, requests);
    }
}
