package net.b0n541.pmcts.game.skat

import java.util.*

class SkatPlayer {
    private val hand: MutableList<Card> = ArrayList()

    fun takeCards(cards: List<Card>) {
        hand.addAll(cards)
    }

    fun playCard(): Card {
        val card = hand.random()
        hand.remove(card)
        return card
    }
}