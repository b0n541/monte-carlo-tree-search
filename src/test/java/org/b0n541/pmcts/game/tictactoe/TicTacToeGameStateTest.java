package org.b0n541.pmcts.game.tictactoe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TicTacToeGameStateTest {
    @Test
    public void testNewGameState() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O, PlayerSymbol.O);

        assertThat(state.isGameFinished()).isFalse();
        assertThat(state.getPossibleMoves()).hasSize(9);
        assertThat(state.getGameResult()).isEqualTo(0.0);
    }

    @Test
    public void testAddMoves() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O, PlayerSymbol.O);

        final TicTacToeGameState newState = state
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1));

        assertThat(newState.isGameFinished()).isFalse();
        assertThat(newState.getPossibleMoves()).hasSize(7);
        assertThat(newState.getGameResult()).isEqualTo(0.0);
    }

    @Test
    public void testWinningGame() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O, PlayerSymbol.O);

        final TicTacToeGameState newState = state
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 2, 2))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 2));

        assertThat(newState.isGameFinished()).isTrue();
        assertThat(newState.getPossibleMoves()).hasSize(0);
        assertThat(newState.getGameResult()).isEqualTo(1.0);
    }

    @Test
    public void testLoosingGame() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.X, PlayerSymbol.O);

        final TicTacToeGameState newState = state
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 2, 2))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 2));

        assertThat(newState.isGameFinished()).isTrue();
        assertThat(newState.getPossibleMoves()).hasSize(0);
        assertThat(newState.getGameResult()).isEqualTo(-1.0);
    }
}
