package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.enterPage.EnterController;
import ir.sharif.aminra.controller.explorerPage.ExplorerController;
import ir.sharif.aminra.controller.messagingPage.EditMessageController;
import ir.sharif.aminra.controller.messagingPage.MessageViewerController;
import ir.sharif.aminra.controller.messagingPage.MessagingController;
import ir.sharif.aminra.controller.messagingPage.chatGroupPage.ChatGroupController;
import ir.sharif.aminra.controller.messagingPage.messageSendingPage.MessageSendingController;
import ir.sharif.aminra.controller.network.RequestSender;
import ir.sharif.aminra.controller.personalPage.MyPageController;
import ir.sharif.aminra.controller.personalPage.editPage.EditPageController;
import ir.sharif.aminra.controller.personalPage.listsPage.GroupPageController;
import ir.sharif.aminra.controller.personalPage.listsPage.ListsPageController;
import ir.sharif.aminra.controller.personalPage.listsPage.NewGroupController;
import ir.sharif.aminra.controller.personalPage.notificationsPage.NotificationsPageController;
import ir.sharif.aminra.controller.profileView.ProfileViewController;
import ir.sharif.aminra.controller.settingsPage.PrivacySettingsController;
import ir.sharif.aminra.controller.timelinePage.TimelineController;
import ir.sharif.aminra.controller.tweets.TweetManager;
import ir.sharif.aminra.exceptions.ClientDisconnectException;
import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.models.viewModels.*;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.UpdatePageRequest;
import ir.sharif.aminra.request.messagingPage.UpdateMessageViewerPageRequest;
import ir.sharif.aminra.request.messagingPage.UpdateMessagingPageRequest;
import ir.sharif.aminra.request.personalPage.editPage.UpdateProfilePageRequest;
import ir.sharif.aminra.request.personalPage.listsPage.UpdateGroupPageRequest;
import ir.sharif.aminra.request.tweets.UpdateTweetPageRequest;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.util.Loop;
import ir.sharif.aminra.view.FXController;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.explorerPage.ExplorerFXController;
import ir.sharif.aminra.view.messagingPage.MessageViewerFXController;
import ir.sharif.aminra.view.messagingPage.MessagingFXController;
import ir.sharif.aminra.view.messagingPage.messageSendingPage.ChatSelectingFXController;
import ir.sharif.aminra.view.personalPage.MyFXController;
import ir.sharif.aminra.view.personalPage.listsPage.GroupFXController;
import ir.sharif.aminra.view.personalPage.listsPage.ListsFXController;
import ir.sharif.aminra.view.personalPage.notificationsPage.NotificationsFXController;
import ir.sharif.aminra.view.profileView.ProfileFXController;
import ir.sharif.aminra.view.timelinePage.TimelineFXController;
import ir.sharif.aminra.view.tweets.TweetFXController;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Client implements ResponseVisitor {

    static private final Logger logger = LogManager.getLogger(ResponseVisitor.class);

    @Getter
    private static Client client;
    private final RequestSender requestSender;
    private final List<Request> requestsList;
    private final Loop loop, updater;

    private final EnterController enterController;
    private final MyPageController myPageController;
    private final EditPageController editPageController;
    private final NotificationsPageController notificationsPageController;
    private final ListsPageController listsPageController;
    private final GroupPageController groupPageController;
    private final NewGroupController newGroupController;
    private final ProfileViewController profileViewController;
    private final TimelineController timelineController;
    private final ExplorerController explorerController;
    private final PrivacySettingsController privacySettingsController;
    private final MessagingController messagingController;
    private final ChatGroupController chatGroupController;
    private final MessageSendingController messageSendingController;
    private final MessageViewerController messageViewerController;
    private final EditMessageController editMessageController;

    public Client(RequestSender requestSender) {
        this.requestSender = requestSender;
        this.requestsList = new LinkedList<>();
        this.loop = new Loop(Config.getConfig("client").getProperty(Double.class, "loopFps"),
                this::sendRequests);
        this.updater = new Loop(Config.getConfig("client").getProperty(Double.class, "updaterFps"),
                this::updatePage);
        client = this;

        enterController = new EnterController();
        myPageController = new MyPageController();
        editPageController = new EditPageController();
        notificationsPageController = new NotificationsPageController();
        listsPageController = new ListsPageController();
        groupPageController = new GroupPageController();
        newGroupController = new NewGroupController();
        profileViewController = new ProfileViewController();
        timelineController = new TimelineController();
        explorerController = new ExplorerController();
        privacySettingsController = new PrivacySettingsController();
        messagingController = new MessagingController();
        chatGroupController = new ChatGroupController();
        messageSendingController = new MessageSendingController();
        messageViewerController = new MessageViewerController();
        editMessageController = new EditMessageController();
    }

    public void start(Stage stage) {
        loop.start();
        updater.start();
        ViewManager.getInstance().start(stage);
    }


    public void addRequest(Request request) {
        synchronized (requestsList) {
            requestsList.add(request);
        }
    }

    private void sendRequests() {
        List<Request> tmpRequestsList;
        synchronized (requestsList) {
            tmpRequestsList = new LinkedList<>(requestsList);
            requestsList.clear();
        }
        try {
            for (Request request : tmpRequestsList) {
                Response response;
                response = requestSender.sendRequest(request);
                if (response != null)
                    response.visit(this);
            }
        } catch (ClientDisconnectException e) {
            logger.info("client program terminated");
            System.exit(0);
        }
    }

    private void updatePage() {
        if (ViewManager.getInstance().getCurPage() == null)
            return;
        FXController fxController = ViewManager.getInstance().getCurPage().getFxController();
        if (fxController instanceof GroupFXController)
            addRequest(new UpdateGroupPageRequest(((GroupFXController) fxController).getGroup()));
        else if (fxController instanceof ProfileFXController)
            addRequest(new UpdateProfilePageRequest(((ProfileFXController) fxController).getUserToVeVisited()));
        else if(fxController instanceof TweetFXController)
            addRequest(new UpdateTweetPageRequest(((TweetFXController) fxController).getTweetID(),
                    ((TweetFXController) fxController).isMyTweets()));
        else if(fxController instanceof MessagingFXController)
            addRequest(new UpdateMessagingPageRequest(((MessagingFXController) fxController).getSelectedChatID(), false));
        else if(fxController instanceof MessageViewerFXController)
            addRequest(new UpdateMessageViewerPageRequest(((MessageViewerFXController) fxController).getMessageID()));
        else if (fxController instanceof MyFXController || fxController instanceof ListsFXController ||
                fxController instanceof NotificationsFXController || fxController instanceof TimelineFXController ||
        fxController instanceof ExplorerFXController || fxController instanceof ChatSelectingFXController)
            addRequest(new UpdatePageRequest(fxController.getClass().getSimpleName()));
    }

    @Override
    public void goTo(String pageName, String message) {
        Platform.runLater(() -> {
            ViewManager.getInstance().setPage(new Page(pageName));
            if (message.length() > 0)
                ViewManager.getInstance().showInformation(message);
        });
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() -> ViewManager.getInstance().showError(message));
    }

    @Override
    public void updatePersonalPage(String bytes, List<ViewTweet> viewTweetList) {
        myPageController.refresh(bytes, viewTweetList);
    }

    @Override
    public void enter(boolean success, String message) {
        enterController.enter(success, message);
    }

    @Override
    public void logout(boolean terminate) {
        if(terminate)
            System.exit(0);
        else
            goTo("enterPage", "");
    }

    @Override
    public void switchToEditPage(String username, String firstname, String lastname, String bio,
                                 LocalDate birthdate, String email, String phoneNumber, String lastSeenType,
                                 boolean isPrivate, boolean isPublicData) {
        editPageController.switchToEditPage(username, firstname, lastname, bio, birthdate, email, phoneNumber, lastSeenType,
                isPrivate, isPublicData);
    }

    @Override
    public void applyEditResponse(String error) {
        editPageController.applyEditResponse(error);
    }

    @Override
    public void updateNotificationsPage(List<String> requestMessages, List<String> systemMessages, List<ViewUser> requests) {
        notificationsPageController.refresh(requestMessages, systemMessages, requests);
    }

    @Override
    public void updateListsPage(List<ViewUser> followers, List<ViewUser> followings, List<ViewUser> blocklist, List<ViewGroup> groups) {
        listsPageController.refresh(followers, followings, blocklist, groups);
    }

    @Override
    public void updateGroupPage(List<ViewUser> members) {
        groupPageController.refresh(members);
    }

    @Override
    public void applyCreateGroupResponse(String error) {
        newGroupController.applyCreateGroupResponse(error);
    }

    @Override
    public void applyEditGroupResponse(String error) {
        groupPageController.applyEditGroupResponse(error);
    }

    @Override
    public void switchToProfilePage(SwitchToProfileType switchToProfileType, boolean exists, boolean mine, String error,
                                    Integer userToBeVisited) {
        profileViewController.switchToProfilePage(switchToProfileType, exists, mine, error, userToBeVisited);
    }

    @Override
    public void updateProfilePage(String username, String avatarString, String firstname, String lastname, String lastSeen,
                                  String bio, String birthdate, String email, String phoneNumber, String blockString,
                                  String muteString, String followString) {
        profileViewController.refresh(username, avatarString, firstname, lastname, lastSeen, bio, birthdate, email,
                phoneNumber, blockString, muteString, followString);
    }

    @Override
    public void back() {
        Platform.runLater(() -> ViewManager.getInstance().back());
    }

    @Override
    public void updateTweetPage(String tweetContent, String tweetDate, String retweetString, String tweetImage,
                                int likeNumbers, List<ViewTweet> viewTweetList, String likeButtonText) {
        TweetManager.getInstance().refresh(tweetContent, tweetDate, retweetString, tweetImage, likeNumbers, viewTweetList,
                likeButtonText);
    }

    @Override
    public void applyTweetActionResponse(String verdict, boolean isError) {
        TweetManager.getInstance().applyTweetActionResponse(verdict, isError);
    }

    @Override
    public void updateTimelinePage(List<ViewTweet> viewTweetList) {
        timelineController.refresh(viewTweetList);
    }

    @Override
    public void updateExplorerPage(List<ViewTweet> viewTweetList) {
        explorerController.refresh(viewTweetList);
    }

    @Override
    public void switchToSettingsPage(boolean isPrivate, String lastSeenType, String password) {
        privacySettingsController.switchToPrivacySettingsPage(isPrivate, lastSeenType, password);
    }

    @Override
    public void updateMessagingPage(boolean isChanged, Integer chatId, String chatName, List<ViewChat> chats, List<ViewMessage> messages) {
        messagingController.refresh(isChanged, chatId, chatName, chats, messages);
    }

    @Override
    public void applyEditChatGroupResponse(String error) {
        chatGroupController.applyError(error);
    }

    @Override
    public void applyNewMessageResponse(String error, Integer messageId, List<ViewGroup> groups, List<ViewChat> chats) {
        messageSendingController.applyNewMessageResponse(error, messageId, groups, chats);
    }

    @Override
    public void updateMessageViewerPage(boolean deactivated, String messageImage, String messageContent,
                                        LocalDateTime messageDateTime, String messageSender) {
        messageViewerController.refresh(deactivated, messageImage, messageContent, messageDateTime, messageSender);
    }

    @Override
    public void applyEditMessageResponse(String error) {
        editMessageController.applyEditMessageResponse(error);
    }

}