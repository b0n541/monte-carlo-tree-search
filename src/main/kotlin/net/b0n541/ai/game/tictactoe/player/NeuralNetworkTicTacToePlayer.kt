package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.tictactoe.TicTacToeMove

class NeuralNetworkTicTacToePlayer : TicTacToePlayer() {
    override fun play(): TicTacToeMove {

        return TicTacToeMove(playerSymbol, 0, 0)
    }
}