package net.b0n541.ai.game.connect4

import net.b0n541.ai.game.connect4.player.Connect4Player
import org.slf4j.LoggerFactory

class Connect4(val noughtsPlayer: Connect4Player, val crossesPlayer: Connect4Player) {

    private val LOG = LoggerFactory.getLogger(Connect4::class.java)

    fun playGame() {
        var gameState = Connect4GameState()

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