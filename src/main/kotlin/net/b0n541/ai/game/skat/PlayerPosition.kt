package net.b0n541.ai.game.skat

import net.b0n541.ai.game.common.PlayerSymbol

enum class PlayerPosition : PlayerSymbol {
    FOREHAND, MIDDLEHAND, REARHAND;

    val nextPlayer: PlayerPosition
        get() {
            return when (this) {
                FOREHAND -> MIDDLEHAND
                MIDDLEHAND -> REARHAND
                REARHAND -> FOREHAND
            }
        }
}
