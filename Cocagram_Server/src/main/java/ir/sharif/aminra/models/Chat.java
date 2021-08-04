package ir.sharif.aminra.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
public class Chat implements SaveAble {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    String chatName;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Integer> messages;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Integer> users;

    boolean isGroup;

    public Chat(String chatName, boolean isGroup) {
        this.chatName = chatName;
        this.isGroup = isGroup;
        this.users = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public String getChatName() {
        return chatName;
    }

    public List<Integer> getMessages() {
        return messages;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void addUser(Integer userID) {
        users.add(userID);
    }

    public void addMessage(Integer messageID) {
        messages.add(messageID);
    }

    public void removeMessage(Integer messageID) {
        messages.remove(messageID);
    }

    public void removeUser(Integer userID) {
        users.remove(userID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return chat.getId().equals(getId());
    }
}
