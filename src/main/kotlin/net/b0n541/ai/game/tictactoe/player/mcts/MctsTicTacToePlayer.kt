package net.b0n541.ai.game.tictactoe.player.mcts

import net.b0n541.ai.game.tictactoe.PlayerSymbol
import net.b0n541.ai.game.tictactoe.TicTacToeMove
import net.b0n541.ai.game.tictactoe.player.AbstractTicTacToePlayer
import net.b0n541.ai.mcts.*
import org.slf4j.LoggerFactory
import java.time.Duration

class MctsTicTacToePlayer(playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    AbstractTicTacToePlayer(playerSymbol, firstPlayer) {

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