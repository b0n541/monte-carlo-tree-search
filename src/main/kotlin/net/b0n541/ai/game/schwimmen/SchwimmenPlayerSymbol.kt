package net.b0n541.ai.game.schwimmen

import net.b0n541.ai.game.common.PlayerSymbol

enum class SchwimmenPlayerSymbol : PlayerSymbol {
    ONE, TWO, THREE;

    val nextPlayer: SchwimmenPlayerSymbol
        get() {
            return when (this) {
                ONE -> TWO
                TWO -> THREE
                THREE -> ONE
            }
        }
}