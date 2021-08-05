package ir.sharif.aminra.response.messagingPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.time.LocalDateTime;

public class UpdateMessageViewerResponse extends Response {
    private final boolean deactivated;
    private final String messageImage;
    private final String messageContent;
    private final LocalDateTime messageDateTime;
    private final String messageSender;

    public UpdateMessageViewerResponse(boolean deactivated, String messageImage, String messageContent,
                                       LocalDateTime messageDateTime, String messageSender) {
        this.deactivated = deactivated;
        this.messageImage = messageImage;
        this.messageContent = messageContent;
        this.messageDateTime = messageDateTime;
        this.messageSender = messageSender;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateMessageViewerPage(deactivated, messageImage, messageContent, messageDateTime, messageSender);
    }
}
