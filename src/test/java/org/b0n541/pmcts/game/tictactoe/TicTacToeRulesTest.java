package org.b0n541.pmcts.game.tictactoe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TicTacToeRulesTest {
    @Test
    public void gameStart() {
        final TicTacToeRules rules = new TicTacToeRules();

        assertThat(rules.hasWon(PlayerSymbol.X)).isFalse();
        assertThat(rules.hasWon(PlayerSymbol.O)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("winningMoves")
    public void winningGames(final List<TicTacToeMove> moves, final boolean expectedResult) {

        final TicTacToeRules rules = new TicTacToeRules();

        for (final TicTacToeMove move : moves) {
            rules.update(move.playerSymbol, move.row, move.column);
        }

        assertThat(rules.hasWon(moves.get(moves.size() - 1).playerSymbol)).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> winningMoves() {
        return Stream.of(
                // win in a row
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                                new TicTacToeMove(PlayerSymbol.X, 0, 1),
                                new TicTacToeMove(PlayerSymbol.X, 0, 2)), true),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 1, 0),
                                new TicTacToeMove(PlayerSymbol.X, 1, 1),
                                new TicTacToeMove(PlayerSymbol.X, 1, 2)), true),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 2, 0),
                                new TicTacToeMove(PlayerSymbol.X, 2, 1),
                                new TicTacToeMove(PlayerSymbol.X, 2, 2)), true),
                // win in a column
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 0),
                                new TicTacToeMove(PlayerSymbol.O, 1, 0),
                                new TicTacToeMove(PlayerSymbol.O, 2, 0)), true),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 1),
                                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                                new TicTacToeMove(PlayerSymbol.O, 2, 1)), true),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 2),
                                new TicTacToeMove(PlayerSymbol.O, 1, 2),
                                new TicTacToeMove(PlayerSymbol.O, 2, 2)), true),
                // win in a diagonal
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                                new TicTacToeMove(PlayerSymbol.X, 1, 1),
                                new TicTacToeMove(PlayerSymbol.X, 2, 2)), true),
                Arguments.of(
                        List.of(
                                new TicTacToeMove(PlayerSymbol.O, 0, 2),
                                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                                new TicTacToeMove(PlayerSymbol.O, 2, 0)), true)
        );
    }

    @Test
    public void possibleMoves() {
        final TicTacToeRules rules = new TicTacToeRules();

        assertThat(rules.getPossibleMoves(PlayerSymbol.X)).hasSize(9);

        rules.update(PlayerSymbol.X, 0, 0);

        List<TicTacToeMove> possibleMoves = rules.getPossibleMoves(PlayerSymbol.O);
        assertThat(possibleMoves).hasSize(8);
        assertThat(possibleMoves).doesNotContain(new TicTacToeMove(PlayerSymbol.O, 0, 0));

        rules.update(PlayerSymbol.O, 1, 1);

        possibleMoves = rules.getPossibleMoves(PlayerSymbol.X);
        assertThat(possibleMoves).hasSize(7);
        assertThat(possibleMoves).doesNotContain(
                new TicTacToeMove(PlayerSymbol.X, 0, 0),
                new TicTacToeMove(PlayerSymbol.X, 1, 1));

        rules.update(PlayerSymbol.X, 2, 2);

        possibleMoves = rules.getPossibleMoves(PlayerSymbol.O);
        assertThat(possibleMoves).hasSize(6);
        assertThat(possibleMoves).doesNotContain(
                new TicTacToeMove(PlayerSymbol.O, 0, 0),
                new TicTacToeMove(PlayerSymbol.O, 1, 1),
                new TicTacToeMove(PlayerSymbol.O, 2, 2));
    }
}
