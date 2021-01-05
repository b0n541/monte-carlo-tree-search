package net.b0n541.pmcts.game.tictactoe

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class TicTacToeGameStateTest {
    @Test
    fun testNewGameState() {
        val state = TicTacToeGameState(PlayerSymbol.O)
        Assertions.assertThat(state.isGameFinished).isFalse
        Assertions.assertThat(state.possibleMoves).hasSize(9)
        Assertions.assertThat(state.gameValues).isEqualTo(mapOf("O" to 0.5, "X" to 0.5))
        Assertions.assertThat(state.nextPlayer).isEqualTo("O")
    }

    @Test
    fun testAddMoves() {
        val state = TicTacToeGameState(PlayerSymbol.O)
        val newState = state.addMove(TicTacToeMove(PlayerSymbol.O, 0, 0))
        Assertions.assertThat(newState.nextPlayer).isEqualTo("X")
        val newState2 = newState.addMove(TicTacToeMove(PlayerSymbol.X, 1, 1))
        Assertions.assertThat(newState2.nextPlayer).isEqualTo("O")
        Assertions.assertThat(newState2.isGameFinished).isFalse
        Assertions.assertThat(newState2.possibleMoves).hasSize(7)
        Assertions.assertThat(newState2.gameValues).isEqualTo(mapOf("O" to 0.5, "X" to 0.5))
    }

    @Test
    fun testWinningGameFirstPlayerWin() {
        val state = TicTacToeGameState(PlayerSymbol.O)
        val newState = state
            .addMove(TicTacToeMove(PlayerSymbol.O, 0, 0))
            .addMove(TicTacToeMove(PlayerSymbol.X, 1, 1))
            .addMove(TicTacToeMove(PlayerSymbol.O, 0, 1))
            .addMove(TicTacToeMove(PlayerSymbol.X, 2, 2))
            .addMove(TicTacToeMove(PlayerSymbol.O, 0, 2))
        Assertions.assertThat(newState.isGameFinished).isTrue
        Assertions.assertThat(newState.possibleMoves).hasSize(0)
        Assertions.assertThat(newState.gameValues).isEqualTo(mapOf("O" to 1.0, "X" to 0.0))
    }

    @Test
    fun testWinningGameFirstPlayerLoose() {
        val state = TicTacToeGameState(PlayerSymbol.O)
        val newState = state
            .addMove(TicTacToeMove(PlayerSymbol.O, 0, 0))
            .addMove(TicTacToeMove(PlayerSymbol.X, 1, 0))
            .addMove(TicTacToeMove(PlayerSymbol.O, 0, 1))
            .addMove(TicTacToeMove(PlayerSymbol.X, 1, 1))
            .addMove(TicTacToeMove(PlayerSymbol.O, 2, 0))
            .addMove(TicTacToeMove(PlayerSymbol.X, 1, 2))
        Assertions.assertThat(newState.isGameFinished).isTrue
        Assertions.assertThat(newState.possibleMoves).hasSize(0)
        Assertions.assertThat(newState.gameValues).isEqualTo(mapOf("O" to 0.0, "X" to 1.0))
    }
}