package ir.sharif.aminra;

import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.models.Chat;
import ir.sharif.aminra.models.ChatState;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.media.Tweet;

public class Main {
    public static void main(String[] args) {
        Connector connector = new Connector();
        User user = new User("a", "a", "a", "a", null, "a", "p",
                "p", true, "online", null);
        connector.save(user);

        User user2 = new User("b", "a", "a", "a", null, "a", "p",
                "p", true, "online", null);
        connector.save(user2);

        user.addToFollowers(user2.getId());
        connector.save(user);

        Chat savedMessageChat = new Chat("saved messages", false);
        Chat savedMessage2Chat = new Chat("saved messages2", false);
        connector.save(savedMessageChat);
        connector.save(savedMessage2Chat);

        ChatState chatState = new ChatState(savedMessageChat.getId());
        ChatState chatState1 = new ChatState(savedMessage2Chat.getId());
        connector.save(chatState);
        connector.save(chatState1);

        user.addChatState(chatState.getId());
        user.addChatState(chatState1.getId());

        connector.save(user);

        Tweet tweet = new Tweet("salam", 2, null, null);
        connector.save(tweet);

        user.addToLikedTweets(tweet.getId());
        connector.save(user);
    }
}
