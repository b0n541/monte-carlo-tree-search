package org.b0n541.pmcts.game.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicTacToe {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToe.class);

    public static void playGame() {

        TicTacToeGameState gameState = new TicTacToeGameState(PlayerSymbol.O);
        final TicTacToePlayer noughtsPlayer = new TicTacToePlayer(PlayerSymbol.O, PlayerSymbol.O);
        final TicTacToePlayer crossesPlayer = new TicTacToePlayer(PlayerSymbol.X, PlayerSymbol.O);

        do {
            final PlayerSymbol nextPlayer = gameState.getNextPlayer();
            LOG.info("Next player: {} index {}", nextPlayer, nextPlayer.ordinal());

            TicTacToeMove move = null;
            if (nextPlayer == PlayerSymbol.O) {
                move = noughtsPlayer.play();
            } else if (nextPlayer == PlayerSymbol.X) {
                move = crossesPlayer.play();
            }

            gameState = gameState.addMove(move);
            noughtsPlayer.addMove(move);
            crossesPlayer.addMove(move);

            LOG.info(gameState.toString());

        } while (!gameState.isGameFinished());

        LOG.info("--> {}", gameState.getGameValues());
    }
}
