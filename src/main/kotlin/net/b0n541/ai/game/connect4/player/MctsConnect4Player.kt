package net.b0n541.ai.game.connect4.player

import net.b0n541.ai.game.connect4.Connect4Move
import net.b0n541.ai.game.connect4.Connect4PlayerSymbol
import net.b0n541.ai.mcts.*
import org.slf4j.LoggerFactory
import java.time.Duration

class MctsConnect4Player(playerSymbol: Connect4PlayerSymbol, firstPlayer: Connect4PlayerSymbol) :
    AbstractConnect4Player(playerSymbol, firstPlayer) {

    companion object {
        private val LOG = LoggerFactory.getLogger(MctsConnect4Player::class.java)
    }

    override fun play(): Connect4Move {
        val tree = Tree(gameState as GameState<GameMove>)

        play(tree, Duration.ofSeconds(1))

        return tree.bestMove as Connect4Move
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