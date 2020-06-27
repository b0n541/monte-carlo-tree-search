package org.b0n541.pmcts.game.tictactoe;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class TicTacToePlayer {

    public final PlayerSymbol playerSymbol;
    private TicTacToeGameState gameState;

    public TicTacToePlayer(final PlayerSymbol playerSymbol, final PlayerSymbol firstPlayer) {
        this.playerSymbol = playerSymbol;
        gameState = new TicTacToeGameState(playerSymbol, firstPlayer);
    }

    public TicTacToeMove play() {
        final List<TicTacToeMove> possibleMoves = gameState.getPossibleMoves();
        return possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
    }

    public void addMove(final TicTacToeMove move) {
        gameState = gameState.addMove(move);
    }
}
