package XO;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public List<GameResponse> getResponse(Integer writer, String content) {
        List<String> lines = new ArrayList<>();
        content.lines().forEach(lines::add);
        try {
            switch (lines.get(0)) {
                case "new game":
                    return handleNewGame(writer);
                case "join game":
                    return handleJoiningGame(writer, lines.get(1));
                case "place":
                    return handlePlacing(writer, lines.get(1), lines.get(2));
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private List<GameResponse> handleNewGame(Integer writer) {
        Game game = new Game(writer);
        GameResponse gameResponse = new GameResponse(writer, String.format("game created. it's code is %s",
                game.getId()));
        return new ArrayList<>(List.of(gameResponse));
    }

    private List<GameResponse> handleJoiningGame(Integer writer, String gameId) {
        Game game = Game.findGame(Integer.valueOf(gameId));
        if(game.getSecondUser() != null) {
            GameResponse gameResponse = new GameResponse(writer, "this game is started already!");
            return new ArrayList<>(List.of(gameResponse));
        }

        game.setSecondUser(writer);
        GameResponse firstGameResponse = new GameResponse(game.getFirstUser(),
                "someone joined your game. it's your turn to place X on board!");
        GameResponse secondGameResponse = new GameResponse(game.getSecondUser(),
                "you joined the game.");
        return new ArrayList<>(List.of(firstGameResponse, secondGameResponse));
    }

    private List<GameResponse> handlePlacing(Integer writer, String gameId, String place) {
        Game game = Game.findGame(Integer.valueOf(gameId));
        if(!writer.equals(game.getFirstUser()) && (game.getSecondUser() == null || !writer.equals(game.getSecondUser()))) {
            GameResponse gameResponse = new GameResponse(writer, "you're not joined in this game");
            return new ArrayList<>(List.of(gameResponse));
        }
        String error = game.placeX(writer, place);
        if(error != null) {
            GameResponse gameResponse = new GameResponse(writer, error);
            return new ArrayList<>(List.of(gameResponse));
        }
        int winner = game.getCurBoard().checkEndOfGame();
        if(winner < 0) {
            GameResponse firstGameResponse;
            GameResponse secondGameResponse;
            if(game.getTurn() == 0) {
                firstGameResponse = new GameResponse(game.getFirstUser(),
                        String.format(game.getCurBoard().print() + "it's your turn in game %s", game.getId()));
                secondGameResponse = new GameResponse(game.getSecondUser(),
                        String.format(game.getCurBoard().print() + "it's your opponent turn in game %s", game.getId()));
            } else {
                firstGameResponse = new GameResponse(game.getFirstUser(),
                        String.format(game.getCurBoard().print() + "it's your opponent turn in game %s", game.getId()));
                secondGameResponse = new GameResponse(game.getSecondUser(),
                        String.format(game.getCurBoard().print() + "it's your turn in game %s", game.getId()));
            }
            return new ArrayList<>(List.of(firstGameResponse, secondGameResponse));
        } else {
            Game.removeGame(game);
            if(winner == 2) {
                GameResponse firstGameResponse = new GameResponse(game.getFirstUser(),
                        String.format("the game %s is finished with draw status.", game.getId()));
                GameResponse secondGameResponse = new GameResponse(game.getSecondUser(),
                        String.format("the game %s is finished with draw status.", game.getId()));
                return new ArrayList<>(List.of(firstGameResponse, secondGameResponse));
            } else if(winner == 0) {
                GameResponse firstGameResponse = new GameResponse(game.getFirstUser(),
                        String.format("you won the game %s", game.getId()));
                GameResponse secondGameResponse = new GameResponse(game.getSecondUser(),
                        String.format("you lost the game %s", game.getId()));
                return new ArrayList<>(List.of(firstGameResponse, secondGameResponse));
            } else {
                GameResponse firstGameResponse = new GameResponse(game.getFirstUser(),
                        String.format("you lost the game %s", game.getId()));
                GameResponse secondGameResponse = new GameResponse(game.getSecondUser(),
                        String.format("you won the game %s", game.getId()));
                return new ArrayList<>(List.of(firstGameResponse, secondGameResponse));
            }
        }
    }
}
