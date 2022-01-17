package net.b0n541.ai.game.schwimmen

import net.b0n541.ai.game.common.GameState
import net.b0n541.ai.game.common.card.Card
import net.b0n541.ai.game.common.card.Hand

class SchwimmenGameState(
    var nextPlayerPosition: SchwimmenPlayerSymbol
) : GameState<SchwimmenMove> {

    private val gameMoves: MutableList<SchwimmenMove> = mutableListOf()

    private val middleCards = Hand()
    private val playerCards = mapOf(
        SchwimmenPlayerSymbol.ONE to Hand(),
        SchwimmenPlayerSymbol.TWO to Hand(),
        SchwimmenPlayerSymbol.THREE to Hand()
    )
    private var closeAnnouncePlayer: SchwimmenPlayerSymbol? = null
    private var gameFinished: Boolean = false

    override val players = listOf(
        SchwimmenPlayerSymbol.ONE.toString(),
        SchwimmenPlayerSymbol.TWO.toString(),
        SchwimmenPlayerSymbol.THREE.toString()
    )

    fun dealPlayerCards(player: SchwimmenPlayerSymbol, cards: List<Card>) {
        playerCards[player]?.addCards(cards)
    }

    fun dealMiddleCards(cards: List<Card>) {
        middleCards.addCards(cards)
    }

    override val lastMove
        get() = gameMoves.last()

    override val nextPlayer
        get() = nextPlayerPosition.toString()

    override fun addMove(move: SchwimmenMove): GameState<SchwimmenMove> {
        require(nextPlayerPosition == move.player) { "Move player needs to be $nextPlayerPosition." }

        val newState = copy()

        newState.gameMoves.add(move)

        when (move.move) {
            SchwimmenMoveType.ONE_CARD_SWAP,
            SchwimmenMoveType.THREE_CARD_SWAP -> {
                playerCards[move.player]!!.removeCards(move.ownCards)
                middleCards.removeCards(move.middleCards)
                playerCards[move.player]!!.addCards(move.middleCards)
                middleCards.addCards(move.ownCards)
            }
            SchwimmenMoveType.CLOSE -> closeAnnouncePlayer = move.player
            SchwimmenMoveType.THIRTY_ONE -> gameFinished = true
            SchwimmenMoveType.FIRE -> gameFinished = true
        }

        newState.nextPlayerPosition = move.player.nextPlayer

        return newState
    }

    private fun copy(): SchwimmenGameState {
        val newState = SchwimmenGameState(nextPlayerPosition)

        newState.apply {
            gameMoves.clear()
            gameMoves.addAll(this.gameMoves)

            middleCards.addCards(this.middleCards.cards)
            playerCards[SchwimmenPlayerSymbol.ONE]?.addCards(this.playerCards[SchwimmenPlayerSymbol.ONE]!!.cards)
            playerCards[SchwimmenPlayerSymbol.TWO]?.addCards(this.playerCards[SchwimmenPlayerSymbol.TWO]!!.cards)
            playerCards[SchwimmenPlayerSymbol.THREE]?.addCards(this.playerCards[SchwimmenPlayerSymbol.THREE]!!.cards)

            closeAnnouncePlayer = this.closeAnnouncePlayer
        }

        return newState
    }

    override val possibleMoves: List<SchwimmenMove>
        get() = TODO()

    override val isGameFinished: Boolean
        get() = gameFinished

    override val gameValues: Map<String, Double>
        get() = TODO("Not yet implemented")
}