package net.b0n541.ai.game.schwimmen

import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.card.OpenCard

data class SchwimmenMove(
    val player: SchwimmenPlayerSymbol,
    val move: SchwimmenMoveType,
    val ownCards: List<OpenCard> = emptyList(),
    val middleCards: List<OpenCard> = emptyList()
) : GameMove {
    override fun toShortString(): String {
        return when (move) {
            SchwimmenMoveType.ONE_CARD_SWAP -> "One card swap: $ownCards <-> $middleCards"
            SchwimmenMoveType.THREE_CARD_SWAP -> "Three card swap: $ownCards <-> $middleCards"
            SchwimmenMoveType.SHOVE -> "Shove"
            SchwimmenMoveType.CLOSE -> "Close"
            SchwimmenMoveType.THIRTY_ONE -> "Thirty one: $ownCards"
            SchwimmenMoveType.FIRE -> "Fire: $ownCards"
        }
    }
}

enum class SchwimmenMoveType {
    ONE_CARD_SWAP,
    THREE_CARD_SWAP,
    CLOSE,
    SHOVE,
    THIRTY_ONE,
    FIRE
}