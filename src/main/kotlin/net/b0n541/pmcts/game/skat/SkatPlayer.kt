package net.b0n541.pmcts.game.skat

import java.util.*

class SkatPlayer {

    private val hand: MutableList<OpenCard> = ArrayList()

    fun takeCards(cards: List<OpenCard>) {
        hand.addAll(cards)
    }

    fun playCard(): OpenCard {
        val card = hand.random()
        hand.remove(card)
        return card
    }
}