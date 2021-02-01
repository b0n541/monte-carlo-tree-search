package net.b0n541.ai.game.tictactoe.player.random

import net.b0n541.ai.game.tictactoe.PlayerSymbol
import net.b0n541.ai.game.tictactoe.TicTacToeMove
import net.b0n541.ai.game.tictactoe.player.AbstractTicTacToePlayer

class RandomTicTacToePlayer(playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    AbstractTicTacToePlayer(playerSymbol, firstPlayer) {

    override fun play(): TicTacToeMove {
        return gameState.possibleMoves.random()
    }
}