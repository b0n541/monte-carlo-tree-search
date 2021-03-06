package net.b0n541.ai.game.tictactoe

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class TicTacToeBoardTest {
    @Test
    fun emptyBoard() {
        val board = TicTacToeBoard(PlayerSymbol.O)
        for (row in 0..2) {
            for (column in 0..2) {
                Assertions.assertThat(board[row, column]).isNull()
            }
        }
        Assertions.assertThat(board.getMoves()).hasSize(0)
    }

    @Test
    fun addMove() {
        val board = TicTacToeBoard(PlayerSymbol.X)
        board.addMove(TicTacToeMove(PlayerSymbol.X, 0, 0))
        Assertions.assertThat(board[0, 0]).isEqualTo(PlayerSymbol.X)
    }

    @Test
    fun addMoveFieldAlreadyOccupied() {
        val board = TicTacToeBoard(PlayerSymbol.X)
        board.addMove(TicTacToeMove(PlayerSymbol.X, 0, 0))
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java) {
            board.addMove(
                TicTacToeMove(PlayerSymbol.O, 0, 0)
            )
        }
        Assertions.assertThat(board[0, 0]).isEqualTo(PlayerSymbol.X)
    }

    @Test
    fun possibleMoves() {
        val board = TicTacToeBoard(PlayerSymbol.X)
        Assertions.assertThat(board.possibleMoves).hasSize(9)
        board.addMove(TicTacToeMove(PlayerSymbol.X, 0, 0))
        var possibleMoves = board.possibleMoves
        Assertions.assertThat(possibleMoves).hasSize(8)
        Assertions.assertThat(possibleMoves).doesNotContain(TicTacToeMove(PlayerSymbol.O, 0, 0))
        board.addMove(TicTacToeMove(PlayerSymbol.O, 1, 1))
        possibleMoves = board.possibleMoves
        Assertions.assertThat(possibleMoves).hasSize(7)
        Assertions.assertThat(possibleMoves).doesNotContain(
            TicTacToeMove(PlayerSymbol.X, 0, 0),
            TicTacToeMove(PlayerSymbol.X, 1, 1)
        )
        board.addMove(TicTacToeMove(PlayerSymbol.X, 2, 2))
        possibleMoves = board.possibleMoves
        Assertions.assertThat(possibleMoves).hasSize(6)
        Assertions.assertThat(possibleMoves).doesNotContain(
            TicTacToeMove(PlayerSymbol.O, 0, 0),
            TicTacToeMove(PlayerSymbol.O, 1, 1),
            TicTacToeMove(PlayerSymbol.O, 2, 2)
        )
    }

    @ParameterizedTest
    @MethodSource("provideWinningMoves")
    fun winningGames(moves: List<TicTacToeMove>, expectedResult: TicTacToeGameResult?) {
        val board = TicTacToeBoard(PlayerSymbol.X)
        for ((playerSymbol, row, column) in moves) {
            board.addMove(TicTacToeMove(playerSymbol, row, column))
        }
        Assertions.assertThat(board.isFinished).isTrue
        Assertions.assertThat(board.gameResult).isEqualTo(expectedResult)
    }

    companion object {
        @JvmStatic
        private fun provideWinningMoves(): Stream<Arguments> {
            return Stream.of( // win in a row
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.X, 0, 0),
                        TicTacToeMove(PlayerSymbol.X, 0, 1),
                        TicTacToeMove(PlayerSymbol.X, 0, 2)
                    ), TicTacToeGameResult.X_WON
                ),
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.X, 1, 0),
                        TicTacToeMove(PlayerSymbol.X, 1, 1),
                        TicTacToeMove(PlayerSymbol.X, 1, 2)
                    ), TicTacToeGameResult.X_WON
                ),
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.X, 2, 0),
                        TicTacToeMove(PlayerSymbol.X, 2, 1),
                        TicTacToeMove(PlayerSymbol.X, 2, 2)
                    ), TicTacToeGameResult.X_WON
                ),  // win in a column
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.O, 0, 0),
                        TicTacToeMove(PlayerSymbol.O, 1, 0),
                        TicTacToeMove(PlayerSymbol.O, 2, 0)
                    ), TicTacToeGameResult.O_WON
                ),
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.O, 0, 1),
                        TicTacToeMove(PlayerSymbol.O, 1, 1),
                        TicTacToeMove(PlayerSymbol.O, 2, 1)
                    ), TicTacToeGameResult.O_WON
                ),
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.O, 0, 2),
                        TicTacToeMove(PlayerSymbol.O, 1, 2),
                        TicTacToeMove(PlayerSymbol.O, 2, 2)
                    ), TicTacToeGameResult.O_WON
                ),  // win in a diagonal
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.X, 0, 0),
                        TicTacToeMove(PlayerSymbol.X, 1, 1),
                        TicTacToeMove(PlayerSymbol.X, 2, 2)
                    ), TicTacToeGameResult.X_WON
                ),
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.O, 0, 2),
                        TicTacToeMove(PlayerSymbol.O, 1, 1),
                        TicTacToeMove(PlayerSymbol.O, 2, 0)
                    ), TicTacToeGameResult.O_WON
                ),  // draw  game
                Arguments.of(
                    java.util.List.of(
                        TicTacToeMove(PlayerSymbol.X, 0, 0),
                        TicTacToeMove(PlayerSymbol.X, 0, 1),
                        TicTacToeMove(PlayerSymbol.O, 0, 2),
                        TicTacToeMove(PlayerSymbol.O, 1, 0),
                        TicTacToeMove(PlayerSymbol.O, 1, 1),
                        TicTacToeMove(PlayerSymbol.X, 1, 2),
                        TicTacToeMove(PlayerSymbol.X, 2, 0),
                        TicTacToeMove(PlayerSymbol.X, 2, 1),
                        TicTacToeMove(PlayerSymbol.O, 2, 2)
                    ), TicTacToeGameResult.DRAW
                )
            )
        }
    }
}