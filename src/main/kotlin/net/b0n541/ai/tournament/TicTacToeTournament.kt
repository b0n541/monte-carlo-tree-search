package net.b0n541.ai.tournament

import net.b0n541.ai.game.tictactoe.TicTacToePlayerSymbol
import net.b0n541.ai.game.tictactoe.player.MctsTicTacToePlayer
import net.b0n541.ai.game.tictactoe.player.TicTacToePlayer
import net.b0n541.ai.tournament.elo.EloRating
import org.slf4j.LoggerFactory

class TicTacToeTournament(val player: List<TicTacToePlayer>) {

    companion object {
        private val LOG = LoggerFactory.getLogger(MctsTicTacToePlayer::class.java)
    }

    private val eloRatings: Map<String, EloRating> = player.associate { it.javaClass.name to EloRating() }

    fun getScores(): Map<String, EloRating> =
        eloRatings.toList().sortedByDescending { (_, value) -> value.rating }.toMap()

    fun run() {
        for (gameNo in 1..10) {
            LOG.info("Game no. $gameNo")
            player.forEach { it.startNewGame(TicTacToePlayerSymbol.O, TicTacToePlayerSymbol.O) }
        }
    }
}

fun permutations(player: List<TicTacToePlayer>, playerCountPerGame: Int): List<List<TicTacToePlayer>> {
    return listOf()
}