package net.b0n541.pmcts.game.skat

class Hand(initialCards: List<Card> = listOf()) {
    private val handCards = mutableListOf<Card>()
    var cardValues = 0
        private set

    init {
        takeCards(initialCards)
    }

    /**
     * Returns all cards on the hand.
     */
    val cards: List<Card>
        get() = handCards.toList()

    /**
     * Returns all open cards on the hand.
     */
    val openCards: Set<OpenCard>
        get() = handCards.filterIsInstance<OpenCard>().toSet()

    /**
     * Returns true if the hand hold hidden cards.
     */
    val holdsHiddenCards
        get() = handCards.contains(HiddenCard)

    /**
     * Gets all trump cards for [gameType].
     */
    fun getTrumpCards(gameType: SkatGameType): Set<OpenCard> = openCards.filter { it.isTrump(gameType) }.toSet()

    /**
     * Takes a list of [newCards] and adjusts the [cardValues] of the hand.
     */
    fun takeCards(newCards: List<Card>) {
        handCards.addAll(newCards)

        newCards.forEach {
            if (it is OpenCard) {
                cardValues += it.value
            }
        }
    }

    /**
     * Removes a [card] and adjusts the [cardValues] of the hand.
     */
    fun removeCard(card: Card) {
        handCards.remove(card)

        if (card is OpenCard) {
            cardValues -= card.value
        }
    }
}