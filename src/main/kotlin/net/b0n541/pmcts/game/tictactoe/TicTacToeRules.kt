package net.b0n541.pmcts.game.tictactoe

import org.slf4j.LoggerFactory
import java.util.*

object TicTacToeRules {
    private val LOG = LoggerFactory.getLogger(TicTacToeRules::class.java)

    /**
     * Indices of board cells in the winning patterns.
     *
     *
     * 0 | 1 | 2
     * -----------
     * 3 | 4 | 5
     * -----------
     * 6 | 7 | 8
     */
    private val WINNING_PATTERNS = intArrayOf(
        7,  // row 0
        56,  // row 1
        448,  // row 2
        73,  // column 0
        146,  // column 1
        292,  // column 2
        273,  // diagonal
        84 // diagonal reversed
    )
    private const val FULL_FIELD = 511

    @JvmStatic
    fun hasWon(playerPattern: Int): Boolean {
        for (winningPattern in WINNING_PATTERNS) {
            if (winningPattern and playerPattern == winningPattern) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun isDraw(noughts: Int, crosses: Int): Boolean {
        return !hasWon(noughts) && !hasWon(crosses)
    }

    @JvmStatic
    fun isBoardFull(noughts: Int, crosses: Int): Boolean {
        return noughts or crosses and FULL_FIELD == FULL_FIELD
    }

    fun getPossibleMoves(player: PlayerSymbol, noughts: Int, crosses: Int): List<TicTacToeMove> {
        val freeCells = (noughts or crosses and FULL_FIELD).inv()
        val result: MutableList<TicTacToeMove> = ArrayList()
        for (row in 0..2) {
            for (column in 0..2) {
                val free = freeCells and (0x1 shl row * 3 + column) != 0
                if (free) {
                    result.add(TicTacToeMove(player, row, column))
                }
            }
        }
        return Collections.unmodifiableList(result)
    }
}