package org.b0n541.pmcts.game.tictactoe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TicTacToeGameStateTest {
    @Test
    public void testNewGameState() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O);

        assertThat(state.isGameFinished()).isFalse();
        assertThat(state.getPossibleMoves()).hasSize(9);
        assertThat(state.getGameResult()).isEqualTo(0.5);
    }

    @Test
    public void testAddMoves() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O);

        final TicTacToeGameState newState = state.addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0));

        assertThat(newState.getRootPlayerIndex()).isEqualTo(1);
        assertThat(newState.getPlayerIndex()).isEqualTo(0);

        final TicTacToeGameState newState2 = newState.addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1));

        assertThat(newState2.getRootPlayerIndex()).isEqualTo(1);
        assertThat(newState2.getPlayerIndex()).isEqualTo(1);

        assertThat(newState2.isGameFinished()).isFalse();
        assertThat(newState2.getPossibleMoves()).hasSize(7);
        assertThat(newState2.getGameResult()).isEqualTo(0.5);
    }

    @Test
    public void testWinningGameFirstPlayerWin() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O);

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
    public void testWinningGameFirstPlayerLoose() {
        final TicTacToeGameState state = new TicTacToeGameState(PlayerSymbol.O);

        final TicTacToeGameState newState = state
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 0, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 1))
                .addMove(new TicTacToeMove(PlayerSymbol.O, 2, 0))
                .addMove(new TicTacToeMove(PlayerSymbol.X, 1, 2));

        assertThat(newState.isGameFinished()).isTrue();
        assertThat(newState.getPossibleMoves()).hasSize(0);
        assertThat(newState.getGameResult()).isEqualTo(0.0);
    }
}
