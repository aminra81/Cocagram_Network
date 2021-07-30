package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.enterPage.SignInController;
import ir.sharif.aminra.controller.enterPage.SignUpController;
import ir.sharif.aminra.controller.explorerPage.ExplorerController;
import ir.sharif.aminra.controller.network.ResponseSender;
import ir.sharif.aminra.controller.personalPage.MyPageController;
import ir.sharif.aminra.controller.personalPage.editPage.EditPageController;
import ir.sharif.aminra.controller.personalPage.listsPage.GroupPageController;
import ir.sharif.aminra.controller.personalPage.listsPage.ListsPageController;
import ir.sharif.aminra.controller.personalPage.listsPage.NewGroupController;
import ir.sharif.aminra.controller.personalPage.notificationsPage.NotificationsPageController;
import ir.sharif.aminra.controller.personalPage.notificationsPage.RequestController;
import ir.sharif.aminra.controller.profileView.ProfileViewController;
import ir.sharif.aminra.controller.settingsPage.PrivacySettingsController;
import ir.sharif.aminra.controller.settingsPage.SettingsController;
import ir.sharif.aminra.controller.timelinePage.TimelineController;
import ir.sharif.aminra.controller.tweets.NewTweetController;
import ir.sharif.aminra.controller.tweets.TweetManager;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.ClientDisconnectException;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.events.*;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.util.Config;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

public class ClientHandler extends Thread implements RequestVisitor {

    @Getter
    private final ResponseSender responseSender;
    private volatile boolean running;
    @Getter
    @Setter
    private User user;

    private final SignUpController signUpController;
    private final SignInController signInController;
    private final MyPageController myPageController;
    private final EditPageController editPageController;
    private final NotificationsPageController notificationsPageController;
    private final RequestController requestController;
    private final ListsPageController listsPageController;
    private final GroupPageController groupPageController;
    private final NewGroupController newGroupController;
    private final ProfileViewController profileViewController;
    private final NewTweetController newTweetController;
    private final TimelineController timelineController;
    private final ExplorerController explorerController;
    private final SettingsController settingsController;
    private final PrivacySettingsController privacySettingsController;

    public ClientHandler(ResponseSender responseSender) throws IOException {
        this.responseSender = responseSender;

        signUpController = new SignUpController(this);
        signInController = new SignInController(this);
        myPageController = new MyPageController(this);
        editPageController = new EditPageController(this);
        notificationsPageController = new NotificationsPageController(this);
        requestController = new RequestController(this);
        listsPageController = new ListsPageController(this);
        groupPageController = new GroupPageController(this);
        newGroupController = new NewGroupController(this);
        profileViewController = new ProfileViewController(this);
        newTweetController = new NewTweetController(this);
        timelineController = new TimelineController(this);
        explorerController = new ExplorerController(this);
        settingsController = new SettingsController(this);
        privacySettingsController = new PrivacySettingsController(this);
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    @Override
    public void run() {
        while (running) {
            try {
                try {
                    //updating user
                    if(user != null)
                        user  = Connector.getInstance().fetch(User.class, user.getId());
                    Request request = responseSender.getRequest();
                    Response response = request.visit(this);
                    responseSender.sendResponse(response);
                } catch (DatabaseDisconnectException e) {
                    responseSender.sendResponse(new ShowErrorResponse(Config.getConfig("server").
                            getProperty("databaseDisconnectError")));
                }
            } catch (ClientDisconnectException e) {
                running = false;
            }
        }
        responseSender.close();
    }

    @Override
    public Response updatePage(String pageName) {
        switch (pageName) {
            case "MyFXController":
                return myPageController.getUpdate();
            case "NotificationsFXController":
                return notificationsPageController.getUpdate();
            case "ListsFXController":
                return listsPageController.getUpdate();
            case "TimelineFXController":
                return timelineController.getUpdate();
            case "ExplorerFXController":
                return explorerController.getUpdate();
            default:
                return null;
        }
    }

    @Override
    public Response login(String username, String password) {
        return signInController.login(username, password);
    }

    @Override
    public Response register(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                             String email, String phoneNumber, String password, boolean publicData, String lastSeenType) {
        return signUpController.register(username, firstname, lastname, bio, birthDate, email, phoneNumber, password, publicData,
                lastSeenType);
    }

    @Override
    public Response logout() {
        return settingsController.logout();
    }

    @Override
    public Response edit(String firstname, String lastname, String bio, LocalDate birthdate, String email,
                         String phoneNumber, byte[] avatarArray) {
        return editPageController.edit(firstname, lastname, bio, birthdate, email, phoneNumber, avatarArray);
    }

    @Override
    public Response switchToEditPage() {
        return editPageController.getInfoToSwitch();
    }

    @Override
    public Response followRequestHandle(RequestAnswerType requestAnswerType, Integer requesterID) {
        return requestController.handle(requestAnswerType, requesterID);
    }

    @Override
    public Response updateGroupPage(Integer groupId) {
        return groupPageController.getUpdate(groupId);
    }

    @Override
    public Response createGroup(String groupName) {
        return newGroupController.createGroup(groupName);
    }

    @Override
    public Response editGroup(GroupPageEventType groupPageEventType, Integer group, String username) {
        return groupPageController.getEditResponse(groupPageEventType, group, username);
    }

    @Override
    public Response profileHandle(ProfilePageEventType profilePageEventType, Integer userToBeVisited) {
        return profileViewController.profileHandle(profilePageEventType, userToBeVisited);
    }

    @Override
    public Response switchToProfilePage(SwitchToProfileType switchToProfileType, Integer Id, String username) {
        return profileViewController.getInfoToSwitch(switchToProfileType, Id, username);
    }

    @Override
    public Response updateProfilePage(Integer userToBeVisited) {
        return profileViewController.getUpdate(userToBeVisited);
    }

    @Override
    public Response newTweet(String content, byte[] avatarArray, Integer upPost) {
        return newTweetController.addTweet(content, avatarArray, upPost);
    }

    @Override
    public Response updateTweetPage(Integer tweetId, boolean myTweets) {
        return TweetManager.getInstance().getUpdate(tweetId, user, myTweets);
    }

    @Override
    public Response applyTweetAction(TweetPageEventType tweetPageEventType, Integer tweetId) {
        return TweetManager.getInstance().applyTweetAction(tweetPageEventType, user, tweetId);
    }

    @Override
    public Response switchToPrivacySettingsPage() {
        return privacySettingsController.getInfoToSwitch();
    }

    @Override
    public Response deactivate() {
        return privacySettingsController.deactivate();
    }

    @Override
    public Response editPrivacySettings(boolean isPrivate, String lastSeenType, String password) {
        return privacySettingsController.editPrivacySettingsResponse(isPrivate, lastSeenType, password);
    }

}
