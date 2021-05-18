package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.common.GamePlayer
import net.b0n541.ai.game.tictactoe.TicTacToeGameState
import net.b0n541.ai.game.tictactoe.TicTacToeMove
import net.b0n541.ai.game.tictactoe.TicTacToePlayerSymbol

sealed class TicTacToePlayer : GamePlayer<TicTacToePlayerSymbol, TicTacToeMove> {

    protected var playerSymbol: TicTacToePlayerSymbol? = null
    protected var gameState: TicTacToeGameState = TicTacToeGameState()

    override fun startNewGame(
        playerSymbol: TicTacToePlayerSymbol,
        firstPlayer: TicTacToePlayerSymbol
    ) {
        this.playerSymbol = playerSymbol
        gameState = TicTacToeGameState()
    }

    override fun addMove(move: TicTacToeMove) {
        gameState = gameState.addMove(move)
    }

    override fun toShortString(): String {
        return playerSymbol.toString()
    }
}