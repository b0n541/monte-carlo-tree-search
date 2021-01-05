package net.b0n541.ai.game.skat

import org.slf4j.LoggerFactory

class Trick(
    private val trickForeHand: PlayerPosition,
    private val cards: MutableList<SkatGameMove> = mutableListOf(),
) {
    private val LOG = LoggerFactory.getLogger(javaClass)

    val leadingCard: OpenCard?
        get() = cards.getOrNull(0)?.card
    var cardValues: Int = 0
        private set
    var trickWinner: PlayerPosition? = null
        private set

    val isFinished: Boolean
        get() = cards.size == 3

    fun addMove(move: SkatGameMove) {

        require(cards.size < 3) { "Trick is already completed." }

        cards.add(move)
        cardValues += move.card.value

        if (isFinished) {
            trickWinner = trickForeHand
            if (cards[1].beats(cards[0])) {
                trickWinner = cards[1].player
                if (cards[2].beats(cards[1])) {
                    trickWinner = cards[2].player
                }
            } else if (cards[2].beats(cards[0])) {
                trickWinner = cards[2].player
            }

            LOG.info("Trick finished: cards $cards trick winner $trickWinner value $cardValues")
        }
    }

    fun copy(): Trick {
        val copy = Trick(trickForeHand)
        copy.cards.addAll(cards)
        copy.trickWinner = trickWinner
        copy.cardValues = cardValues
        return copy
    }
}