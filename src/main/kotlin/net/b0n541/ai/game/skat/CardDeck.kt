package net.b0n541.ai.game.skat

class CardDeck(initialCards: Set<OpenCard> = ALL_32_CARDS) {
    init {
        require(initialCards.size == 32) { "Card deck needs to have a size of 32 cards." }
    }

    private val cards = initialCards.toMutableList()

    val sortedCards
        get() = cards.toList()

    fun shuffle() {
        cards.shuffle()
    }

    fun dealCards(player: PlayerPosition): Set<OpenCard> {
        return when (player) {
            PlayerPosition.FOREHAND -> cards.subList(0, 10).toSet()
            PlayerPosition.MIDDLEHAND -> cards.subList(10, 20).toSet()
            PlayerPosition.REARHAND -> cards.subList(20, 30).toSet()
        }
    }

    fun dealSkat(): Set<OpenCard> {
        return cards.subList(30, 32).toSet()
    }
}

val PERFECT_CARD_DISTRIBUTION = CardDeck(
    setOf(
        CJ, SJ, HJ, DJ, CA, CT, C9, SA, ST, S9,
        CK, CQ, SK, SQ, HA, HK, HQ, DT, D9, D8,
        C8, C7, S8, S7, HT, H9, H8, DA, DK, DQ,
        H7, D7
    )
)
