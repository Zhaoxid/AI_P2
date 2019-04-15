package heuristic;

import game.Board;

public class Algorithms {

    private Algorithms() {}
    public static int alphaBetaPruning (Board board) {
        AlphaBetaPruning.run(board.getTurn(), board, Double.POSITIVE_INFINITY, 0, 5);
        return AlphaBetaPruning.getIndex();
    }
}
