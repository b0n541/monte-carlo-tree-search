package net.b0n541.pmcts.game.tictactoe;

import org.assertj.core.api.Assertions;
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
        final TicTacToeBoard board = new TicTacToeBoard(PlayerSymbol.O);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertThat(board.get(row, column)).isNull();
            }
        }

        assertThat(board.getMoves()).hasSize(0);
    }

    @Test
    public void addMove() {
        final TicTacToeBoard board = new TicTacToeBoard(PlayerSymbol.X);
        board.addMove(new TicTacToeMove(PlayerSymbol.X, 0, 0));

        assertThat(board.get(0, 0)).isEqualTo(PlayerSymbol.X);
    }

    @Test
    public void addMoveFieldAlreadyOccupied() {
        final TicTacToeBoard board = new TicTacToeBoard(PlayerSymbol.X);
        board.addMove(new TicTacToeMove(PlayerSymbol.X, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> board.addMove(new TicTacToeMove(PlayerSymbol.O, 0, 0)));
        assertThat(board.get(0, 0)).isEqualTo(PlayerSymbol.X);
    }

    @Test
    public void possibleMoves() {
        final TicTacToeBoard board = new TicTacToeBoard(PlayerSymbol.X);

        assertThat(board.getPossibleMoves()).hasSize(9);

        board.addMove(new TicTacToeMove(PlayerSymbol.X, 0, 0));

        List<TicTacToeMove> possibleMoves = board.getPossibleMoves();
        Assertions.assertThat(possibleMoves).hasSize(8);
        Assertions.assertThat(possibleMoves).doesNotContain(new TicTacToeMove(PlayerSymbol.O, 0, 0));

        board.addMove(new TicTacToeMove(PlayerSymbol.O, 1, 1));

        possibleMoves = board.getPossibleMoves();
        Assertions.assertThat(possibleMoves).hasSize(7);
        Assertions.assertThat(possibleMoves).doesNotContain(
                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                new TicTacToeMove(PlayerSymbol.X, 1, 1));

        board.addMove(new TicTacToeMove(PlayerSymbol.X, 2, 2));

        possibleMoves = board.getPossibleMoves();
        Assertions.assertThat(possibleMoves).hasSize(6);
        Assertions.assertThat(possibleMoves).doesNotContain(
                new TicTacToeMove(PlayerSymbol.O, 0, 0),
                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                new TicTacToeMove(PlayerSymbol.O, 2, 2));
    }

    @ParameterizedTest
    @MethodSource("provideWinningMoves")
    public void winningGames(final List<TicTacToeMove> moves, final TicTacToeGameResult expectedResult) {

        final TicTacToeBoard board = new TicTacToeBoard(PlayerSymbol.X);

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
                                new TicTacToeMove(PlayerSymbol.X, 0, 2)), TicTacToeGameResult.X_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 1, 0),
                                new TicTacToeMove(PlayerSymbol.X, 1, 1),
                                new TicTacToeMove(PlayerSymbol.X, 1, 2)), TicTacToeGameResult.X_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 2, 0),
                                new TicTacToeMove(PlayerSymbol.X, 2, 1),
                                new TicTacToeMove(PlayerSymbol.X, 2, 2)), TicTacToeGameResult.X_WON),
                // win in a column
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 0),
                                new TicTacToeMove(PlayerSymbol.O, 1, 0),
                                new TicTacToeMove(PlayerSymbol.O, 2, 0)), TicTacToeGameResult.O_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 1),
                                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                                new TicTacToeMove(PlayerSymbol.O, 2, 1)), TicTacToeGameResult.O_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 2),
                                new TicTacToeMove(PlayerSymbol.O, 1, 2),
                                new TicTacToeMove(PlayerSymbol.O, 2, 2)), TicTacToeGameResult.O_WON),
                // win in a diagonal
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                                new TicTacToeMove(PlayerSymbol.X, 1, 1),
                                new TicTacToeMove(PlayerSymbol.X, 2, 2)), TicTacToeGameResult.X_WON),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 2),
                                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                                new TicTacToeMove(PlayerSymbol.O, 2, 0)), TicTacToeGameResult.O_WON),
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
                                new TicTacToeMove(PlayerSymbol.O, 2, 2)), TicTacToeGameResult.DRAW)
        );
    }
}
