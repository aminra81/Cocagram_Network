package ir.sharif.aminra.listeners.messagingPage.chatGroupPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.ChatGroupEventType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.messagingPage.chatGroupPage.ChatGroupEditRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatGroupPageListener {

    static private final Logger logger = LogManager.getLogger(ChatGroupPageListener.class);

    public void addUser(String groupName, String username) {
        Request request = new ChatGroupEditRequest(ChatGroupEventType.ADD, groupName, username);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void createGroup(String groupName) {
        Request request = new ChatGroupEditRequest(ChatGroupEventType.CREATE, groupName, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void removeGroup(String groupName) {
        Request request = new ChatGroupEditRequest(ChatGroupEventType.REMOVE, groupName, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
