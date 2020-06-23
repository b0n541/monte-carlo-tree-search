package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.GameMove;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

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

    @Test
    public void possibleMoves() {
        final TicTacToeBoard board = new TicTacToeBoard();

        assertThat(board.getPossibleMoves(PlayerSymbol.X)).hasSize(9);

        board.addMove(new TicTacToeMove(PlayerSymbol.X, 0, 0));

        List<GameMove> possibleMoves = board.getPossibleMoves(PlayerSymbol.O);
        assertThat(possibleMoves).hasSize(8);
        assertThat(possibleMoves).doesNotContain(new TicTacToeMove(PlayerSymbol.O, 0, 0));

        board.addMove(new TicTacToeMove(PlayerSymbol.O, 1, 1));

        possibleMoves = board.getPossibleMoves(PlayerSymbol.X);
        assertThat(possibleMoves).hasSize(7);
        assertThat(possibleMoves).doesNotContain(
                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                new TicTacToeMove(PlayerSymbol.X, 1, 1));

        board.addMove(new TicTacToeMove(PlayerSymbol.X, 2, 2));

        possibleMoves = board.getPossibleMoves(PlayerSymbol.O);
        assertThat(possibleMoves).hasSize(6);
        assertThat(possibleMoves).doesNotContain(
                new TicTacToeMove(PlayerSymbol.O, 0, 0),
                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                new TicTacToeMove(PlayerSymbol.O, 2, 2));
    }

    @ParameterizedTest
    @MethodSource("provideWinningMoves")
    public void winningGames(final List<TicTacToeMove> moves, final GameResult expectedResult) {

        final TicTacToeBoard board = new TicTacToeBoard();

        for (final TicTacToeMove move : moves) {
            board.addMove(new TicTacToeMove(move.playerSymbol, move.row, move.column));
        }

        assertThat(board.isFinished()).isTrue();
        assertThat(board.getGameResult()).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideWinningMoves() {
        return Stream.of(
                // win in a row
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                                new TicTacToeMove(PlayerSymbol.X, 0, 1),
                                new TicTacToeMove(PlayerSymbol.X, 0, 2)), GameResult.X_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 1, 0),
                                new TicTacToeMove(PlayerSymbol.X, 1, 1),
                                new TicTacToeMove(PlayerSymbol.X, 1, 2)), GameResult.X_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 2, 0),
                                new TicTacToeMove(PlayerSymbol.X, 2, 1),
                                new TicTacToeMove(PlayerSymbol.X, 2, 2)), GameResult.X_WON),
                // win in a column
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 0),
                                new TicTacToeMove(PlayerSymbol.O, 1, 0),
                                new TicTacToeMove(PlayerSymbol.O, 2, 0)), GameResult.O_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 1),
                                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                                new TicTacToeMove(PlayerSymbol.O, 2, 1)), GameResult.O_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 2),
                                new TicTacToeMove(PlayerSymbol.O, 1, 2),
                                new TicTacToeMove(PlayerSymbol.O, 2, 2)), GameResult.O_WON),
                // win in a diagonal
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                                new TicTacToeMove(PlayerSymbol.X, 1, 1),
                                new TicTacToeMove(PlayerSymbol.X, 2, 2)), GameResult.X_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 2),
                                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                                new TicTacToeMove(PlayerSymbol.O, 2, 0)), GameResult.O_WON),
                // draw  game
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                                new TicTacToeMove(PlayerSymbol.X, 0, 1),
                                new TicTacToeMove(PlayerSymbol.O, 0, 2),
                                new TicTacToeMove(PlayerSymbol.O, 1, 0),
                                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                                new TicTacToeMove(PlayerSymbol.X, 1, 2),
                                new TicTacToeMove(PlayerSymbol.X, 2, 0),
                                new TicTacToeMove(PlayerSymbol.X, 2, 1),
                                new TicTacToeMove(PlayerSymbol.O, 2, 2)), GameResult.DRAW)
        );
    }
}
