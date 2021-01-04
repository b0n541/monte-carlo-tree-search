package net.b0n541.pmcts.game.skat

import net.b0n541.pmcts.mcts.GameMove
import net.b0n541.pmcts.mcts.GameState

class SkatGameState(
    val gameType: SkatGameType,
    val declarer: PlayerPosition,
    var nextPlayerPosition: PlayerPosition
) :
    GameState<SkatGameMove> {

    private val gameMoves: MutableList<GameMove> = mutableListOf()

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

    override fun getPlayers(): List<String> =
        listOf(
            PlayerPosition.FOREHAND.toString(),
            PlayerPosition.MIDDLEHAND.toString(),
            PlayerPosition.REARHAND.toString()
        )

    override fun getNextPlayer(): String {
        return nextPlayerPosition.toString()
    }

    override fun getPossibleMoves(): List<SkatGameMove> {
        val allCards = playerCards[nextPlayerPosition]!!

        return if (lastTrick.leadingCard == null) {
            allCards.openCards.map { SkatGameMove(nextPlayerPosition, it) }
        } else {
            val leadingCard = lastTrick.leadingCard!!
            val trumpCards = playerCards[nextPlayerPosition]!!.getTrumpCards(gameType)

            allCards.openCards
                .filter { it.isAllowedOn(leadingCard, gameType, allCards) }
                .map { SkatGameMove(nextPlayerPosition, it) }
        }
    }

    override fun addMove(move: SkatGameMove): SkatGameState {

        require(nextPlayerPosition == move.player)

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
        newState.gameMoves.clear()
        newState.gameMoves.addAll(gameMoves)
        newState.tricks.clear()
        for (trick: Trick in tricks) {
            newState.tricks.add(trick.copy())
        }
        newState.declarerPoints = declarerPoints
        newState.opponentPoints = opponentPoints
        newState.skatCards.takeCards(skatCards.cards)
        newState.playerCards[PlayerPosition.FOREHAND]!!.takeCards(playerCards[PlayerPosition.FOREHAND]!!.cards)
        newState.playerCards[PlayerPosition.MIDDLEHAND]!!.takeCards(playerCards[PlayerPosition.MIDDLEHAND]!!.cards)
        newState.playerCards[PlayerPosition.REARHAND]!!.takeCards(playerCards[PlayerPosition.REARHAND]!!.cards)
        return newState
    }

    override fun isGameFinished(): Boolean {
        return tricks.size == 10 && lastTrick.isFinished
    }

    override fun getGameValues(): Map<String, Double> {
        return mapOf(
            declarer.toString() to declarerPoints.toDouble(),
            declarer.nextPlayer.toString() to opponentPoints.toDouble(),
            declarer.nextPlayer.nextPlayer.toString() to opponentPoints.toDouble()
        )
    }

    override fun getLastMove(): GameMove = gameMoves.last()

    fun dealPlayerCards(player: PlayerPosition, cards: List<Card>) {
        playerCards[player]!!.takeCards(cards)
    }

    fun dealSkat(cards: List<Card>) {
        skatCards.takeCards(cards)
    }
}