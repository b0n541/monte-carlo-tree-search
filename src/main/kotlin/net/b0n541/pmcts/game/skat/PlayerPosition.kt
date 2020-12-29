package net.b0n541.pmcts.game.skat

enum class PlayerPosition {
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
