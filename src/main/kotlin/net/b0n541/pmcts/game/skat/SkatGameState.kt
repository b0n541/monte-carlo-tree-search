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
    private val tricks: MutableList<Trick> = mutableListOf()
    private var declarerPoints: Int = 0
    private var opponentPoints: Int = 0

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
        TODO("Not yet implemented")
    }

    override fun addMove(move: SkatGameMove): SkatGameState {

        require(nextPlayerPosition == move.player)

        val newState = copy()

        newState.gameMoves.add(move)

        var lastTrick = newState.getLastTrick()
        lastTrick.addMove(move)

        if (lastTrick.isFinished && newState.tricks.size < 10) {
            val trickWinner = lastTrick.trickWinner!!
            if (trickWinner == declarer) {
                newState.declarerPoints += lastTrick.cardValues
            } else {
                newState.opponentPoints += lastTrick.cardValues
            }
            newState.tricks.add(Trick(trickWinner))
            newState.nextPlayerPosition = trickWinner
        } else {
            newState.nextPlayerPosition = move.player.nextPlayer
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
        return newState
    }

    override fun isGameFinished(): Boolean {
        return tricks.size == 10 && tricks.last().isFinished
    }

    override fun getGameValues(): Map<String, Double> {
        return mapOf(
            declarer.toString() to declarerPoints.toDouble(),
            declarer.nextPlayer.toString() to opponentPoints.toDouble(),
            declarer.nextPlayer.nextPlayer.toString() to opponentPoints.toDouble()
        )
    }

    override fun getLastMove(): GameMove = gameMoves.last()

    fun getLastTrick(): Trick = tricks.last()
}