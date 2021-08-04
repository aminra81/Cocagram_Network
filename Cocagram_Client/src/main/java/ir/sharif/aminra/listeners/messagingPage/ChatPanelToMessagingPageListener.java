package ir.sharif.aminra.listeners.messagingPage;


import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.messagingPage.UpdateMessagingPageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatPanelToMessagingPageListener {

    static private final Logger logger = LogManager.getLogger(ChatPanelToMessagingPageListener.class);

    public void viewChat(Integer chatID) {
        Request request = new UpdateMessagingPageRequest(chatID, true);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
