package net.b0n541.ai.game.skat

import net.b0n541.ai.mcts.GamePlayer

class SkatPlayer : GamePlayer<SkatGameMove> {

    private val hand: Hand = Hand()

    fun takeCards(cards: Set<OpenCard>) {
        hand.takeCards(cards.toList())
    }

    fun playCard(): OpenCard {
        val card = hand.openCards.random()
        hand.removeCard(card)
        return card
    }

    override fun toShortString(): String {
        TODO("Not yet implemented")
    }

    override fun addMove(move: SkatGameMove) {
        TODO("Not yet implemented")
    }
}