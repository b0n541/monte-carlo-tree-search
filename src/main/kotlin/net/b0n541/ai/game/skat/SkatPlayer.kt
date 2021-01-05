package net.b0n541.ai.game.skat

class SkatPlayer {

    private val hand: Hand = Hand()

    fun takeCards(cards: Set<OpenCard>) {
        hand.takeCards(cards.toList())
    }

    fun playCard(): OpenCard {
        val card = hand.openCards.random()
        hand.removeCard(card)
        return card
    }
}