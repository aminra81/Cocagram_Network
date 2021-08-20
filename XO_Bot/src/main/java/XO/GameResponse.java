package XO;

import lombok.Getter;

public class GameResponse {
    @Getter
    private final Integer userId;
    @Getter
    private final String message;

    public GameResponse(Integer userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
