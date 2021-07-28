package ir.sharif.aminra.listeners.personalPage.notificationsPage;
import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.models.events.RequestAnswerType;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.personalPage.notificationsPage.FollowRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestHandlingListener {

    static private final Logger logger = LogManager.getLogger(RequestHandlingListener.class);

    public void eventOccurred(RequestAnswerType requestAnswerType, Integer userToBeVisited) {
        Request request = new FollowRequest(requestAnswerType, userToBeVisited);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
