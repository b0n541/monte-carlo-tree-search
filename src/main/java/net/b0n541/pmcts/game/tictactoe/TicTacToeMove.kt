package net.b0n541.pmcts.game.tictactoe;

import net.b0n541.pmcts.mcts.GameMove;

import java.util.Objects;

public final class TicTacToeMove implements GameMove {
    public final PlayerSymbol playerSymbol;
    public final int row;
    public final int column;

    public TicTacToeMove(final PlayerSymbol playerSymbol, final int row, final int column) {
        this.playerSymbol = playerSymbol;
        this.row = row;
        this.column = column;
    }

    @Override
    public String toShortString() {
        return "[" + row + "," + column + "]";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TicTacToeMove move = (TicTacToeMove) o;
        return row == move.row &&
                column == move.column &&
                playerSymbol == move.playerSymbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerSymbol, row, column);
    }

    @Override
    public String toString() {
        return "TicTacToeMove{" +
                "playerSymbol=" + playerSymbol +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
