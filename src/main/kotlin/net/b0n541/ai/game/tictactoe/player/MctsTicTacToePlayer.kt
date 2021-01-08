package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.tictactoe.PlayerSymbol
import net.b0n541.ai.game.tictactoe.TicTacToeMove
import net.b0n541.ai.mcts.GameMove
import net.b0n541.ai.mcts.GameState
import net.b0n541.ai.mcts.MonteCarloTreeSearch.run
import net.b0n541.ai.mcts.Tree
import org.slf4j.LoggerFactory
import java.time.Duration

class MctsTicTacToePlayer(playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    AbstractTicTacToePlayer(playerSymbol, firstPlayer) {


    override fun play(): TicTacToeMove {
        val tree = Tree(gameState as GameState<GameMove>)

        play(tree, Duration.ofSeconds(1))

//        play(tree, 200)
//        tree.printDigraph()

        return tree.bestMove as TicTacToeMove
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(MctsTicTacToePlayer::class.java)
        private fun play(tree: Tree, maxRounds: Int) {
            var rounds: Long = 0
            do {
                run(tree, 1)
                rounds++
            } while (rounds < maxRounds)
            LOG.info("Tree search finished. Rounds: {}, Tree size: {}", rounds, tree.size)
        }

        private fun play(tree: Tree, duration: Duration) {
            val finishTime = System.nanoTime() + duration.toNanos()
            var rounds: Long = 0
            do {
                run(tree, 1)
                rounds++
            } while (System.nanoTime() < finishTime)
            LOG.info("Tree search finished. Rounds: {}, Tree size: {}", rounds, tree.size)
        }
    }
}