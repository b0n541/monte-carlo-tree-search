package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.GameMove;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class TicTacToePlayer {

    public final PlayerSymbol playerSymbol;

    public TicTacToePlayer(final PlayerSymbol playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public static GameMove play(final List<GameMove> possibleMoves) {
        return possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
    }
}
