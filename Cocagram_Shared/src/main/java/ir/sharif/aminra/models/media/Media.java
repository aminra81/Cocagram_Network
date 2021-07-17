package ir.sharif.aminra.models.media;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
public abstract class Media {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String content;
    Integer writer;
    Integer image;
    LocalDateTime datetime;

    public Media(String content, Integer writer, Integer image) {
        this.content = content;
        this.writer = writer;
        this.image = image;
        datetime = LocalDateTime.now();
    }

    public Integer getWriter() { return writer; }

    public String getContent() { return content; }

    public Integer getImage() { return image; }

    public LocalDateTime getDateTime() {
        return datetime;
    }
}
