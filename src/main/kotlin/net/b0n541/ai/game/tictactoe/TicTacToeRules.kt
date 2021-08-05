package net.b0n541.ai.game.tictactoe

import org.slf4j.LoggerFactory
import java.util.*

object TicTacToeRules {
    private val LOG = LoggerFactory.getLogger(TicTacToeRules::class.java)

    /**
     * Indices of board cells in the winning patterns.
     *
     * 0 | 1 | 2
     * -----------
     * 3 | 4 | 5
     * -----------
     * 6 | 7 | 8
     */
    private val WINNING_PATTERNS = intArrayOf(
        0b000000111, // row 0
        0b000111000, // row 1
        0b111000000, // row 2
        0b001001001, // column 0
        0b010010010, // column 1
        0b100100100, // column 2
        0b100010001, // diagonal
        0b001010100  // diagonal reversed
    )

    private const val FULL_FIELD = 0b111111111

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
        return noughts or crosses == FULL_FIELD
    }

    @JvmStatic
    fun getPossibleMoves(ticTacToePlayer: TicTacToePlayerSymbol, noughts: Int, crosses: Int): List<TicTacToeMove> {
        val freeCells = (noughts or crosses).inv()
        val result: MutableList<TicTacToeMove> = ArrayList()
        for (row in 0..2) {
            for (column in 0..2) {
                val free = freeCells and (0x1 shl row * 3 + column) != 0
                if (free) {
                    result.add(TicTacToeMove(ticTacToePlayer, row, column))
                }
            }
        }
        return Collections.unmodifiableList(result)
    }
}