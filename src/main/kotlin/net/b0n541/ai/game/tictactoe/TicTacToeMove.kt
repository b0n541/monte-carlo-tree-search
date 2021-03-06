package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.mcts.GameMove

data class TicTacToeMove(val playerSymbol: PlayerSymbol, val row: Int, val column: Int) : GameMove {

    override fun toShortString(): String {
        return "[$row,$column]"
    }
}