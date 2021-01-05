package net.b0n541.ai.mcts

import java.util.*

internal class TestGameState(players: List<String>) : GameState<GameMove> {
    private val moves: MutableList<GameMove> = mutableListOf()
    override val players: List<String>

    private constructor(players: List<String>, moves: List<GameMove>, move: GameMove) : this(players) {
        this.moves.addAll(moves)
        this.moves.add(move)
    }

    override val possibleMoves: List<GameMove>
        get() = java.util.List.of(TestGameMove.HEADS, TestGameMove.TAILS)

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
            return java.util.Map.of(
                TestGameMove.HEADS.toString(),
                headsGameValue,
                TestGameMove.TAILS.toString(),
                100 - headsGameValue
            )
        }
    override val lastMove: GameMove
        get() = moves[moves.size - 1]
    override val nextPlayer: String
        get() = players[0]

    init {
        this.players = Collections.unmodifiableList(players)
    }
}