package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.algorithm.mcts.MonteCarloTreeSearch
import net.b0n541.ai.algorithm.mcts.RandomPlayoutPolicy
import net.b0n541.ai.algorithm.mcts.tree.Tree
import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.GameState
import net.b0n541.ai.game.tictactoe.TicTacToeMove
import org.slf4j.LoggerFactory
import java.time.Duration

class MctsTicTacToePlayer : TicTacToePlayer() {

    companion object {
        private val LOG = LoggerFactory.getLogger(MctsTicTacToePlayer::class.java)
    }

    override fun play(): TicTacToeMove {
        val tree = Tree(gameState as GameState<GameMove>)

        play(tree, Duration.ofSeconds(1))

//        play(tree, 200)
//        tree.printDigraph()

        return tree.bestMove as TicTacToeMove
    }

    private fun play(tree: Tree, maxRounds: Int) {
        var rounds: Long = 0
        do {
            MonteCarloTreeSearch.run(tree, 1, RandomPlayoutPolicy())
            rounds++
        } while (rounds < maxRounds)
        LOG.info("Tree search finished. Rounds: {}, Tree size: {}", rounds, tree.size)
    }

    private fun play(tree: Tree, duration: Duration) {
        val finishTime = System.nanoTime() + duration.toNanos()
        var rounds: Long = 0
        do {
            MonteCarloTreeSearch.run(tree, 1, RandomPlayoutPolicy())
            rounds++
        } while (System.nanoTime() < finishTime)
        LOG.info("Tree search finished. Rounds: {}, Tree size: {}", rounds, tree.size)
    }
}