package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.game.GameState;

import java.util.List;

public final class TicTacToeGameState implements GameState<TicTacToeMove> {

    private final TicTacToeBoard board = new TicTacToeBoard();

    @Override
    public List<TicTacToeMove> getPossibleMoves(final PlayerSymbol playerSymbol) {
        return board.getPossibleMoves(playerSymbol);
    }

    @Override
    public void addMove(final TicTacToeMove move) {
        board.addMove(move);
    }

    @Override
    public boolean isFinished() {
        return board.isFinished();
    }

    @Override
    public GameResult getGameResult() {
        return board.getGameResult();
    }
}
