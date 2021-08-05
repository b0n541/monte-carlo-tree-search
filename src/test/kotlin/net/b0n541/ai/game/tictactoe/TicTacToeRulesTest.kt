package net.b0n541.ai.game.tictactoe

import net.b0n541.ai.game.tictactoe.TicTacToeRules.getPossibleMoves
import net.b0n541.ai.game.tictactoe.TicTacToeRules.hasWon
import net.b0n541.ai.game.tictactoe.TicTacToeRules.isBoardFull
import net.b0n541.ai.game.tictactoe.TicTacToeRules.isDraw
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TicTacToeRulesTest {

    @Test
    fun gameStart() {
        assertThat(isBoardFull(0, 0)).isFalse
        assertThat(hasWon(0)).isFalse
        assertThat(isDraw(0, 0)).isTrue
    }

    @Test
    fun boardFull() {
        checkBoardFullAndDraw(270, 241)
        checkBoardFullAndDraw(156, 355)
        checkBoardFullAndDraw(284, 227)

        assertThat(isBoardFull(0b111000000, 0b000111000)).isFalse
    }

    @Test
    fun possibleMoves() {

        assertThat(getPossibleMoves(TicTacToePlayerSymbol.O, 0b111000000, 0b000111000))
            .containsExactlyInAnyOrder(
                TicTacToeMove(TicTacToePlayerSymbol.O, 0, 0),
                TicTacToeMove(TicTacToePlayerSymbol.O, 0, 1),
                TicTacToeMove(TicTacToePlayerSymbol.O, 0, 2)
            )

        assertThat(getPossibleMoves(TicTacToePlayerSymbol.X, 0b001010100, 0b110000000))
            .containsExactlyInAnyOrder(
                TicTacToeMove(TicTacToePlayerSymbol.X, 0, 0),
                TicTacToeMove(TicTacToePlayerSymbol.X, 0, 1),
                TicTacToeMove(TicTacToePlayerSymbol.X, 1, 0),
                TicTacToeMove(TicTacToePlayerSymbol.X, 1, 2)
            )
    }

    companion object {
        private fun checkBoardFullAndDraw(noughts: Int, crosses: Int) {
            assertThat(isBoardFull(noughts, crosses)).isTrue
            assertThat(isDraw(noughts, crosses)).isTrue
        }
    }
}