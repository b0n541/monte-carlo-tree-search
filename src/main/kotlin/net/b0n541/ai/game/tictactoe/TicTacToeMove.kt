package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.game.common.GameMove

data class TicTacToeMove(val ticTacToePlayerSymbol: TicTacToePlayerSymbol, val row: Int, val column: Int) : GameMove {

    override fun toShortString(): String {
        return "[$row,$column]"
    }
}