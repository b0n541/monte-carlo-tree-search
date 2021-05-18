package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.tictactoe.TicTacToeMove

class RandomTicTacToePlayer : TicTacToePlayer() {

    override fun play(): TicTacToeMove {
        return gameState.possibleMoves.random()
    }
}