package ir.sharif.aminra.listeners.messagingPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.messagingPage.EditMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditMessagePageListener {
    static private final Logger logger = LogManager.getLogger(EditMessagePageListener.class);

    public void edit(Integer messageId, String messageContent) {
        Request request = new EditMessageRequest(messageId, messageContent);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
