package heuristic;

import game.Board;
import java.util.ArrayList;
import java.util.List;
class AlphaBetaPruning {

    private static double maxDepth;
    private static int currentIndex = -1;

    private AlphaBetaPruning () {}

    static void run (Board.State player, Board board, double maxDepth, int depth, int maxDp) {
        if (maxDp< 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        AlphaBetaPruning.maxDepth = maxDepth;
        alphaBetaPruning(player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, depth, maxDp);
    }

    public static int getIndex() {
    	return currentIndex;
    }

    private static int alphaBetaPruning (Board.State player, Board board, double alpha, double beta, int currentDepth, int depth, int maxDp) {
        if (currentDepth++ == maxDp || board.isGameOver() || maxDp == depth) {
            return score(player, board, currentDepth);
        }
        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentDepth, depth, maxDp);
        } else {
            return getMin(player, board, alpha, beta, currentDepth, depth, maxDp);
        }
    }

   private static int getMax (Board.State player, Board board, double alpha, double beta, int currentPly, int depth, int maxDepth) {
        int indexOfBestMove = -1;
//        if (depth > maxDepth) {
//            return (int)beta;
//        }
        List<Integer> moves = new ArrayList<>();
        List<Integer> newMoves = new ArrayList<>();
        for (Integer theMove : board.getAvailableMoves()) {
            moves.add(theMove);
        }
        int totalNum = moves.size() - 1;
        int left = totalNum / 2;
        int right = left + 1;
        while (left >= 0 || right <= totalNum) {
            if (left >= 0) {
                newMoves.add(moves.get(left));
                left--;
            }
            if (right <= totalNum) {
                newMoves.add(moves.get(right));
                right++;
            }
        }
        for (Integer theMove : newMoves) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);
            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentPly, depth + 1, maxDepth);
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
        	currentIndex = indexOfBestMove;
            board.move(indexOfBestMove);
        }
        currentIndex = indexOfBestMove;
        return (int)alpha;
    }
    /**
     * Play the move with the lowest score.
     * @param player        the player that the AI will identify as
     * @param board         the Tic Tac Toe board to play on
     * @param alpha         the alpha value
     * @param beta          the beta value
     * @param currentPly    the current depth
     * @return              the score of the board
     */
    private static int getMin (Board.State player, Board board, double alpha, double beta, int currentPly, int depth, int maxDepth) {
        int indexOfBestMove = -1;
//        if (depth > maxDepth) {
//            return (int)beta;
//        }
        List<Integer> moves = new ArrayList<>();
        List<Integer> newMoves = new ArrayList<>();
        for (Integer theMove : board.getAvailableMoves()) {
            moves.add(theMove);
        }
        int totalNum = moves.size() - 1;
        int left = totalNum / 2;
        int right = left + 1;
        while (left >= 0 || right <= totalNum) {
            if (left >= 0) {
                newMoves.add(moves.get(left));
                left--;
            }
            if (right <= totalNum) {
                newMoves.add(moves.get(right));
                right++;
            }
        }
        for (Integer theMove : newMoves) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentPly, depth + 1, maxDepth);

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
        	currentIndex = indexOfBestMove;
            board.move(indexOfBestMove);
        }
        currentIndex = indexOfBestMove;
        return (int)beta;
    }

    private static int score (Board.State player, Board board, int currentDepth) {
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
