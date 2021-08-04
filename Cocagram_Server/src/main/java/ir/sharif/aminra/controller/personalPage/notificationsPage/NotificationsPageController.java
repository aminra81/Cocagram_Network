package ir.sharif.aminra.controller.personalPage.notificationsPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.viewModels.ViewUser;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.personalPage.notificationsPage.UpdateNotificationsPageResponse;
import ir.sharif.aminra.util.Config;

import java.util.ArrayList;
import java.util.List;

public class NotificationsPageController {

    private final ClientHandler clientHandler;

    public NotificationsPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        try {
            User user = clientHandler.getUser();
            List<String> requestMessages = user.getRequestNotifications();
            List<String> systemMessages = user.getNotifications();
            List<ViewUser> requests = new ArrayList<>();
            for (Integer id : user.getRequests()) {
                User currentUser = Connector.getInstance().fetch(User.class, id);
                requests.add(new ViewUser(currentUser.getUsername(), currentUser.getId(), currentUser.isActive()));
            }
            return new UpdateNotificationsPageResponse(requestMessages, systemMessages, requests);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
