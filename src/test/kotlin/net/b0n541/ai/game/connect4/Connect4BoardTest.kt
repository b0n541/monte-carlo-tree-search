package net.b0n541.ai.game.connect4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class Connect4BoardTest {
    @Test
    fun initialState() {
        val emptyBoard = Connect4Board()

        assertEmptyBoard(emptyBoard)
        assertThat(emptyBoard.nextPlayer).isEqualTo(Connect4PlayerSymbol.O)
    }

    private fun assertEmptyBoard(emptyBoard: Connect4Board) {
        for (i in 0..5) {
            assertThat(emptyBoard.getRow(i)).isEqualTo(listOf(" ", " ", " ", " ", " ", " ", " "))
        }
    }

    @Test
    fun setInitialPlayer() {
        val emptyBoard = Connect4Board(nextPlayer = Connect4PlayerSymbol.X)

        assertEmptyBoard(emptyBoard)
        assertThat(emptyBoard.nextPlayer).isEqualTo(Connect4PlayerSymbol.X)
    }

    @Test
    fun setInvalidInitialStates() {
        assertThrows<IllegalArgumentException> {
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " ")
                )
            )
        }

        assertThrows<IllegalArgumentException> {
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " ")
                )
            )
        }

        assertThrows<IllegalArgumentException> {
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " "),
                    arrayOf(" ", " "),
                    arrayOf(" ", " "),
                    arrayOf(" ", " "),
                    arrayOf(" ", " "),
                    arrayOf("X", "O")
                )
            )
        }

        assertThrows<IllegalArgumentException> {
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " ", " "),
                    arrayOf("X", "O", "X", "O", "X", "O", "X", "O")
                )
            )
        }

        assertThrows<IllegalArgumentException> {
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " "),
                    arrayOf(" ", " ", " "),
                    arrayOf("X", "O")
                )
            )
        }

        assertThrows<IllegalArgumentException> {
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", "X", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", "O", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf(" ", " ", " ", " ", " ", " ", " "),
                    arrayOf("X", "O", "X", "O", "X", "O", "X")
                )
            )
        }
    }

    @Test
    fun setValidInitialState() {
        val board = Connect4Board(
            arrayOf(
                arrayOf(" ", " ", " ", " ", " ", " ", " "),
                arrayOf(" ", " ", " ", " ", " ", " ", " "),
                arrayOf(" ", " ", " ", " ", " ", " ", " "),
                arrayOf(" ", " ", " ", " ", " ", " ", " "),
                arrayOf(" ", " ", " ", " ", " ", " ", " "),
                arrayOf("X", "O", "X", "O", "X", "O", "X")
            )
        )

        assertThat(board.getRow(0)).isEqualTo(listOf("X", "O", "X", "O", "X", "O", "X"))
        for (i in 1..5) {
            assertThat(board.getRow(i)).isEqualTo(listOf(" ", " ", " ", " ", " ", " ", " "))
        }

        assertThat(board.nextPlayer).isEqualTo(Connect4PlayerSymbol.O)
    }

    @Test
    fun validRowIndex() {
        val board = Connect4Board()

        assertThrows<IllegalArgumentException> { board.getRow(-1) }
        assertThrows<IllegalArgumentException> { board.getRow(6) }

        assertThat(board.getRow(0)).isEqualTo(listOf(" ", " ", " ", " ", " ", " ", " "))
        assertThat(board.getRow(5)).isEqualTo(listOf(" ", " ", " ", " ", " ", " ", " "))
    }

    @Test
    fun validColumnIndex() {
        val board = Connect4Board()

        assertThrows<IllegalArgumentException> { board.getColumn(-1) }
        assertThrows<IllegalArgumentException> { board.getColumn(7) }

        assertThat(board.getColumn(0)).isEqualTo(listOf(" ", " ", " ", " ", " ", " "))
        assertThat(board.getColumn(6)).isEqualTo(listOf(" ", " ", " ", " ", " ", " "))
    }

    @Test
    fun validCellIndex() {
        val board = Connect4Board()

        assertThrows<IllegalArgumentException> { board.getCell(0, -1) }
        assertThrows<IllegalArgumentException> { board.getCell(0, 7) }
        assertThrows<IllegalArgumentException> { board.getCell(-1, 0) }
        assertThrows<IllegalArgumentException> { board.getCell(6, 0) }

        assertThat(board.getCell(0, 0)).isEqualTo(" ")
        assertThat(board.getCell(5, 6)).isEqualTo(" ")
    }

    @Test
    fun dropPiece() {
        val board = Connect4Board()

        assertThrows<IllegalArgumentException> { board.dropPiece(Connect4Move(Connect4PlayerSymbol.X, 0)) }
        assertThrows<IllegalArgumentException> { board.dropPiece(Connect4Move(Connect4PlayerSymbol.O, -1)) }
        assertThrows<IllegalArgumentException> { board.dropPiece(Connect4Move(Connect4PlayerSymbol.O, 7)) }

        val newBoard = board.dropPiece(Connect4Move(Connect4PlayerSymbol.O, 0))

        assertThat(newBoard.nextPlayer).isEqualTo(Connect4PlayerSymbol.X)
        assertThat(newBoard.getColumn(0)).isEqualTo(listOf("O", " ", " ", " ", " ", " "))

        val newBoard2 = newBoard.dropPiece(Connect4Move(Connect4PlayerSymbol.X, 0))

        assertThat(newBoard2.nextPlayer).isEqualTo(Connect4PlayerSymbol.O)
        assertThat(newBoard2.getColumn(0)).isEqualTo(listOf("O", "X", " ", " ", " ", " "))
    }

    @Test
    fun setInvalidInitialColumn() {
        assertThrows<IllegalArgumentException> { Connect4Board.Connect4Column(listOf("A")) }
        assertThrows<IllegalArgumentException> { Connect4Board.Connect4Column(listOf(" ", "X")) }
        assertThrows<IllegalArgumentException> { Connect4Board.Connect4Column(listOf("X", " ", "X")) }
        assertThrows<IllegalArgumentException> {
            Connect4Board.Connect4Column(
                listOf(" ", " ", " ", " ", " ", " ", " ")
            )
        }
    }

    @Test
    fun setInitialColumn() {
        assertThat(Connect4Board.Connect4Column(listOf(" ", " ", " ", " ", " ", " ")).toList())
            .isEqualTo(listOf(" ", " ", " ", " ", " ", " "))

        assertThat(Connect4Board.Connect4Column(listOf("X", "O", " ")).toList())
            .isEqualTo(listOf("X", "O", " ", " ", " ", " "))
    }

    @Test
    fun columnFull() {
        val board = Connect4Board()

        val newBoard = board.dropPiece(Connect4Move(Connect4PlayerSymbol.O, 1))
            .dropPiece(Connect4Move(Connect4PlayerSymbol.X, 1))
            .dropPiece(Connect4Move(Connect4PlayerSymbol.O, 1))
            .dropPiece(Connect4Move(Connect4PlayerSymbol.X, 1))
            .dropPiece(Connect4Move(Connect4PlayerSymbol.O, 1))
            .dropPiece(Connect4Move(Connect4PlayerSymbol.X, 1))

        assertThat(newBoard.getColumn(1)).isEqualTo(listOf("O", "X", "O", "X", "O", "X"))

        assertThrows<IllegalArgumentException> { newBoard.dropPiece(Connect4Move(Connect4PlayerSymbol.O, 1)) }
    }

    @Test
    fun possibleMoves() {
        val board = Connect4Board(
            arrayOf(
                arrayOf(" ", " ", " ", " ", " ", " ", "O"),
                arrayOf(" ", " ", " ", " ", " ", "X", "X"),
                arrayOf(" ", " ", " ", " ", "X", "O", "X"),
                arrayOf(" ", " ", " ", "O", "X", "X", "O"),
                arrayOf(" ", " ", "X", "X", "O", "O", "X"),
                arrayOf(" ", "O", "X", "X", "O", "X", "O")
            )
        )

        assertThat(board.getPossibleMoves()).containsExactlyInAnyOrder(0, 1, 2, 3, 4, 5)
    }

    @Test
    fun getGameResult() {

        assertThat(
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", "O"),
                    arrayOf(" ", " ", " ", " ", " ", "X", "X"),
                    arrayOf(" ", " ", " ", " ", "X", "O", "X"),
                    arrayOf(" ", " ", " ", "O", "X", "X", "O"),
                    arrayOf(" ", " ", "X", "X", "O", "O", "X"),
                    arrayOf(" ", "O", "X", "X", "O", "X", "O")
                )
            ).gameResult
        ).isEqualTo(Connect4GameResult.UNDECIDED)

        // vertical win
        assertThat(
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", "X", " ", "O"),
                    arrayOf(" ", " ", " ", " ", "X", "X", "X"),
                    arrayOf(" ", " ", " ", " ", "X", "O", "X"),
                    arrayOf(" ", " ", " ", "O", "X", "X", "O"),
                    arrayOf(" ", " ", "X", "X", "O", "O", "X"),
                    arrayOf(" ", "O", "X", "X", "O", "X", "O")
                )
            ).gameResult
        ).isEqualTo(Connect4GameResult.X_WIN)

        // horizontal win
        assertThat(
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", "O"),
                    arrayOf(" ", " ", " ", " ", " ", "X", "X"),
                    arrayOf(" ", " ", " ", " ", "X", "O", "X"),
                    arrayOf(" ", " ", " ", "O", "X", "X", "O"),
                    arrayOf("X", "X", "X", "X", "O", "O", "X"),
                    arrayOf("O", "O", "X", "X", "O", "X", "O")
                )
            ).gameResult
        ).isEqualTo(Connect4GameResult.X_WIN)

        // diagnol win
        assertThat(
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", "O"),
                    arrayOf(" ", "O", " ", " ", " ", "X", "X"),
                    arrayOf(" ", "X", "O", " ", "X", "O", "X"),
                    arrayOf(" ", "O", "X", "O", "X", "X", "O"),
                    arrayOf(" ", "X", "X", "X", "O", "O", "X"),
                    arrayOf(" ", "O", "X", "X", "O", "X", "O")
                )
            ).gameResult
        ).isEqualTo(Connect4GameResult.O_WIN)

        assertThat(
            Connect4Board(
                arrayOf(
                    arrayOf(" ", " ", " ", " ", " ", " ", "O"),
                    arrayOf(" ", " ", " ", " ", " ", "X", "X"),
                    arrayOf(" ", " ", " ", " ", "X", "X", "X"),
                    arrayOf(" ", " ", " ", "O", "X", "X", "O"),
                    arrayOf(" ", " ", "X", "X", "O", "O", "X"),
                    arrayOf(" ", "O", "X", "X", "O", "X", "O")
                )
            ).gameResult
        ).isEqualTo(Connect4GameResult.X_WIN)

        // draw
        assertThat(
            Connect4Board(
                arrayOf(
                    arrayOf("O", "O", "O", "X", "O", "O", "O"),
                    arrayOf("X", "X", "X", "O", "X", "X", "X"),
                    arrayOf("O", "O", "X", "X", "X", "O", "X"),
                    arrayOf("X", "X", "O", "O", "X", "X", "O"),
                    arrayOf("O", "O", "X", "X", "O", "O", "X"),
                    arrayOf("O", "O", "X", "X", "O", "X", "O")
                )
            ).gameResult
        ).isEqualTo(Connect4GameResult.DRAW)
    }
}