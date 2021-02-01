package net.b0n541.ai.game.connect4

import net.b0n541.ai.mcts.GameState
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j

class Connect4GameState(
    firstPlayer: Connect4PlayerSymbol = Connect4PlayerSymbol.O,
    private val board: Connect4Board = Connect4Board(nextPlayer = firstPlayer)
) : GameState<Connect4Move> {

    private constructor(oldBoard: Connect4Board, move: Connect4Move) : this(board = oldBoard.dropPiece(move))

    override val players: List<String> = listOf(Connect4PlayerSymbol.O.toString(), Connect4PlayerSymbol.X.toString())

    override val nextPlayer: String
        get() = board.nextPlayer.toString()

    override val possibleMoves: List<Connect4Move>
        get() = board.getPossibleMoves().map { Connect4Move(board.nextPlayer, it) }

    override fun addMove(move: Connect4Move): Connect4GameState {
        return Connect4GameState(board, move)
    }

    override val isGameFinished: Boolean
        get() = board.gameResult != Connect4GameResult.UNDECIDED

    override val gameValues: Map<String, Double>
        get() {
            val playerOResult = when (board.gameResult) {
                Connect4GameResult.O_WIN -> 1.0
                Connect4GameResult.X_WIN -> 0.0
                else -> 0.5
            }

            return mapOf(
                Connect4PlayerSymbol.O.toString() to playerOResult,
                Connect4PlayerSymbol.X.toString() to 1.0 - playerOResult
            )
        }

    override val lastMove: Connect4Move
        get() = board.lastMove

    override fun toString(): String {
        return board.toString()
    }

    val networkInputs: INDArray
        get() {
            val result = mutableListOf<Double>()

            for (column in 0..6) {
                val columnValues = board.getColumn(column).map {
                    when (it) {
                        " " -> 0.0
                        nextPlayer -> 1.0
                        else -> -1.0
                    }
                }
                result.addAll(columnValues)
            }

            result.add(1.0)

            return Nd4j.create(result.toDoubleArray(), intArrayOf(1, 43), 'c')
        }
}