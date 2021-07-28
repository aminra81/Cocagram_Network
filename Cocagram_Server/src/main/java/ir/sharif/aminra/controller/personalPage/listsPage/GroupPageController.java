package ir.sharif.aminra.controller.personalPage.listsPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.Group;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.events.GroupPageEventType;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.personalPage.listsPage.EditGroupResponse;
import ir.sharif.aminra.response.personalPage.listsPage.UpdateGroupPageResponse;
import ir.sharif.aminra.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GroupPageController {
    private final ClientHandler clientHandler;
    static private final Logger logger = LogManager.getLogger(GroupPageController.class);

    public GroupPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate(Integer groupId) {
        try {
            Group group = Connector.getInstance().fetch(Group.class, groupId);
            if(group == null)
                return null;
            return new UpdateGroupPageResponse(getUserList(group.getUsers()));
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private List<User> getUserList(List<Integer> idList) throws DatabaseDisconnectException {
        List<User> userList = new ArrayList<>();
        for (Integer id : idList)
            userList.add(Connector.getInstance().fetch(User.class, id));
        return userList;
    }

    public Response getEditResponse(GroupPageEventType groupPageEventType, Integer group, String username) {
        switch (groupPageEventType) {
            case ADD_USER:
                return addUser(group, username);
            case REMOVE_USER:
                return removeUser(group, username);
            case REMOVE_GROUP:
                return removeGroup(group);
        }
        return null;
    }

    private Response removeGroup(Integer groupId) {
        try {
            User user = clientHandler.getUser();
            Group group = Connector.getInstance().fetch(Group.class, groupId);

            user.removeGroup(groupId);
            Connector.getInstance().save(user);

            Connector.getInstance().delete(group);

            logger.info(String.format("user %s removed group %s", user.getUsername(), group));
            return new EditGroupResponse("");
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private Response removeUser(Integer groupId, String username) {
        try {
            User user = clientHandler.getUser();
            Group group = Connector.getInstance().fetch(Group.class, groupId);
            User currentUser = Connector.getInstance().getUserByUsername(username);
            Config errorsConfig = Config.getConfig("groupPage");

            if (currentUser == null || !currentUser.isActive()) {
                logger.info(String.format("user %s wants to remove a user which doesn't exist.",
                        user.getUsername()));
                return new EditGroupResponse(errorsConfig.getProperty("notExistedUserError"));
            } else if (!group.getUsers().contains(currentUser.getId())) {
                logger.info(String.format("user %s wants to remove a user which doesn't exist from group.",
                        user.getUsername()));
                return new EditGroupResponse(errorsConfig.getProperty("userNotInGroupError"));
            } else {
                group.removeUser(currentUser.getId());
                Connector.getInstance().save(group);
                return new EditGroupResponse("");
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private Response addUser(Integer groupId, String username) {
        try {
            User user = clientHandler.getUser();
            Group group = Connector.getInstance().fetch(Group.class, groupId);
            User currentUser = Connector.getInstance().getUserByUsername(username);
            Config errorsConfig = Config.getConfig("groupPage");

            if (currentUser == null || !currentUser.isActive()) {
                logger.info(String.format("user %s wants to add a user which doesn't exist to group.",
                        user.getUsername()));
                return new EditGroupResponse(errorsConfig.getProperty("notExistedUserError"));
            } else if (!user.getFollowings().contains(currentUser.getId())) {
                logger.info(String.format("user %s wants to add a user which isn't in his/her followings",
                        user.getUsername()));
                return new EditGroupResponse(errorsConfig.getProperty("userNotInFollowingsError"));
            } else if (group.getUsers().contains(currentUser.getId())) {
                logger.info(String.format("user %s wants to add a user which already exists in group.",
                        user.getUsername()));
                return new EditGroupResponse(errorsConfig.getProperty("userAlreadyInGroupError"));
            } else {
                group.addUser(currentUser.getId());
                Connector.getInstance().save(group);
                return new EditGroupResponse("");
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}