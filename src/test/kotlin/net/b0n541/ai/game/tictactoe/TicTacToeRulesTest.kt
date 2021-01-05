package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.game.tictactoe.TicTacToeRules.hasWon
import net.b0n541.ai.game.tictactoe.TicTacToeRules.isBoardFull
import net.b0n541.ai.game.tictactoe.TicTacToeRules.isDraw
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class TicTacToeRulesTest {

    @Test
    fun gameStart() {
        Assertions.assertThat(isBoardFull(0, 0)).isFalse
        Assertions.assertThat(hasWon(0)).isFalse
        Assertions.assertThat(isDraw(0, 0)).isTrue
    }

    @Test
    fun boardFull() {
        checkBoardFullAndDraw(270, 241)
        checkBoardFullAndDraw(156, 355)
        checkBoardFullAndDraw(284, 227)
    }

    companion object {
        private fun checkBoardFullAndDraw(noughts: Int, crosses: Int) {
            Assertions.assertThat(isBoardFull(noughts, crosses)).isTrue
            Assertions.assertThat(isDraw(noughts, crosses)).isTrue
        }
    }
}