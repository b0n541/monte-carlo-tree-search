package net.b0n541.ai.game.tictactoe

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TicTacToeGameStateTest {
    @Test
    fun testNewGameState() {
        val state = TicTacToeGameState()

        assertThat(state.isGameFinished).isFalse
        assertThat(state.possibleMoves).hasSize(9)
        assertThat(state.gameValues).isEqualTo(mapOf("O" to 0.5, "X" to 0.5))
        assertThat(state.nextPlayer).isEqualTo("O")
    }

    @Test
    fun testAddMoves() {
        val state = TicTacToeGameState()
        val newState = state.addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0))

        assertThat(newState.nextPlayer).isEqualTo("X")

        val newState2 = newState.addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 1, 1))

        assertThat(newState2.nextPlayer).isEqualTo("O")
        assertThat(newState2.isGameFinished).isFalse
        assertThat(newState2.possibleMoves).hasSize(7)
        assertThat(newState2.gameValues).isEqualTo(mapOf("O" to 0.5, "X" to 0.5))
    }

    @Test
    fun testWinningGameFirstPlayerWin() {
        val state = TicTacToeGameState()
        val newState = state
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 1, 1))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 0, 1))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 2, 2))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 0, 2))

        assertThat(newState.isGameFinished).isTrue
        assertThat(newState.possibleMoves).hasSize(0)
        assertThat(newState.gameValues).isEqualTo(mapOf("O" to 1.0, "X" to 0.0))
    }

    @Test
    fun testWinningGameFirstPlayerLoose() {
        val state = TicTacToeGameState()
        val newState = state
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 1, 0))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 0, 1))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 1, 1))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.O, 2, 0))
            .addMove(TicTacToeMove(TicTacToePlayerSymbol.X, 1, 2))
        assertThat(newState.isGameFinished).isTrue
        assertThat(newState.possibleMoves).hasSize(0)
        assertThat(newState.gameValues).isEqualTo(mapOf("O" to 0.0, "X" to 1.0))
    }
}