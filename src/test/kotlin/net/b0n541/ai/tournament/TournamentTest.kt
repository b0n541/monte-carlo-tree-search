package net.b0n541.ai.tournament

import net.b0n541.ai.game.tictactoe.player.MctsTicTacToePlayer
import net.b0n541.ai.game.tictactoe.player.RandomTicTacToePlayer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TournamentTest {
    @Test
    fun `2 player types in 2 player game`() {
        val first = RandomTicTacToePlayer()
        val second = MctsTicTacToePlayer()

        val result = getPlayerCombinations(2, listOf(first, second))

        assertThat(result).containsExactlyInAnyOrder(
            listOf(first, first),
            listOf(first, second),
            listOf(second, first),
            listOf(second, second)
        )
    }
}