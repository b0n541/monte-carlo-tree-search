package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.MonteCarloTreeSearch;
import org.b0n541.pmcts.mcts.Tree;

import java.time.Duration;

public final class TicTacToePlayer {

    public final PlayerSymbol playerSymbol;
    private TicTacToeGameState gameState;

    public TicTacToePlayer(final PlayerSymbol playerSymbol, final PlayerSymbol firstPlayer) {
        this.playerSymbol = playerSymbol;
        gameState = new TicTacToeGameState(playerSymbol, firstPlayer);
    }

    public TicTacToeMove play() {
        final Tree tree = new Tree(gameState);

        final Long finishTime = System.nanoTime() + Duration.ofSeconds(2).toNanos();
        do {
            MonteCarloTreeSearch.run(tree, 1);
        } while (System.nanoTime() < finishTime);

        return (TicTacToeMove) tree.getRootNode().getBestMove();
    }

    public void addMove(final TicTacToeMove move) {
        gameState = gameState.addMove(move);
    }
}
