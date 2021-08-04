package ir.sharif.aminra.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@ToString
@Entity
@NoArgsConstructor
public class ChatState implements SaveAble{
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer chat;

    LocalDateTime lastCheck;

    public ChatState(Integer chat) {
        this.chat = chat;
        this.lastCheck = LocalDateTime.now();
    }

    public Integer getChat() {
        return chat;
    }

    public LocalDateTime getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(LocalDateTime lastCheck) { this.lastCheck = lastCheck; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatState chatState = (ChatState) o;
        return chatState.getChat().equals(getChat());
    }
}
