package org.b0n541.pmcts.game.tictactoe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicTacToeBoardTest {
    @Test
    public void emptyBoard() {
        final TicTacToeBoard board = new TicTacToeBoard();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertThat(board.get(row, column)).isNull();
            }
        }

        assertThat(board.getMoves()).hasSize(0);
    }

    @Test
    public void addMove() {
        final TicTacToeBoard board = new TicTacToeBoard();
        board.addMove(new TicTacToeMove(PlayerSymbol.X, 0, 0));

        assertThat(board.get(0, 0)).isEqualTo(PlayerSymbol.X);
    }

    @Test
    public void addMoveFieldAlreadyOccupied() {
        final TicTacToeBoard board = new TicTacToeBoard();
        board.addMove(new TicTacToeMove(PlayerSymbol.X, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> board.addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0)));
        assertThat(board.get(0, 0)).isEqualTo(PlayerSymbol.X);
    }
}
