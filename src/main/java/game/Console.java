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

    public void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().name() + "'s turn.");
    }

    private int getPlayerMove () {
        System.out.print("Index of move: ");

        int move = sc.nextInt();
        return move;
    }

    private void printWinner () {
        Board.State winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == Board.State.Blank) {
            System.out.println("The TicTacToe is a Draw.");
        } else {
            System.out.println("Player " + winner.toString() + " wins!");
        }
    }

}
