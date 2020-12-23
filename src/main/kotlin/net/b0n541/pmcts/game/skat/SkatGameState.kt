package net.b0n541.pmcts.game.skat

import net.b0n541.pmcts.mcts.GameMove
import net.b0n541.pmcts.mcts.GameState

class SkatGameState(val gameType: SkatGameType, private var nextPlayerPosition: PlayerPosition) :
    GameState<SkatGameMove> {

    private val gameMoves: MutableList<GameMove> = mutableListOf()
    private val tricks: MutableList<Trick> = mutableListOf()

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

    override fun addMove(move: SkatGameMove): GameState<SkatGameMove> {

        val newState = SkatGameState(gameType, nextPlayerPosition)
        newState.gameMoves.clear()
        newState.gameMoves.addAll(gameMoves)
        newState.gameMoves.add(move)
        newState.tricks.clear()
        for (trick: Trick in tricks) {
            newState.tricks.add(trick.copy())
        }

        val lastTrick = newState.tricks.last()
        lastTrick.addMove(move)
        if (lastTrick.isFinished) {
            tricks.add(Trick(lastTrick.trickWinner!!))
            newState.nextPlayerPosition = lastTrick.trickWinner!!
        } else {
            newState.nextPlayerPosition = move.player.nextPlayer
        }

        return newState
    }

    override fun isGameFinished(): Boolean {
        return tricks.size == 10 && tricks.last().isFinished
    }

    override fun getGameValues(): Map<String, Double> {
        TODO("Not yet implemented")
    }

    override fun getLastMove(): GameMove = gameMoves.last()
}