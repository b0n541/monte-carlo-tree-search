package net.b0n541.ai.game.skat

import net.b0n541.ai.game.common.GamePlayer

class SkatPlayer : GamePlayer<PlayerPosition, SkatMove> {

    private lateinit var playerPosition: PlayerPosition
    private var hand: Hand = Hand()

    override fun startNewGame(playerPosition: PlayerPosition, firstPlayer: PlayerPosition) {
        this.playerPosition = playerPosition
        this.hand = Hand()
    }

    fun takeCards(cards: Set<OpenCard>) {
        hand.takeCards(cards.toList())
    }

    override fun play(): SkatMove {
        return SkatMove(playerPosition!!, playCard())
    }

    private fun playCard(): OpenCard {
        val card = hand.openCards.random()
        hand.removeCard(card)
        return card
    }

    override fun toShortString(): String {
        TODO("Not yet implemented")
    }

    override fun addMove(move: SkatMove) {
        TODO("Not yet implemented")
    }
}