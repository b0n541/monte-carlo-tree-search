package net.b0n541.pmcts.game.tictactoe;

import java.util.Scanner;

public final class HumanTicTacToePlayer extends TicTacToePlayer {

    public HumanTicTacToePlayer(final PlayerSymbol playerSymbol, final PlayerSymbol firstPlayer) {
        super(playerSymbol, firstPlayer);
    }

    @Override
    public TicTacToeMove play() {
        System.out.println("Your move...");
        System.out.print("Row: ");
        final Scanner scanner = new Scanner(System.in);
        final int row = scanner.nextInt();
        System.out.print("Column: ");
        final int column = scanner.nextInt();

        return new TicTacToeMove(playerSymbol, row, column);
    }
}
