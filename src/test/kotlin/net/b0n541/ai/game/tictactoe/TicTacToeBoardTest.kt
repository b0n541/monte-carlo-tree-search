package net.b0n541.ai.game.tictactoe

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class TicTacToeBoardTest {
    @Test
    fun emptyBoard() {
        val board = TicTacToeBoard(TicTacToePlayerSymbol.O)
        for (row in 0..2) {
            for (column in 0..2) {
                Assertions.assertThat(board[row, column]).isNull()
            }
        }
        Assertions.assertThat(board.getMoves()).hasSize(0)
    }

    @Test
    fun addMove() {
        val board = TicTacToeBoard(TicTacToePlayerSymbol.X)
        board.addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0))
        Assertions.assertThat(board[0, 0]).isEqualTo(TicTacToePlayerSymbol.X)
    }

    @Test
    fun addMoveFieldAlreadyOccupied() {
        val board = TicTacToeBoard(TicTacToePlayerSymbol.X)
        board.addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0))
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            board.addMove(
                TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0)
            )
        }
        Assertions.assertThat(board[0, 0]).isEqualTo(TicTacToePlayerSymbol.X)
    }

    @Test
    fun possibleMoves() {
        val board = TicTacToeBoard(TicTacToePlayerSymbol.X)
        Assertions.assertThat(board.possibleMoves).hasSize(9)
        board.addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0))
        var possibleMoves = board.possibleMoves
        Assertions.assertThat(possibleMoves).hasSize(8)
        Assertions.assertThat(possibleMoves).doesNotContain(TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0))
        board.addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 1, 1))
        possibleMoves = board.possibleMoves
        Assertions.assertThat(possibleMoves).hasSize(7)
        Assertions.assertThat(possibleMoves).doesNotContain(
            TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0),
            TicTacToeMove(TicTacToePlayerSymbol.X, 1, 1)
        )
        board.addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 2, 2))
        possibleMoves = board.possibleMoves
        Assertions.assertThat(possibleMoves).hasSize(6)
        Assertions.assertThat(possibleMoves).doesNotContain(
            TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0),
            TicTacToeMove(TicTacToePlayerSymbol.O, 1, 1),
            TicTacToeMove(TicTacToePlayerSymbol.O, 2, 2)
        )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class WinningGames() {
        @ParameterizedTest
        @MethodSource("provideWinningMoves")
        fun winningGames(moves: List<TicTacToeMove>, expectedResult: TicTacToeGameResult?) {
            val board = TicTacToeBoard(TicTacToePlayerSymbol.X)
            for ((playerSymbol, row, column) in moves) {
                board.addMove(TicTacToeMove(playerSymbol, row, column))
            }
            Assertions.assertThat(board.isFinished).isTrue
            Assertions.assertThat(board.gameResult).isEqualTo(expectedResult)
        }

        private fun provideWinningMoves(): Stream<Arguments> {
            return Stream.of( // win in a row
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 0, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 0, 2)
                    ), TicTacToeGameResult.X_WON
                ),
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.X, 1, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 1, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 1, 2)
                    ), TicTacToeGameResult.X_WON
                ),
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.X, 2, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 2, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 2, 2)
                    ), TicTacToeGameResult.X_WON
                ),  // win in a column
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 1, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 2, 0)
                    ), TicTacToeGameResult.O_WON
                ),
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.O, 0, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 1, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 2, 1)
                    ), TicTacToeGameResult.O_WON
                ),
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.O, 0, 2),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 1, 2),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 2, 2)
                    ), TicTacToeGameResult.O_WON
                ),  // win in a diagonal
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 1, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 2, 2)
                    ), TicTacToeGameResult.X_WON
                ),
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.O, 0, 2),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 1, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 2, 0)
                    ), TicTacToeGameResult.O_WON
                ),  // draw  game
                Arguments.of(
                    listOf(
                        TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 0, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 0, 2),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 1, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 1, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 1, 2),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 2, 0),
                        TicTacToeMove(TicTacToePlayerSymbol.X, 2, 1),
                        TicTacToeMove(TicTacToePlayerSymbol.O, 2, 2)
                    ), TicTacToeGameResult.DRAW
                )
            )
        }
    }
}