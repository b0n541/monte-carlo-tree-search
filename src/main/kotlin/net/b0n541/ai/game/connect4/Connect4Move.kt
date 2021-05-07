package net.b0n541.ai.game.connect4

import net.b0n541.ai.game.common.GameMove

data class Connect4Move(val player: Connect4PlayerSymbol, val column: Int) : GameMove {
    override fun toShortString(): String {
        return "$player -> $column"
    }
}