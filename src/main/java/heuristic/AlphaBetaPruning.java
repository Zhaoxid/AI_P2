package heuristic;

import game.Board;

class AlphaBetaPruning {

    private static double maxDepth;
    private static int currentIndex = -1;

    private AlphaBetaPruning () {}

    static void run (Board.State player, Board board, double maxDepth, int depth, int maxDepth) {
        if (maxDepth < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        AlphaBetaPruning.maxDepth = maxDepth;
        alphaBetaPruning(player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, depth, maxDepth);
    }

    public static int getIndex() {
    	return currentIndex;
    }

    private static int alphaBetaPruning (Board.State player, Board board, double alpha, double beta, int currentDepth, int depth, int maxDepth) {
        if (currentDepth++ == maxDepth || board.isGameOver() || maxDepth == depth) {
            return score(player, board, currentDepth);
        }
        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentDepth, depth, maxDepth);
        } else {
            return getMin(player, board, alpha, beta, currentDepth, depth, maxDepth);
        }
    }

    private static int getMax (Board.State player, Board board, double alpha, double beta, int currentDepth, int depth, int maxDepth) {
        //System.out.println("getMax depth = " + depth);
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);
            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentDepth, depth + 1, maxDepth);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = theMove;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
            currentIndex = indexOfBestMove;
        }

        return (int)alpha;
    }

    private static int getMin (Board.State player, Board board, double alpha, double beta, int currentDepth, int depth, int maxDepth) {
        //System.out.println("getMin depth = " + depth);
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentDepth, depth + 1, maxDepth);

            if (score < beta) {
                beta = score;
                indexOfBestMove = theMove;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
            currentIndex = indexOfBestMove;
        }
        currentIndex = indexOfBestMove;
        return (int)beta;
    }

    private static int score (Board.State player, Board board, int currentDepth) {

        if (player == Board.State.Blank) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Board.State opponent = (player == Board.State.X) ? Board.State.O : Board.State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return 10 - currentDepth;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10 + currentDepth;
        } else {
            return 0;
        }
    }

}
