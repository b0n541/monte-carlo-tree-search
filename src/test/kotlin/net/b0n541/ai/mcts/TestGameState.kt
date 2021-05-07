package net.b0n541.ai.mcts

import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.GameState

internal class TestGameState(override val players: List<String>) : GameState<GameMove> {
    private val moves: MutableList<GameMove> = mutableListOf()

    private constructor(players: List<String>, moves: List<GameMove>, move: GameMove) : this(players) {
        this.moves.addAll(moves)
        this.moves.add(move)
    }

    override val possibleMoves: List<GameMove>
        get() = listOf(TestGameMove.HEADS, TestGameMove.TAILS)

    override fun addMove(move: GameMove): GameState<GameMove> {
        return TestGameState(players, moves, move)
    }

    override val isGameFinished: Boolean
        get() = moves.size == 100

    override val gameValues: Map<String, Double>
        get() {
            val headsGameValue = 1.0 *
                    moves.stream()
                        .filter { move: GameMove -> move === TestGameMove.HEADS }
                        .count()

            if (players.size == 1) {
                return mapOf(players[0] to headsGameValue)
            } else if (players.size == 2) {
                return (mapOf(players[0] to headsGameValue, players[1] to (100 - headsGameValue)))
            }
            return mapOf()
        }

    override val lastMove: GameMove
        get() = moves[moves.size - 1]

    override val nextPlayer: String
        get() = players[0]
}