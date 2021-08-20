package XO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final static List<Game> allGames = new ArrayList<>();
    @Getter
    private final Integer id;
    @Getter
    private final Integer firstUser;
    @Setter
    @Getter
    private Integer secondUser;
    @Getter
    private int turn;
    @Getter
    private final Board curBoard;

    private final char[] roles = {'X', 'O'};

    public static Game findGame(Integer gameId) {
        for (Game game : allGames)
            if (game.getId().equals(gameId))
                return game;
        return null;
    }

    public static void removeGame(Game game) {
        allGames.remove(game);
    }

    public Game(Integer firstUser) {
        this.id = allGames.size() + 1;
        this.firstUser = firstUser;
        this.turn = 0;
        char[][] board = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 2) {
                    board[i][j] = ' ';
                } else {
                    board[i][j] = '_';
                }
            }
        }
        this.curBoard = new Board(board);


        allGames.add(this);
    }

    public String placeX(Integer writer, String place) {
        if (turn == 0 && !writer.equals(firstUser))
            return "it's not your turn!";
        if(turn == 1 && !writer.equals(secondUser))
            return "it's not your turn!";

        String[] s = place.split(" ");
        int row;
        int col;
        if (s.length != 2)
            return "## invalid input, please input valid [row column], separate using space";

        try {
            row = Integer.parseInt(s[0]);
        } catch (NumberFormatException e) {
            return "Row input error, please input [row, column], digits only!";
        }

        try {
            col = Integer.parseInt(s[1]);
        } catch (NumberFormatException e) {
            return "Column input error, please input [row, column], digits only!";
        }

        if (row < 1 || row > 3 || col < 1 || col > 3)
            return "Row or Column does not exist, please input correct [row, column]!";

        // finally, we get a correct input
        if (curBoard.board[row - 1][col - 1] != ' ' && curBoard.board[row - 1][col - 1] != '_')
            return "The position has been taken, please input another [row, column]!";

        // finally, we get a valid input
        curBoard.board[row - 1][col - 1] = roles[turn];
        turn++;
        turn = turn % 2;
        return null;
    }
}
