package net.b0n541.pmcts.game.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TicTacToeRules {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToeRules.class);

    /**
     * Indices of board cells in the winning patterns.
     * <p>
     * 0 | 1 | 2
     * -----------
     * 3 | 4 | 5
     * -----------
     * 6 | 7 | 8
     */
    private static final int[] WINNING_PATTERNS = {
            0b000000111, // row 0
            0b000111000, // row 1
            0b111000000, // row 2
            0b001001001, // column 0
            0b010010010, // column 1
            0b100100100, // column 2
            0b100010001, // diagonal
            0b001010100  // diagonal reversed
    };

    private static final int FULL_FIELD = 0b111111111;

    public static boolean hasWon(final int playerPattern) {
        for (final int winningPattern : WINNING_PATTERNS) {
            if ((winningPattern & playerPattern) == winningPattern) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDraw(final int noughts, final int crosses) {
        return !hasWon(noughts) && !hasWon(crosses);
    }

    public static boolean isBoardFull(final int noughts, final int crosses) {
        return ((noughts | crosses) & FULL_FIELD) == FULL_FIELD;
    }

    public static List<TicTacToeMove> getPossibleMoves(final PlayerSymbol player, final int noughts, final int crosses) {

        final int freeCells = ~((noughts | crosses) & FULL_FIELD);

        final List<TicTacToeMove> result = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                final boolean free = (freeCells & (0x1 << row * 3 + column)) != 0;
                if (free) {
                    result.add(new TicTacToeMove(player, row, column));
                }
            }
        }

        return Collections.unmodifiableList(result);
    }
}
