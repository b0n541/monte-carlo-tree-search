package net.b0n541.ai.game.common.card

open class Hand(initialCards: List<Card> = listOf()) {
    private val handCards = mutableListOf<Card>()

    init {
        addCards(initialCards)
    }

    val cards: List<Card>
        get() = handCards.toList()

    fun addCards(newCards: List<Card>) {
        handCards.addAll(newCards)
    }

    fun removeCards(cardsToRemove: List<Card>) {
        handCards.removeAll(cardsToRemove)
    }
}