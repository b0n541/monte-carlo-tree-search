package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.common.GamePlayer
import net.b0n541.ai.game.tictactoe.PlayerSymbol
import net.b0n541.ai.game.tictactoe.TicTacToeGameState
import net.b0n541.ai.game.tictactoe.TicTacToeMove

abstract class AbstractTicTacToePlayer(val playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    GamePlayer<TicTacToeMove> {

    protected var gameState: TicTacToeGameState = TicTacToeGameState(firstPlayer)

    override fun addMove(move: TicTacToeMove) {
        gameState = gameState.addMove(move)
    }

    override fun toShortString(): String {
        return playerSymbol.toString()
    }
}