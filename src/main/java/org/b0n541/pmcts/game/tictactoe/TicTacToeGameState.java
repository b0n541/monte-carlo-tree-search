package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class TicTacToeGameState implements GameState<TicTacToeMove> {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToeGameState.class);

    private final PlayerSymbol firstPlayer;
    private final TicTacToeBoard board;

    public TicTacToeGameState(final PlayerSymbol firstPlayer) {
        this.firstPlayer = firstPlayer;
        board = new TicTacToeBoard(firstPlayer);
    }

    private TicTacToeGameState(final TicTacToeBoard oldBoard, final TicTacToeMove nextMove) {
        this(oldBoard.getMoves().size() > 0 ? oldBoard.getMoves().get(0).playerSymbol : oldBoard.getNextPlayer());
        oldBoard.getMoves().forEach(move -> board.addMove(move));
        board.addMove(nextMove);
    }

    public PlayerSymbol getNextPlayer() {
        return board.getNextPlayer();
    }

    @Override
    public List<TicTacToeMove> getPossibleMoves() {
        return board.getPossibleMoves();
    }

    @Override
    public TicTacToeGameState addMove(final TicTacToeMove move) {
        return new TicTacToeGameState(board, move);
    }

    @Override
    public boolean isGameFinished() {
        return board.isFinished();
    }

    @Override
    public double getGameResult() {
        double result = 0.0;
        switch (board.getGameResult()) {
            case O_WON:
                if (PlayerSymbol.O == firstPlayer) {
                    result = 1.0;
                } else {
                    result = 0.0;
                }
                break;
            case X_WON:
                if (PlayerSymbol.X == firstPlayer) {
                    result = 1.0;
                } else {
                    result = 0.0;
                }
                break;
            default:
                result = 0.5;
        }
        return result;
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }

    @Override
    public int getRootPlayerIndex() {
        return firstPlayer.ordinal();
    }

    @Override
    public int getPlayerIndex() {
        return board.getNextPlayer().ordinal();
    }

    @Override
    public String toString() {
        return board.format();
    }
}
