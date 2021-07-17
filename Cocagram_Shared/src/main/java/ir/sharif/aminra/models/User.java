package ir.sharif.aminra.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Entity
@NoArgsConstructor
public class User implements SaveAble {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;
    private String lastname;
    private String username;
    private String bio;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean publicData;
    private boolean isActive;
    private LocalDateTime lastSeen;
    private String lastSeenType;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> followings;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> followers;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> blockList;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> tweets;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> requestNotifications;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> likedTweets;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> requests;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> notifications;

    private boolean isPrivate;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> groups;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> mutedUsers;

    private Integer avatar;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> reportedSpamTweets;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> chatStates;

    //TODO adding saved message and avatar to it

    public User(String username, String firstname, String lastname, String bio, LocalDate birthDate, String email, String phoneNumber, String password, boolean publicData, String lastSeenType, Integer avatarID) {
        //get from user.
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.birthDate = birthDate;

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.publicData = publicData;
        this.lastSeenType = lastSeenType;
        this.avatar = avatarID;


        //fill them by default.
        this.isActive = true;
        this.isPrivate = false;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.blockList = new ArrayList<>();
        this.lastSeen = LocalDateTime.now();
        this.groups = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.likedTweets = new ArrayList<>();
        this.requestNotifications = new ArrayList<>();

        this.requests = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.mutedUsers = new ArrayList<>();
        this.reportedSpamTweets = new ArrayList<>();

        this.chatStates = new ArrayList<>();
    }



    public void addToLikedTweets(Integer tweet) {
        this.likedTweets.add(tweet);
    }

    public void removeFromTweets(Integer tweet) {
        this.tweets.remove(tweet);
    }

    public void removeFromLikedTweets(Integer tweet) {
        this.likedTweets.remove(tweet);
    }

    public void addToTweets(Integer tweet) {
        this.tweets.add(tweet);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getTweets() {
        return tweets;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBio() {
        return bio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isPublicData() {
        return publicData;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getLastSeenType() {
        return lastSeenType;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public List<String> getRequestNotifications() {
        return requestNotifications;
    }

    public List<Integer> getRequests() {
        return requests;
    }

    public void addToFollowings(Integer user) {
        followings.add(user);
    }

    public void removeFromFollowings(Integer user) {
        followings.remove(user);
    }

    public void addToFollowers(Integer user) {
        followers.add(user);
    }

    public void removeFromFollowers(Integer user) {
        followers.remove(user);
    }

    public void addToBlocklist(Integer user) {
        blockList.add(user);
    }

    public void removeFromBlocklist(Integer user) {
        blockList.remove(user);
    }

    public void addToRequestNotifications(String content) {
        requestNotifications.add(content);
        if (requestNotifications.size() > 10)
            requestNotifications.remove(0);
    }

    public void addToNotifications(String content) {
        notifications.add(content);
        if (notifications.size() > 10)
            notifications.remove(0);
    }

    public void addToRequests(Integer requester) {
        requests.add(requester);
    }

    public void removeFromRequests(User requester) {
        requests.remove(requester);
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public List<Integer> getBlockList() {
        return blockList;
    }

    public void removeGroup(Integer group) {
        groups.remove(group);
    }

    public void addGroup(Integer group) {
        groups.add(group);
    }

    public List<Integer> getLikedTweets() {
        return likedTweets;
    }

    public List<Integer> getMutedUsers() {
        return mutedUsers;
    }

    public void addToMutedUsers(Integer user) {
        mutedUsers.add(user);
    }

    public void removeFromMutedUsers(Integer user) {
        mutedUsers.remove(user);
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastSeenType(String lastSeenType) {
        this.lastSeenType = lastSeenType;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void removeFromRequestNotifications(String requestNotification) {
        requestNotifications.remove(requestNotification);
    }

    public void removeFromNotifications(String notification) {
        notifications.remove(notification);
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public void addToReportedSpamTweets(Integer tweet) {
        reportedSpamTweets.add(tweet);
    }

    public List<Integer> getReportedSpamTweets() {
        return reportedSpamTweets;
    }

    public void addChatState(Integer chatState) {
        chatStates.add(chatState);
    }

    public List<Integer> getChatStates() {
        return chatStates;
    }

    /*public void updateChatLastCheck(Integer chatID) {
        for (Integer chatState : chatStates)
            if (chatState.getChat().equals(chatID))
                chatState.setLastCheck(LocalDateTime.now());
    }*/
    //TODO make updateChatLastCheck correct in logic.

    public void removeFromChatStates(Integer chatState) {
        chatStates.remove(chatState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
