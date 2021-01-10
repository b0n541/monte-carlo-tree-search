package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.game.tictactoe.player.MctsTicTacToePlayer
import org.slf4j.LoggerFactory

object TicTacToe {

    private val LOG = LoggerFactory.getLogger(TicTacToe::class.java)

    @JvmStatic
    fun playGame() {
        var gameState = TicTacToeGameState(PlayerSymbol.O)
        val noughtsPlayer = MctsTicTacToePlayer(PlayerSymbol.O, PlayerSymbol.O)
        val crossesPlayer = MctsTicTacToePlayer(PlayerSymbol.X, PlayerSymbol.O)
        var nextPlayer = PlayerSymbol.O
        do {
            LOG.info("Next player: {}", nextPlayer)
            var move: TicTacToeMove? = null
            if (nextPlayer == PlayerSymbol.O) {
                move = noughtsPlayer.play()
            } else if (nextPlayer == PlayerSymbol.X) {
                move = crossesPlayer.play()
            }
            gameState = gameState.addMove(move!!)
            noughtsPlayer.addMove(move)
            crossesPlayer.addMove(move)
            if (nextPlayer == PlayerSymbol.O) {
                nextPlayer = PlayerSymbol.X
            } else if (nextPlayer == PlayerSymbol.X) {
                nextPlayer = PlayerSymbol.O
            }
            LOG.info(gameState.toString())
        } while (!gameState.isGameFinished)
        LOG.info("--> {}", gameState.gameValues)
    }
}