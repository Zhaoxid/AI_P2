package game;

import java.util.Scanner;

import heuristic.Algorithms;

public class Console {

    private Board board;
    private Scanner sc = new Scanner(System.in);

    public Board getBoard() {
    	return board;
    }
    
    public Console(int board_width, int parts_to_win) {
        board = new Board(board_width, parts_to_win);
    }

//    public void play () {
//
//        System.out.println("Starting a new game.");
//        Algorithms.alphaBetaPruning(board);
//        int index = 0, x, y;
//        while (true) {
//            printGameStatus();
//            index = getPlayerMove();
//            x = index % board.getBoardWidth();
//            y = index / board.getBoardWidth();
//            playMove(x, y);
//        }
//    }

    public String playMove (String moveString) {
    	int indexInt, x, y;
    	String indexStr;
    	if (moveString.equals("")) {
    		indexInt = Algorithms.alphaBetaPruning(board);
            x = indexInt % board.getBoardWidth();
            y = indexInt / board.getBoardWidth();
            indexStr = Integer.toString(x) + "," + Integer.toString(y);
            return indexStr;
    	}
    	String[] tokens = moveString.split(",");
    	x = Integer.parseInt(tokens[0]);
    	y = Integer.parseInt(tokens[1]);
        printGameStatus();
        int index = x + y * board.getBoardWidth();
        board.move(index);
        // Check if game is over.
        if (board.isGameOver())
        	System.out.println("Game is Over");
        if (board.isGameOver()) {
            printWinner();
            if (Board.State.Blank == board.getWinner()) {
            	return "DRAW";
            }
            else if (Board.State.X == board.getWinner()) {
            	return "WIN";
            }
            else {
            	return "LOSE";
            }
        }
        
        indexInt = Algorithms.alphaBetaPruning(board);
        
        x = indexInt % board.getBoardWidth();
        y = indexInt / board.getBoardWidth();
        indexStr = Integer.toString(x) + "," + Integer.toString(y);
        return indexStr;
    }

    public String getGameResult() {
        if (board.isGameOver())
            System.out.println("Game is Over");
        if (board.isGameOver()) {
            printWinner();
            if (Board.State.Blank == board.getWinner()) {
                return "DRAW";
            }
            else if (Board.State.X == board.getWinner()) {
                return "WIN";
            }
            else {
                return "LOSE";
            }
        }
        return "";
    }
    /**
     * Print out the board and the player who's turn it is.
     */
    public void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().name() + "'s turn.");
    }

    /**
     * For reading in and interpreting the move that the user types into the console.
     */
    private int getPlayerMove () {
        System.out.print("Index of move: ");

        int move = sc.nextInt();
        return move;
//        if (move < 0 || move >= board.getBoardWidth()* board.getBoardWidth()) {
//            System.out.println("\nInvalid move.");
//            System.out.println("\nThe index of the move must be between 0 and "
//                    + (board.getBoardWidth() * board.getBoardWidth() - 1) + ", inclusive.");
//        } else if (!board.move(move)) {
//            System.out.println("\nInvalid move.");
//            System.out.println("\nThe selected index must be blank.");
//        }
    }

    /**
     * Print out the winner of the game.
     */
    private void printWinner () {
        Board.State winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == Board.State.Blank) {
            System.out.println("The TicTacToe is a Draw.");
        } else {
            System.out.println("Player " + winner.toString() + " wins!");
        }
    }

    /**
     * Reset the game if the player wants to play again.
     * @return      true if the player wants to play again
     */
    private boolean tryAgain () {
        if (promptTryAgain()) {
            board.reset();
            System.out.println("Started new game.");
            System.out.println("X's turn.");
            return true;
        }

        return false;
    }

    /**
     * Ask the player if they want to play again.
     * @return      true if the player wants to play again
     */
    private boolean promptTryAgain () {
        while (true) {
            System.out.print("Would you like to start a new game? (Y/N): ");
            String response = sc.next();
            if (response.equalsIgnoreCase("y")) {
                return true;
            } else if (response.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Invalid input.");
        }
    }
}
