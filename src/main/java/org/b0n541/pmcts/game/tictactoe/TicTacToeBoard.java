package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.GameMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TicTacToeBoard {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToeBoard.class);

    private final PlayerSymbol[][] board = {
            {null, null, null},
            {null, null, null},
            {null, null, null}
    };
    private int noughts;
    private int crosses;

    private final List<TicTacToeMove> moves = new ArrayList<>();

    private final TicTacToeRules rules = new TicTacToeRules();

    private boolean noughtsWon;
    private boolean crossesWon;
    private boolean isDraw;

    public PlayerSymbol get(final int row, final int column) {
        return board[row][column];
    }

    public List<TicTacToeMove> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public List<GameMove> getPossibleMoves(final PlayerSymbol player) {
        return Collections.unmodifiableList(TicTacToeRules.getPossibleMoves(player, noughts, crosses));
    }

    public void addMove(final TicTacToeMove move) {

        if (board[move.row][move.column] != null) {
            throw new IllegalArgumentException("Board position already occupied.");
        }

        board[move.row][move.column] = move.playerSymbol;
        moves.add(move);
        updateNoughtsAndCrosses(move.playerSymbol, move.row, move.column);

        if (move.playerSymbol == PlayerSymbol.O) {
            noughtsWon = TicTacToeRules.hasWon(noughts);
        } else if (move.playerSymbol == PlayerSymbol.X) {
            crossesWon = TicTacToeRules.hasWon(crosses);
        }

        if (TicTacToeRules.isBoardFull(noughts, crosses)) {
            isDraw = rules.isDraw(noughts, crosses);
        }

        LOG.info(format());
    }

    private void updateNoughtsAndCrosses(final PlayerSymbol playerSymbol, final int row, final int column) {
        final int bitPosition = row * 3 + column;

        if (playerSymbol == PlayerSymbol.O) {
            noughts = noughts | (0x1 << bitPosition);
        } else if (playerSymbol == PlayerSymbol.X) {
            crosses = crosses | (0x1 << bitPosition);
        }
    }

    public boolean isFinished() {
        return noughtsWon || crossesWon || isDraw;
    }

    public GameResult getGameResult() {
        if (noughtsWon) {
            return GameResult.O_WON;
        } else if (crossesWon) {
            return GameResult.X_WON;
        }
        return GameResult.DRAW;
    }

    public String format() {

        final StringBuilder builder = new StringBuilder();

        builder.append("\n\n");

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                builder.append(board[row][column] == null ? "   " : " " + board[row][column] + " ");
                if (column != 2) {
                    builder.append("|");
                }
            }
            builder.append('\n');
            if (row != 2) {
                builder.append("-----------\n");
            }
        }

        return builder.toString();
    }
}
