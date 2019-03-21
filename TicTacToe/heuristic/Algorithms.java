package heuristic;

import game.Board;

public class Algorithms {

    private Algorithms() {}

    public static void miniMax (Board board) {

        MiniMax.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    // ply the maximum depth
    public static void miniMax (Board board, int ply) {
        MiniMax.run(board.getTurn(), board, ply);
    }

    public static void alphaBetaPruning (Board board) {
        AlphaBetaPruning.run(board.getTurn(), board, Double.POSITIVE_INFINITY, 0, 3);
    }
}
