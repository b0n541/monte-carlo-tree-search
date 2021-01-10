package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.tictactoe.PlayerSymbol
import net.b0n541.ai.game.tictactoe.TicTacToeMove

class RandomTicTacToePlayer(playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    AbstractTicTacToePlayer(playerSymbol, firstPlayer) {

    override fun play(): TicTacToeMove {
        return gameState.possibleMoves.random()
    }
}