package net.b0n541.ai.game.connect4

import net.b0n541.ai.game.connect4.player.alphazero.AlphaZeroConnect4Player
import net.b0n541.ai.game.connect4.player.simple.MctsConnect4Player
import org.slf4j.LoggerFactory

object Connect4 {

    private val LOG = LoggerFactory.getLogger(Connect4::class.java)

    @JvmStatic
    fun playGame() {
        var gameState = Connect4GameState(Connect4PlayerSymbol.O)
        val noughtsPlayer = AlphaZeroConnect4Player(Connect4PlayerSymbol.O, Connect4PlayerSymbol.O)
        val crossesPlayer = MctsConnect4Player(Connect4PlayerSymbol.X, Connect4PlayerSymbol.O)

        LOG.info(gameState.toString())

        do {
            LOG.info("Next player: {}", gameState.nextPlayer)

            var move: Connect4Move = if (gameState.nextPlayer == Connect4PlayerSymbol.O.toString()) {
                noughtsPlayer.play()
            } else {
                crossesPlayer.play()
            }

            gameState = gameState.addMove(move)
            noughtsPlayer.addMove(move)
            crossesPlayer.addMove(move)

            LOG.info(gameState.toString())
        } while (!gameState.isGameFinished)

        LOG.info("--> {}", gameState.gameValues)
    }
}