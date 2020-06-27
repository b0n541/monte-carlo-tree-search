package org.b0n541.pmcts.game.tictactoe;

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

    private PlayerSymbol nextPlayer;

    private final List<TicTacToeMove> moves = new ArrayList<>();

    private final TicTacToeRules rules = new TicTacToeRules();

    private boolean noughtsWon;
    private boolean crossesWon;
    private boolean isDraw;

    public TicTacToeBoard(final PlayerSymbol firstPlayer) {
        nextPlayer = firstPlayer;
    }

    public PlayerSymbol get(final int row, final int column) {
        return board[row][column];
    }

    public PlayerSymbol getNextPlayer() {
        return nextPlayer;
    }

    public List<TicTacToeMove> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public List<TicTacToeMove> getPossibleMoves() {
        if (isFinished()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(TicTacToeRules.getPossibleMoves(nextPlayer, noughts, crosses));
    }

    public void addMove(final TicTacToeMove move) {

        addMoveInternal(move);
        switchPlayer();
    }

    private void addMoveInternal(final TicTacToeMove move) {
        if (board[move.row][move.column] != null) {
            throw new IllegalArgumentException("Board position already occupied.");
        }

        board[move.row][move.column] = move.playerSymbol;
        updateNoughtsAndCrosses(move.playerSymbol, move.row, move.column);
        moves.add(move);

        if (move.playerSymbol == PlayerSymbol.O) {
            noughtsWon = TicTacToeRules.hasWon(noughts);
        } else if (move.playerSymbol == PlayerSymbol.X) {
            crossesWon = TicTacToeRules.hasWon(crosses);
        }

        if (TicTacToeRules.isBoardFull(noughts, crosses)) {
            isDraw = rules.isDraw(noughts, crosses);
        }
    }

    private void switchPlayer() {
        if (nextPlayer == PlayerSymbol.O) {
            nextPlayer = PlayerSymbol.X;
        } else if (nextPlayer == PlayerSymbol.X) {
            nextPlayer = PlayerSymbol.O;
        }
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

    public TicTacToeGameResult getGameResult() {
        if (noughtsWon) {
            return TicTacToeGameResult.O_WON;
        } else if (crossesWon) {
            return TicTacToeGameResult.X_WON;
        }
        return TicTacToeGameResult.DRAW;
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
