package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.game.common.GameState
import java.util.function.Consumer

class TicTacToeGameState() : GameState<TicTacToeMove> {

    private val board = TicTacToeBoard(TicTacToePlayerSymbol.O)

    private constructor(oldBoard: TicTacToeBoard, nextMove: TicTacToeMove) : this() {
        oldBoard.moves().forEach(Consumer { move: TicTacToeMove -> board.addMove(move) })
        board.addMove(nextMove)
    }

    override val players
        get() = listOf(TicTacToePlayerSymbol.O.toString(), TicTacToePlayerSymbol.X.toString())

    override val possibleMoves
        get() = board.possibleMoves

    override fun addMove(move: TicTacToeMove): TicTacToeGameState {
        return TicTacToeGameState(board, move)
    }

    override val isGameFinished: Boolean
        get() = board.isFinished

    override val gameValues: Map<String, Double>
        get() {
            val playerOResult = when (board.gameResult) {
                TicTacToeGameResult.O_WON -> 1.0
                TicTacToeGameResult.X_WON -> 0.0
                else -> 0.5
            }

            return mapOf(
                TicTacToePlayerSymbol.O.toString() to playerOResult,
                TicTacToePlayerSymbol.X.toString() to 1.0 - playerOResult
            )
        }

    override val lastMove: TicTacToeMove
        get() = board.moves().last()

    override val nextPlayer: String
        get() = board.nextTicTacToePlayer.toString()

    override fun toString(): String {
        return board.format()
    }
}