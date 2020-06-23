package org.b0n541.pmcts.game.tictactoe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TicTacToeRulesTest {
    @Test
    public void gameStart() {
        assertThat(TicTacToeRules.isBoardFull(0, 0)).isFalse();
        assertThat(TicTacToeRules.hasWon(0)).isFalse();
        assertThat(TicTacToeRules.isDraw(0, 0)).isTrue();
    }

    @Test
    public void boardFull() {
        checkBoardFullAndDraw(0b100001110, 0b011110001);
        checkBoardFullAndDraw(0b010011100, 0b101100011);
        checkBoardFullAndDraw(0b100011100, 0b011100011);
    }

    private static void checkBoardFullAndDraw(final int noughts, final int crosses) {
        assertThat(TicTacToeRules.isBoardFull(noughts, crosses)).isTrue();
        assertThat(TicTacToeRules.isDraw(noughts, crosses)).isTrue();
    }
}
