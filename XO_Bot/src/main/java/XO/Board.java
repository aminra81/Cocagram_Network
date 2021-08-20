package XO;

public class Board {
    char[][] board = new char[3][3];

    public Board(char[][] board) {
        for(int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, 3);
        }
    }

    public int checkEndOfGame() {

        // check X roles
        if(this.findLine('X')) {
            return 0;
        } else if(this.findLine('O')) {
            return 1;
        }

        // now check to see if the board is full
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(this.board[i][j] == ' ' || this.board[i][j] == '_') {
                    // board is not full, can continue
                    return -1;
                }
            }
        }
        // board is full, no winner
        return 2;

    }

    private boolean findLine(char role) {
        // the simple way is just to check each row and each column, and the diagonal

        //System.out.println(role);
        boolean line_found;

        // for each row
        for (char[] chars : this.board) {
            line_found = true;
            for (int j = 0; j < this.board[0].length; j++) {
//				System.out.print(board.board[i][j]);
                if (chars[j] != role) {
                    line_found = false;
                    break;
                }
            }

            if (line_found) {
                return true;
            }
        }

        // for each column
        for(int j = 0; j < this.board[0].length; j++) {
            line_found = true;
            for (char[] chars : this.board) {
                if (chars[j] != role) {
                    line_found = false;
                    break;
                }
            }
            if(line_found) return true;
        }


        // for diagonal
        line_found = true;
        for(int i = 0; i < this.board.length; i++) {
            if(this.board[i][i] != role) {
                line_found = false;
                break;
            }
        }
        if(line_found) return true;

        // for reverse diagonal
        line_found = true;
        for(int i = 0; i < this.board.length; i++) {
            //System.out.println("i is: " + i + ", " + board.board[i][board.board.length - i - 1]);
            if(this.board[i][this.board.length - i - 1] != role) {
                line_found = false;
                break;
            }

        }
        // System.out.println();
        return line_found;
    }

    public String print() {
        StringBuilder result = new StringBuilder();
        result.append("# Current board is: \n");
        result.append("      Column:  1 2 3\n");
        for(int i = 0; i < 3; i++) {
            StringBuilder str= new StringBuilder("      Row:  " + (i+1) + " ");
            for(int j = 0; j < 3; j++) {
                if(j == 0) {
                    str.append(" ").append(board[i][j]);

                } else {
                    str.append("|").append(board[i][j]);
                }

            }
            result.append(str).append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;

        // Check if o is an instance of Complex or not
        // "null instanceof [type]" also returns false
        if(!(o instanceof Board)) {
            return false;
        }

        Board b = (Board) o;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[i][j] != b.board[i][j])
                    return false;
            }
        }

        return true;
    }
}
