package net.b0n541.pmcts.game.skat

class Hand(initialCards: List<Card> = listOf()) {
    val cards = mutableListOf<Card>()
    var cardValues = 0
        private set

    init {
        takeCards(initialCards)
    }

    val containsHiddenCards
        get() = cards.contains(HiddenCard)

    fun takeCards(newCards: List<Card>) {
        cards.addAll(newCards)

        newCards.forEach {
            if (it is OpenCard) {
                cardValues += it.value
            }
        }
    }

    fun removeCard(card: Card) {
        cards.remove(card)

        if (card is OpenCard) {
            cardValues -= card.value
        }
    }
}