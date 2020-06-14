package org.b0n541.pmcts.game.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicTacToe {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToe.class);

    private final TicTacToeGameState gameState = new TicTacToeGameState();
    private final TicTacToePlayer playerOne = new TicTacToePlayer(PlayerSymbol.O);
    private final TicTacToePlayer playerTwo = new TicTacToePlayer(PlayerSymbol.X);

    private TicTacToePlayer currentPlayer = playerOne;

    public void playGame() {

        do {
            final TicTacToeMove move = currentPlayer.play(gameState.getPossibleMoves(currentPlayer.playerSymbol));

            gameState.addMove(move);

            switchPlayer();

        } while (!gameState.isFinished());

        LOG.info("--> {}", gameState.getGameResult());
    }

    private void switchPlayer() {
        if (currentPlayer.playerSymbol == PlayerSymbol.O) {
            currentPlayer = playerTwo;
        } else if (currentPlayer.playerSymbol == PlayerSymbol.X) {
            currentPlayer = playerOne;
        }
    }
}
