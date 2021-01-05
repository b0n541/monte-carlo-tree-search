package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.mcts.GameMove
import net.b0n541.ai.mcts.GamePlayer
import net.b0n541.ai.mcts.GameState
import net.b0n541.ai.mcts.MonteCarloTreeSearch.run
import net.b0n541.ai.mcts.Tree
import org.slf4j.LoggerFactory
import java.time.Duration

open class TicTacToePlayer(val playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) : GamePlayer<TicTacToeMove> {

    private var gameState: TicTacToeGameState

    open fun play(): TicTacToeMove {
        val tree = Tree(gameState as GameState<GameMove>)
        play(tree, Duration.ofSeconds(1))

        //play(tree, 200)
        //tree.printDigraph()

        return tree.bestMove as TicTacToeMove
    }

    override fun addMove(move: TicTacToeMove) {
        gameState = gameState.addMove(move)
    }

    override fun toShortString(): String {
        return playerSymbol.toString()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(TicTacToePlayer::class.java)
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

    init {
        gameState = TicTacToeGameState(firstPlayer)
    }
}