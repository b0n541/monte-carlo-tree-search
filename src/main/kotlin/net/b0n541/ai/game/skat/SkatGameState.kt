package net.b0n541.ai.game.skat

import net.b0n541.ai.game.common.GameState

class SkatGameState(
    val gameType: SkatGameType,
    val declarer: PlayerPosition,
    var nextPlayerPosition: PlayerPosition
) :
    GameState<SkatMove> {

    private val gameMoves: MutableList<SkatMove> = mutableListOf()

    private val skatCards = Hand()
    private val playerCards = mapOf(
        PlayerPosition.FOREHAND to Hand(),
        PlayerPosition.MIDDLEHAND to Hand(),
        PlayerPosition.REARHAND to Hand()
    )

    private val tricks: MutableList<Trick> = mutableListOf()

    private var declarerPoints: Int = 0
    private var opponentPoints: Int = 0

    private val lastTrick
        get() = tricks.last()

    init {
        tricks.add(Trick(nextPlayerPosition))
    }

    override val players = listOf(
        PlayerPosition.FOREHAND.toString(),
        PlayerPosition.MIDDLEHAND.toString(),
        PlayerPosition.REARHAND.toString()
    )

    override val nextPlayer
        get() = nextPlayerPosition.toString()

    override val possibleMoves: List<SkatMove>
        get() {
            val allCards = playerCards[nextPlayerPosition]!!

            return if (lastTrick.leadingCard == null) {
                allCards.openCards.map { SkatMove(nextPlayerPosition, it) }
            } else {
                val leadingCard = lastTrick.leadingCard!!
                val trumpCards = playerCards[nextPlayerPosition]!!.getTrumpCards(gameType)

                allCards.openCards
                    .filter { it.isAllowedOn(leadingCard, gameType, allCards) }
                    .map { SkatMove(nextPlayerPosition, it) }
            }
        }

    override fun addMove(move: SkatMove): SkatGameState {
        require(nextPlayerPosition == move.player) { "Move player needs to be $nextPlayerPosition." }

        val newState = copy()

        // TODO use return newState.apply { ... }
        newState.gameMoves.add(move)

        var lastTrick = newState.lastTrick
        lastTrick.addMove(move)

        if (lastTrick.isFinished) {
            val trickWinner = lastTrick.trickWinner!!
            if (trickWinner == declarer) {
                newState.declarerPoints += lastTrick.cardValues
            } else {
                newState.opponentPoints += lastTrick.cardValues
            }
            if (newState.tricks.size < 10) {
                newState.tricks.add(Trick(trickWinner))
            }
            newState.nextPlayerPosition = trickWinner
        } else {
            newState.nextPlayerPosition = move.player.nextPlayer
        }

        if (newState.isGameFinished) {
            newState.declarerPoints += skatCards.cardValues
        }

        return newState
    }

    private fun copy(): SkatGameState {
        val newState = SkatGameState(gameType, declarer, nextPlayerPosition)
        // TODO use newState.apply {...}
        newState.gameMoves.clear()
        newState.gameMoves.addAll(gameMoves)
        newState.tricks.clear()
        for (trick: Trick in tricks) {
            newState.tricks.add(trick.copy())
        }
        newState.declarerPoints = declarerPoints
        newState.opponentPoints = opponentPoints
        newState.skatCards.takeCards(skatCards.cards)
        newState.playerCards[PlayerPosition.FOREHAND]?.takeCards(playerCards[PlayerPosition.FOREHAND]!!.cards)
        newState.playerCards[PlayerPosition.MIDDLEHAND]?.takeCards(playerCards[PlayerPosition.MIDDLEHAND]!!.cards)
        newState.playerCards[PlayerPosition.REARHAND]?.takeCards(playerCards[PlayerPosition.REARHAND]!!.cards)
        return newState
    }

    override val isGameFinished
        get() = tricks.size == 10 && lastTrick.isFinished

    override val gameValues: Map<String, Double>
        get() = mapOf(
            declarer.toString() to declarerPoints.toDouble(),
            declarer.nextPlayer.toString() to opponentPoints.toDouble(),
            declarer.nextPlayer.nextPlayer.toString() to opponentPoints.toDouble()
        )

    override val lastMove
        get() = gameMoves.last()

    fun dealPlayerCards(player: PlayerPosition, cards: List<Card>) {
        playerCards[player]?.takeCards(cards)
    }

    fun dealSkat(cards: List<Card>) {
        skatCards.takeCards(cards)
    }
}