package net.b0n541.pmcts.game.tictactoe

import net.b0n541.pmcts.mcts.GameMove
import net.b0n541.pmcts.mcts.GameState
import java.util.*
import java.util.function.Consumer

class TicTacToeGameState(firstPlayer: PlayerSymbol) : GameState<TicTacToeMove> {

    private val board: TicTacToeBoard = TicTacToeBoard(firstPlayer)

    private constructor(oldBoard: TicTacToeBoard, nextMove: TicTacToeMove) : this(oldBoard.firstPlayer) {
        oldBoard.moves().forEach(Consumer { move: TicTacToeMove -> board.addMove(move) })
        board.addMove(nextMove)
    }

    override val players: List<String>
        get() = listOf(PlayerSymbol.O.toString(), PlayerSymbol.X.toString())

    override val possibleMoves: List<TicTacToeMove>
        get() = board.possibleMoves

    override fun addMove(move: TicTacToeMove): TicTacToeGameState {
        return TicTacToeGameState(board, move)
    }

    override val isGameFinished: Boolean
        get() = board.isFinished

    override val gameValues: Map<String, Double>
        get() {
            val playerOResult: Double = when (board.gameResult) {
                TicTacToeGameResult.O_WON -> 1.0
                TicTacToeGameResult.X_WON -> 0.0
                else -> 0.5
            }
            val result: MutableMap<String, Double> = HashMap()
            result[PlayerSymbol.O.toString()] = playerOResult
            result[PlayerSymbol.X.toString()] = 1.0 - playerOResult
            return result
        }

    override val lastMove: GameMove
        get() = board.moves().last()

    override val nextPlayer: String
        get() = board.nextPlayer.toString()

    override fun toString(): String {
        return board.format()
    }
}