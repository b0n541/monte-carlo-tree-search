package net.b0n541.pmcts.game.skat

class CardDeck(
    defaultCards: List<OpenCard> = listOf(
        CA, CK, CQ, CJ, CT, C9, C8, C7,
        SA, SK, SQ, SJ, ST, S9, S8, S7,
        HA, HK, HQ, HJ, HT, H9, H8, H7,
        DA, DK, DQ, DJ, DT, D9, D8, D7
    )
) {
    init {
        require(defaultCards.toSet().size == 32)
    }

    private val cards = defaultCards.toMutableList()

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
    listOf(
        CJ, SJ, HJ, DJ, CA, CT, C9, SA, ST, S9,
        CK, CQ, SK, SQ, HA, HK, HQ, DT, D9, D8,
        C8, C7, S8, S7, HT, H9, H8, DA, DK, DQ,
        H7, D7
    )
)
