package org.b0n541.pmcts.game.tictactoe;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TicTacToeGameStateTest {
    @Test
    public void testNewGameState() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O, PlayerSymbol.X, PlayerSymbol.O);

        assertThat(state.isGameFinished()).isFalse();
        assertThat(state.getPossibleMoves()).hasSize(9);
        assertThat(state.getGameValues()).isEqualTo(Map.of("O", 0.5, "X", 0.5));
        assertThat(state.getPlayer()).isEqualTo("O");
    }

    @Test
    public void testAddMoves() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O, PlayerSymbol.X, PlayerSymbol.O);

        final TicTacToeGameState newState = state.addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0));

        assertThat(newState.getPlayer()).isEqualTo("X");

        final TicTacToeGameState newState2 = newState.addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1));

        assertThat(newState2.getPlayer()).isEqualTo("O");

        assertThat(newState2.isGameFinished()).isFalse();
        assertThat(newState2.getPossibleMoves()).hasSize(7);
        assertThat(newState2.getGameValues()).isEqualTo(Map.of("O", 0.5, "X", 0.5));
    }

    @Test
    public void testWinningGameFirstPlayerWin() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O, PlayerSymbol.X, PlayerSymbol.O);

        final TicTacToeGameState newState = state
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 2, 2))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 2));

        assertThat(newState.isGameFinished()).isTrue();
        assertThat(newState.getPossibleMoves()).hasSize(0);
        assertThat(newState.getGameValues()).isEqualTo(Map.of("O", 1.0, "X", 0.0));
    }

    @Test
    public void testWinningGameFirstPlayerLoose() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O, PlayerSymbol.X, PlayerSymbol.O);

        final TicTacToeGameState newState = state
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 2, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 2));

        assertThat(newState.isGameFinished()).isTrue();
        assertThat(newState.getPossibleMoves()).hasSize(0);
        assertThat(newState.getGameValues()).isEqualTo(Map.of("O", 0.0, "X", 1.0));
    }
}
