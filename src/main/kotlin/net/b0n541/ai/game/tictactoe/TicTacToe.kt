package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.game.tictactoe.player.TicTacToePlayer
import org.slf4j.LoggerFactory

class TicTacToe(val noughtsPlayer: TicTacToePlayer, val crossesPlayer: TicTacToePlayer) {

    private val LOG = LoggerFactory.getLogger(TicTacToe::class.java)
    private var gameState = TicTacToeGameState()

    fun play() {
        var nextPlayer = TicTacToePlayerSymbol.O
        noughtsPlayer.startNewGame(TicTacToePlayerSymbol.O, TicTacToePlayerSymbol.O)
        crossesPlayer.startNewGame(TicTacToePlayerSymbol.X, TicTacToePlayerSymbol.O)

        do {
            LOG.info("Next player: {}", nextPlayer)
            var move: TicTacToeMove? = null
            if (nextPlayer == TicTacToePlayerSymbol.O) {
                move = noughtsPlayer.play()
            } else if (nextPlayer == TicTacToePlayerSymbol.X) {
                move = crossesPlayer.play()
            }
            gameState = gameState.addMove(move!!)
            noughtsPlayer.addMove(move)
            crossesPlayer.addMove(move)
            if (nextPlayer == TicTacToePlayerSymbol.O) {
                nextPlayer = TicTacToePlayerSymbol.X
            } else if (nextPlayer == TicTacToePlayerSymbol.X) {
                nextPlayer = TicTacToePlayerSymbol.O
            }
            LOG.info(gameState.toString())
        } while (!gameState.isGameFinished)
        LOG.info("--> {}", gameState.gameValues)
    }

    fun getGameValues() = gameState.gameValues
}