package net.b0n541.pmcts.game.skat

class CardDeck {
    private val cards = listOf(
        CA, CK, CQ, CJ, CT, C9, C8, C7,
        SA, SK, SQ, SJ, ST, S9, S8, S7,
        HA, HK, HQ, HJ, HT, H9, H8, H7,
        DA, DK, DQ, DJ, DT, D9, D8, D7
    )

    fun shuffle(): MutableList<OpenCard> {
        val shuffledCards = cards.toMutableList()
        shuffledCards.shuffle()
        return shuffledCards
    }
}