package ir.sharif.aminra.response;

import ir.sharif.aminra.models.Group;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.events.SwitchToProfileType;
import ir.sharif.aminra.models.viewModels.ViewTweet;

import java.time.LocalDate;
import java.util.List;

public interface ResponseVisitor {
    void goTo(String pageName, String message);
    void showError(String message);
    void updatePersonalPage(byte[] bytes, List<ViewTweet> viewTweetList);
    void enter(boolean success, String message);
    void logout();
    void switchToEditPage(String username, String firstname, String lastname, String bio, LocalDate birthdate,
                        String email, String phoneNumber, String lastSeenType, boolean accountPrivacy, boolean dataPrivacy);
    void applyEditResponse(String error);
    void updateNotificationsPage(List<String> requestMessages, List<String> systemMessages, List<User> requests);
    void updateListsPage(List<User> followers, List<User> followings, List<User> blocklist, List<Group> groups);
    void updateGroupPage(List<User> members);
    void applyCreateGroupResponse(String error);
    void applyEditGroupResponse(String error);
    void switchToProfilePage(SwitchToProfileType switchToProfileType, boolean exists, boolean mine, String error,
                             Integer userToBeVisited);
    void updateProfilePage(String username, byte[] avatarArray, String firstname, String lastname, String lastSeen,
                           String bio, String birthdate, String email, String phoneNumber, String blockString,
                           String muteString, String followString);
    void back();
    void updateTweetPage(String tweetContent, String tweetDate, String retweetString, byte[] tweetImage, int likeNumbers, List<ViewTweet> viewTweetList, String likeButtonText);
    void applyTweetActionResponse(String verdict, boolean isError);
    void updateTimelinePage(List<ViewTweet> viewTweetList);
    void updateExplorerPage(List<ViewTweet> viewTweetList);
    void switchToSettingsPage(boolean isPrivate, String lastSeenType, String password);
}
