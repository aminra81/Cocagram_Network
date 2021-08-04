package ir.sharif.aminra.models.media;

import ir.sharif.aminra.models.SaveAble;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@ToString
@NoArgsConstructor
public class Message extends Media implements SaveAble {

    Integer mainMedia;

    boolean isForwardedTweet;

    boolean isDeleted;

    public Message(String content, Integer writer, Integer mainMedia, Integer image, boolean isForwardedTweet) {
        super(content, writer, image);
        this.mainMedia = mainMedia;
        this.isForwardedTweet = isForwardedTweet;
        isDeleted = false;
    }


    public static void sortByDateTime(List<Message> messages) {
        Comparator<Message> byDateTime = Comparator.comparing(Message::getDateTime);
        messages.sort(byDateTime);
    }

    public Integer getMainMedia() { return mainMedia; }

    public boolean isForwardedTweet() { return isForwardedTweet; }

    public boolean isDeleted() { return isDeleted; }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return message.getId().equals(getId());
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
