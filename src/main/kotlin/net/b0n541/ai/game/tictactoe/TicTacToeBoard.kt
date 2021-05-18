package net.b0n541.ai.game.tictactoe

import org.slf4j.LoggerFactory

class TicTacToeBoard(firstPlayer: TicTacToePlayerSymbol) {

    companion object {
        private val LOG = LoggerFactory.getLogger(TicTacToeBoard::class.java)
    }

    var nextTicTacToePlayer: TicTacToePlayerSymbol
        private set

    init {
        nextTicTacToePlayer = firstPlayer
    }

    private var noughts = 0
    private var crosses = 0

    private val board = listOf(
        mutableListOf<TicTacToePlayerSymbol?>(null, null, null),
        mutableListOf<TicTacToePlayerSymbol?>(null, null, null),
        mutableListOf<TicTacToePlayerSymbol?>(null, null, null)
    )
    private val moves: MutableList<TicTacToeMove> = mutableListOf()

    private var noughtsWon = false
    private var crossesWon = false
    private var isDraw = false

    fun moves() = moves.toList()

    operator fun get(row: Int, column: Int): TicTacToePlayerSymbol? {
        return board[row][column]
    }

    fun getMoves(): List<TicTacToeMove> {
        return moves.toList()
    }

    val possibleMoves: List<TicTacToeMove>
        get() = if (isFinished) {
            emptyList()
        } else {
            TicTacToeRules.getPossibleMoves(nextTicTacToePlayer, noughts, crosses)
        }

    fun addMove(move: TicTacToeMove) {
        addMoveInternal(move)
        switchPlayer()
    }

    private fun addMoveInternal(move: TicTacToeMove) {
        require(board[move.row][move.column] == null) { "Board position already occupied." }

        board[move.row][move.column] = move.ticTacToePlayerSymbol
        updateNoughtsAndCrosses(move.ticTacToePlayerSymbol, move.row, move.column)
        moves.add(move)
        if (move.ticTacToePlayerSymbol == TicTacToePlayerSymbol.O) {
            noughtsWon = TicTacToeRules.hasWon(noughts)
        } else if (move.ticTacToePlayerSymbol == TicTacToePlayerSymbol.X) {
            crossesWon = TicTacToeRules.hasWon(crosses)
        }
        if (TicTacToeRules.isBoardFull(noughts, crosses)) {
            isDraw = TicTacToeRules.isDraw(noughts, crosses)
        }
    }

    private fun switchPlayer() {
        if (nextTicTacToePlayer == TicTacToePlayerSymbol.O) {
            nextTicTacToePlayer = TicTacToePlayerSymbol.X
        } else if (nextTicTacToePlayer == TicTacToePlayerSymbol.X) {
            nextTicTacToePlayer = TicTacToePlayerSymbol.O
        }
    }

    private fun updateNoughtsAndCrosses(ticTacToePlayerSymbol: TicTacToePlayerSymbol?, row: Int, column: Int) {
        val bitPosition = row * 3 + column
        if (ticTacToePlayerSymbol == TicTacToePlayerSymbol.O) {
            noughts = noughts or (0x1 shl bitPosition)
        } else if (ticTacToePlayerSymbol == TicTacToePlayerSymbol.X) {
            crosses = crosses or (0x1 shl bitPosition)
        }
    }

    val isFinished: Boolean
        get() = noughtsWon || crossesWon || isDraw

    val gameResult: TicTacToeGameResult
        get() {
            if (noughtsWon) {
                return TicTacToeGameResult.O_WON
            } else if (crossesWon) {
                return TicTacToeGameResult.X_WON
            }
            return TicTacToeGameResult.DRAW
        }

    fun format(): String {
        val builder = StringBuilder()
        builder.append("\n\n")
        for (row in 0..2) {
            for (column in 0..2) {
                builder.append(if (board[row][column] == null) "   " else " " + board[row][column] + " ")
                if (column != 2) {
                    builder.append("|")
                }
            }
            builder.append('\n')
            if (row != 2) {
                builder.append("-----------\n")
            }
        }
        return builder.toString()
    }
}