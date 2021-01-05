package net.b0n541.pmcts.game.tictactoe

import org.slf4j.LoggerFactory

object TicTacToe {

    private val LOG = LoggerFactory.getLogger(TicTacToe::class.java)

    @JvmStatic
    fun playGame() {
        var gameState = TicTacToeGameState(PlayerSymbol.O)
        val noughtsPlayer = TicTacToePlayer(PlayerSymbol.O, PlayerSymbol.O)
        val crossesPlayer = TicTacToePlayer(PlayerSymbol.X, PlayerSymbol.O)
        do {
            val nextPlayer = PlayerSymbol.O
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
            LOG.info(gameState.toString())
        } while (!gameState.isGameFinished)
        LOG.info("--> {}", gameState.gameValues)
    }
}