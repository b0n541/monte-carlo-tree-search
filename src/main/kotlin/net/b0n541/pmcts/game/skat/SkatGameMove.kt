package net.b0n541.pmcts.game.skat

import net.b0n541.pmcts.mcts.GameMove

data class SkatGameMove(val player: PlayerPosition, val card: Card) : GameMove {

    fun beats(move: SkatGameMove): Boolean {
        val otherCard = move.card
        return beats(otherCard, card)
    }

    override fun toShortString(): String {
        return "$player $card"
    }

    companion object {
        private fun beats(leadingCard: Card, followingCard: Card): Boolean {
            if (isTrumpCard(leadingCard)) {
                if (followingCard.rank == Card.Rank.JACK && leadingCard.rank == Card.Rank.JACK) {
                    return followingCard.suit.rank > leadingCard.suit.rank
                }
            } else {
                if (isTrumpCard(followingCard)) {
                    return true
                }
            }
            return followsSuitAndBeats(leadingCard, followingCard)
        }

        private fun followsSuitAndBeats(leadingCard: Card, followingCard: Card): Boolean {
            return followingCard.suit == leadingCard.suit && followingCard.rank.suitGameRank > leadingCard.rank.suitGameRank
        }

        private fun isTrumpCard(card: Card): Boolean {
            // TODO this needs to be parameterizable
            return card.suit == Card.Suit.CLUBS
        }
    }
}