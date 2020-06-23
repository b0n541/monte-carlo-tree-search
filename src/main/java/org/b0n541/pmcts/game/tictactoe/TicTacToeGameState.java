package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.GameMove;
import org.b0n541.pmcts.mcts.GameState;

import java.util.List;

public final class TicTacToeGameState implements GameState {

    private final TicTacToeBoard board = new TicTacToeBoard();

    @Override
    public List<GameMove> getPossibleMoves() {
        return board.getPossibleMoves(PlayerSymbol.X);
    }

    @Override
    public GameState addMove(final GameMove move) {
        return null;
    }

    @Override
    public boolean isGameFinished() {
        return board.isFinished();
    }

    @Override
    public double getGameResult() {
        return 0;
    }
}
