package org.b0n541.pmcts.game.tictactoe;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class TicTacToePlayer {

    public final PlayerSymbol playerSymbol;

    public TicTacToePlayer(final PlayerSymbol playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public static TicTacToeMove play(final List<TicTacToeMove> possibleMoves) {
        return possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
    }
}
