package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.MonteCarloTreeSearch;
import org.b0n541.pmcts.mcts.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class TicTacToePlayer {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToePlayer.class);

    public final PlayerSymbol playerSymbol;
    private TicTacToeGameState gameState;

    public TicTacToePlayer(final PlayerSymbol playerSymbol, final PlayerSymbol firstPlayer) {
        this.playerSymbol = playerSymbol;
        gameState = new TicTacToeGameState(playerSymbol, playerSymbol == PlayerSymbol.O ? PlayerSymbol.X : PlayerSymbol.O, firstPlayer);
    }

    public TicTacToeMove play() {
        final Tree tree = new Tree(gameState);

        play(tree, Duration.ofSeconds(2));
        //play(tree, 200);

        //tree.printDigraph();

        return (TicTacToeMove) tree.getBestMove(playerSymbol.ordinal());
    }

    private static void play(final Tree tree, final int maxRounds) {
        long rounds = 0;
        do {
            MonteCarloTreeSearch.run(tree, 1);
            rounds++;
        } while (rounds < maxRounds);

        LOG.info("Tree search finished. Rounds: {}, Tree size: {}", rounds, tree.getSize());
    }

    private static void play(final Tree tree, final Duration duration) {
        final long finishTime = System.nanoTime() + duration.toNanos();
        long rounds = 0;
        do {
            MonteCarloTreeSearch.run(tree, 1);
            rounds++;
        } while (System.nanoTime() < finishTime);

        LOG.info("Tree search finished. Rounds: {}, Tree size: {}", rounds, tree.getSize());
    }

    public void addMove(final TicTacToeMove move) {
        gameState = gameState.addMove(move);
    }
}
