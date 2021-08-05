package ir.sharif.aminra.listeners.messagingPage.messageSendigPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.messagingPage.messageSendingPage.SendMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ChatSelectingPageListener {

    static private final Logger logger = LogManager.getLogger(ChatSelectingPageListener.class);

    public void send(Integer messageID, List<Integer> selectedChats, List<Integer> selectedGroups) {
        Request request = new SendMessageRequest(messageID, selectedChats, selectedGroups);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
