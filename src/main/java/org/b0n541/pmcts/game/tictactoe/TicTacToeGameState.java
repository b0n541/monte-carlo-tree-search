package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class TicTacToeGameState implements GameState<TicTacToeMove> {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToeGameState.class);

    private final PlayerSymbol gameStatePlayer;
    private final TicTacToeBoard board;

    public TicTacToeGameState(final PlayerSymbol gameStatePlayer, final PlayerSymbol firstPlayer) {
        this.gameStatePlayer = gameStatePlayer;
        board = new TicTacToeBoard(firstPlayer);
    }

    private TicTacToeGameState(final PlayerSymbol gameStatePlayer, final TicTacToeBoard oldBoard, final TicTacToeMove nextMove) {
        this(gameStatePlayer, oldBoard.getMoves().size() > 0 ? oldBoard.getMoves().get(0).playerSymbol : oldBoard.getNextPlayer());
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
        return new TicTacToeGameState(gameStatePlayer, board, move);
    }

    @Override
    public boolean isGameFinished() {
        return board.isFinished();
    }

    @Override
    public double getGameResult() {
        switch (board.getGameResult()) {
            case O_WON:
                if (gameStatePlayer == PlayerSymbol.O) {
                    return 1.0;
                }
                return 0.0;
            case X_WON:
                if (gameStatePlayer == PlayerSymbol.X) {
                    return 1.0;
                }
                return 0.0;
            default:
                return 0.5;
        }
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }

    @Override
    public String toString() {
        return board.format();
    }
}
