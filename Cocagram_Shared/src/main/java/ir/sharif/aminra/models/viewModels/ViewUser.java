package ir.sharif.aminra.models.viewModels;

import lombok.Getter;

public class ViewUser {

    @Getter
    private final String username;
    @Getter
    private final Integer userId;
    private final boolean isActive;

    public ViewUser(String username, Integer userId, boolean isActive) {
        this.username = username;
        this.userId = userId;
        this.isActive = isActive;
    }

    public boolean isActive() { return  isActive; }
}
