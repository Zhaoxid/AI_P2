package game;

import java.util.HashSet;

/**
 * Represents the Tic Tac Toe board.
 */
public class Board {

    private static int BOARD_WIDTH = 3;
    private static int PARTS_TO_WIN = 3;

    public enum State {Blank, X, O}
    private State[][] board;
    private State playersTurn;
    private State winner;
    private HashSet<Integer> movesAvailable;

    private int moveCount;
    private boolean gameOver;

    public Board(int board_width, int parts_to_win) {
    	BOARD_WIDTH = board_width;
    	PARTS_TO_WIN = parts_to_win;
        board = new State[BOARD_WIDTH][BOARD_WIDTH];
        movesAvailable = new HashSet<>();
        reset();
    }

    public int getBoardWidth() {
    	return BOARD_WIDTH;
    }
    
    public int getPartsToWin() {
    	return PARTS_TO_WIN;
    }
    
    /**
     * Set the cells to be blank and load the available moves (all the moves are
     * available at the start of the game).
     */
    private void initialize () {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = State.Blank;
            }
        }

        movesAvailable.clear();

        for (int i = 0; i < BOARD_WIDTH*BOARD_WIDTH; i++) {
            movesAvailable.add(i);
        }
    }

    /**
     * Restart the game with a new blank board.
     */
    void reset () {
        moveCount = 0;
        gameOver = false;
        playersTurn = State.X;
        winner = State.Blank;
        initialize();
    }

    /**
     * Places an X or an O on the specified index depending on whose turn it is.
     * @param index     the position on the board (example: index 4 is location (0, 1))
     * @return          true if the move has not already been played
     */
    public boolean move (int index) {
        return move(index% BOARD_WIDTH, index/ BOARD_WIDTH);
    }

    /**
     * Places an X or an O on the specified location depending on who turn it is.
     * @param x         the x coordinate of the location
     * @param y         the y coordinate of the location
     * @return          true if the move has not already been played
     */
    private boolean move (int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("TicTacToe is over. No moves can be played.");
        }

        if (board[y][x] == State.Blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(y * BOARD_WIDTH + x);

        // The game is a draw.
        if (moveCount == BOARD_WIDTH * BOARD_WIDTH) {
            winner = State.Blank;
            gameOver = true;
        }

        // Check for a winner.
        checkRow(x, y);
        checkColumn(x, y);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);

        playersTurn = (playersTurn == State.X) ? State.O : State.X;
        return true;
    }

    /**
     * Check to see if the game is over (if there is a winner or a draw).
     * @return          true if the game is over
     */
    public boolean isGameOver () {
        return gameOver;
    }

    /**
     * Get a copy of the array that represents the board.
     * @return          the board array
     */
    State[][] toArray () {
        return board.clone();
    }

    /**
     * Check to see who's turn it is.
     * @return          the player who's turn it is
     */
    public State getTurn () {
        return playersTurn;
    }

    /**
     * Check to see who won.
     * @return          the player who won (or Blank if the game is a draw)
     */
    public State getWinner () {
        if (!gameOver) {
            throw new IllegalStateException("TicTacToe is not over yet.");
        }
        return winner;
    }

    /**
     * Get the indexes of all the positions on the board that are empty.
     * @return          the empty cells
     */
    public HashSet<Integer> getAvailableMoves () {
        return movesAvailable;
    }

    /**
     * Checks the specified row to see if there is a winner.
     * @param row       the row to check
     */
    private void checkRow (int column, int row) {
        int left = 1, right = 1;
        for (int i = column; i > 0; i--) {
            if (board[row][i] != board[row][i - 1]) {
                break;
            }
            left++;
        }
        for (int i = column; i < BOARD_WIDTH - 1; i++) {
            if (board[row][i] != board[row][i + 1]) {
                break;
            }
            right++;
        }
        if (left + right - 1 >= PARTS_TO_WIN) {
            winner = playersTurn;
            gameOver = true;
        }
    }

    /**
     * Checks the specified column to see if there is a winner.
     * @param column    the column to check
     */
    private void checkColumn (int column, int row) {
        int up = 1, down = 1;
        for (int i = row; i > 0; i--) {
            if (board[i][column] != board[i - 1][column]) {
                break;
            }
            up++;
        }
        for (int i = row; i < BOARD_WIDTH - 1; i++) {
            if (board[i][column] != board[i + 1][column]) {
                break;
            }
            down++;
        }

        if (up + down - 1 >= PARTS_TO_WIN) {
            winner = playersTurn;
            gameOver = true;
        }
    }

    /**
     * Check the left diagonal to see if there is a winner.
     * @param x         the x coordinate of the most recently played move
     * @param y         the y coordinate of the most recently played move
     */
    private void checkDiagonalFromTopLeft (int column, int row) {
        int up = 1, down = 1;
        int x = row, y = column;
        while (x > 0 && y > 0 && board[x][y] == board[x - 1][y - 1]) {
            up++;
            x -= 1;
            y -= 1;
        }
        int j = row, k = column;
        while (j < BOARD_WIDTH - 1 && k < BOARD_WIDTH - 1 && board[j][k] == board[j + 1][k + 1]) {
            down++;
            j += 1;
            k += 1;
        }
        if (up + down - 1 >= PARTS_TO_WIN) {
            winner = playersTurn;
            gameOver = true;
        }

    }

    /**
     * Check the right diagonal to see if there is a winner.
     * @param x     the x coordinate of the most recently played move
     * @param y     the y coordinate of the most recently played move
     */
    private void checkDiagonalFromTopRight (int column, int row) {
        int up = 1, down = 1;
        int x = row, y = column;
        while (x > 0 && y < BOARD_WIDTH - 1 && board[x][y] == board[x - 1][y + 1]) {
            up++;
            x -= 1;
            y += 1;
        }
        int j = row, k = column;
        while (j < BOARD_WIDTH - 1 && k > 0 && board[j][k] == board[j + 1][k - 1]) {
            down++;
            j += 1;
            k -= 1;

        }
        if (up + down - 1 >= PARTS_TO_WIN) {
            winner = playersTurn;
            gameOver = true;
        }
    }

    /**
     * Get a deep copy of the Tic Tac Toe board.
     * @return      an identical copy of the board
     */
    public Board getDeepCopy () {
        Board board             = new Board(BOARD_WIDTH, PARTS_TO_WIN);

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.playersTurn       = this.playersTurn;
        board.winner            = this.winner;
        board.movesAvailable    = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount         = this.moveCount;
        board.gameOver          = this.gameOver;
        return board;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < BOARD_WIDTH; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {

                if (board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != BOARD_WIDTH -1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }

}
