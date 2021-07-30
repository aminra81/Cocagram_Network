package ir.sharif.aminra.request;

import ir.sharif.aminra.models.events.*;
import ir.sharif.aminra.response.Response;

import java.time.LocalDate;

public interface RequestVisitor {
    Response updatePage(String pageName);
    Response login(String username, String password);
    Response register(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                      String email, String phoneNumber, String password, boolean publicData, String lastSeenType);
    Response logout();
    Response edit(String firstname, String lastname, String bio, LocalDate birthdate, String email, String phoneNumber, byte[] avatarArray);
    Response switchToEditPage();
    Response followRequestHandle(RequestAnswerType requestAnswerType, Integer requesterID);
    Response updateGroupPage(Integer groupId);
    Response createGroup(String groupName);
    Response editGroup(GroupPageEventType groupPageEventType, Integer group, String username);
    Response profileHandle(ProfilePageEventType profilePageEventType, Integer userToBeVisited);
    Response switchToProfilePage(SwitchToProfileType switchToProfileType, Integer id, String username);
    Response updateProfilePage(Integer userToBeVisited);
    Response newTweet(String content, byte[] avatarArray, Integer upPost);
    Response updateTweetPage(Integer tweetId, boolean myTweets);
    Response applyTweetAction(TweetPageEventType tweetPageEventType, Integer tweetId);
    Response switchToPrivacySettingsPage();
    Response deactivate();
    Response editPrivacySettings(boolean isPrivate, String lastSeenType, String password);
}
