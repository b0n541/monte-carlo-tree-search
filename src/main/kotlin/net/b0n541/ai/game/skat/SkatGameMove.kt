package net.b0n541.ai.game.skat

import net.b0n541.ai.mcts.GameMove

data class SkatGameMove(val player: PlayerPosition, val card: OpenCard) : GameMove {

    fun beats(move: SkatGameMove): Boolean {
        val otherCard = move.card
        return beats(otherCard, card)
    }

    override fun toShortString(): String {
        return "$player $card"
    }

    companion object {
        private fun beats(leadingCard: OpenCard, followingCard: OpenCard): Boolean {
            if (isTrumpCard(leadingCard)) {
                if (followingCard.rank == Rank.JACK && leadingCard.rank == Rank.JACK) {
                    return followingCard.suit.rank > leadingCard.suit.rank
                }
            } else {
                if (isTrumpCard(followingCard)) {
                    return true
                }
            }
            return followsSuitAndBeats(leadingCard, followingCard)
        }

        private fun followsSuitAndBeats(leadingCard: OpenCard, followingCard: OpenCard): Boolean {
            return followingCard.suit == leadingCard.suit && followingCard.rank.suitGameRank > leadingCard.rank.suitGameRank
        }

        private fun isTrumpCard(card: OpenCard): Boolean {
            // TODO this needs to be parameterizable
            return card.suit == Suit.CLUBS
        }
    }
}