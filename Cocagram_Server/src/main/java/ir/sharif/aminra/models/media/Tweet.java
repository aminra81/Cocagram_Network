package ir.sharif.aminra.models.media;

import ir.sharif.aminra.models.SaveAble;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@ToString
@NoArgsConstructor
public class Tweet extends Media implements SaveAble {

    private Integer upPost;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> likes;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> comments;

    int spamReports;

    public Tweet(String content, Integer writer, Integer upPost, Integer image) {
        super(content, writer, image);
        this.upPost = upPost;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        spamReports = 0;
    }

    public void addLike(Integer user) {
        this.likes.add(user);
    }

    public void removeLike(Integer user) {
        this.likes.remove(user);
    }

    public Integer getUpPost() {
        return upPost;
    }

    public int getLikeNumbers() {
        return likes.size();
    }

    public static void sortByDateTime(List<Tweet> tweets) {
        Comparator<Tweet> byDateTime = Comparator.comparing(Tweet::getDateTime).reversed();
        tweets.sort(byDateTime);
    }

    public static void sortByLikeNumbers(List<Tweet> tweets) {
        Comparator<Tweet> byDateTime = Comparator.comparing(Tweet::getLikeNumbers).reversed();
        tweets.sort(byDateTime);
    }

    public void addComment(Integer comment) {
        this.comments.add(comment);
    }

    public List<Integer> getComments() {
        return comments;
    }

    public void reportSpam() {
        spamReports++;
    }

    public int getSpamReports() { return spamReports; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return tweet.getId().equals(getId());
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
