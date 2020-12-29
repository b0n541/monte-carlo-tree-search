package net.b0n541.pmcts.game.skat

class Trick(
    private val trickForeHand: PlayerPosition,
    private val cards: MutableList<SkatGameMove> = mutableListOf(),
) {
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

        if (cards.size == 3) {
            trickWinner = trickForeHand
            if (cards[1].beats(cards[0])) {
                trickWinner = cards[1].player
                if (cards[2].beats(cards[1])) {
                    trickWinner = cards[2].player
                }
            } else if (cards[2].beats(cards[0])) {
                trickWinner = cards[2].player
            }
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