package heuristic;

import game.Board;

public class Algorithms {

    private Algorithms() {}

    public static void miniMax (Board board) {

        MiniMax.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    public static void miniMax (Board board, int ply) {
        MiniMax.run(board.getTurn(), board, ply);
    }

    public static int alphaBetaPruning (Board board) {
        AlphaBetaPruning.run(board.getTurn(), board, Double.POSITIVE_INFINITY, 0, 3);
        return AlphaBetaPruning.getIndex();
    }
}
