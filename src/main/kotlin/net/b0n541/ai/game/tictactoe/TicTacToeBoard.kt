package net.b0n541.ai.game.tictactoe

import org.slf4j.LoggerFactory

class TicTacToeBoard(val firstPlayer: PlayerSymbol) {
    private val board = listOf(
        mutableListOf<PlayerSymbol?>(null, null, null),
        mutableListOf<PlayerSymbol?>(null, null, null),
        mutableListOf<PlayerSymbol?>(null, null, null)
    )

    private var noughts = 0
    private var crosses = 0

    var nextPlayer: PlayerSymbol
        private set

    private val moves: MutableList<TicTacToeMove> = mutableListOf()
    fun moves() = moves.toList()

    private var noughtsWon = false
    private var crossesWon = false
    private var isDraw = false

    init {
        nextPlayer = firstPlayer
    }

    operator fun get(row: Int, column: Int): PlayerSymbol? {
        return board[row][column]
    }

    fun getMoves(): List<TicTacToeMove> {
        return moves.toList()
    }

    val possibleMoves: List<TicTacToeMove>
        get() = if (isFinished) {
            emptyList()
        } else {
            TicTacToeRules.getPossibleMoves(nextPlayer, noughts, crosses)
        }

    fun addMove(move: TicTacToeMove) {
        addMoveInternal(move)
        switchPlayer()
    }

    private fun addMoveInternal(move: TicTacToeMove) {
        require(board[move.row][move.column] == null) { "Board position already occupied." }

        board[move.row][move.column] = move.playerSymbol
        updateNoughtsAndCrosses(move.playerSymbol, move.row, move.column)
        moves.add(move)
        if (move.playerSymbol == PlayerSymbol.O) {
            noughtsWon = TicTacToeRules.hasWon(noughts)
        } else if (move.playerSymbol == PlayerSymbol.X) {
            crossesWon = TicTacToeRules.hasWon(crosses)
        }
        if (TicTacToeRules.isBoardFull(noughts, crosses)) {
            isDraw = TicTacToeRules.isDraw(noughts, crosses)
        }
    }

    private fun switchPlayer() {
        if (nextPlayer == PlayerSymbol.O) {
            nextPlayer = PlayerSymbol.X
        } else if (nextPlayer == PlayerSymbol.X) {
            nextPlayer = PlayerSymbol.O
        }
    }

    private fun updateNoughtsAndCrosses(playerSymbol: PlayerSymbol?, row: Int, column: Int) {
        val bitPosition = row * 3 + column
        if (playerSymbol == PlayerSymbol.O) {
            noughts = noughts or (0x1 shl bitPosition)
        } else if (playerSymbol == PlayerSymbol.X) {
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

    companion object {
        private val LOG = LoggerFactory.getLogger(TicTacToeBoard::class.java)
    }
}