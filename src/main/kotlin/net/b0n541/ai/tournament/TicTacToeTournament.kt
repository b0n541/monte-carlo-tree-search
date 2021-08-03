package net.b0n541.ai.tournament

import net.b0n541.ai.game.tictactoe.TicTacToe
import net.b0n541.ai.game.tictactoe.TicTacToePlayerSymbol
import net.b0n541.ai.game.tictactoe.player.MctsTicTacToePlayer
import net.b0n541.ai.game.tictactoe.player.TicTacToePlayer
import net.b0n541.ai.tournament.elo.EloRating
import org.slf4j.LoggerFactory

class TicTacToeTournament(val player: List<TicTacToePlayer>) {

    companion object {
        private val LOG = LoggerFactory.getLogger(MctsTicTacToePlayer::class.java)
    }

    private val eloRatings: MutableMap<String, EloRating> =
        player.associate { it.javaClass.name to EloRating() }.toMutableMap()

    fun getScores(): Map<String, EloRating> =
        eloRatings.toList().sortedByDescending { (_, value) -> value.rating }.toMap()

    fun run() {
        val games = getPlayerCombinations(2, player)

        var gameNo = 0
        for (gamePlayer in games) {
            gameNo++
            LOG.info("Game no. $gameNo")
            val ticTacToe = TicTacToe(
                noughtsPlayer = gamePlayer[0],
                crossesPlayer = gamePlayer[1]
            )
            ticTacToe.play()
            val gameValues = ticTacToe.getGameValues()
            val noughtsRating = eloRatings[player[0].javaClass.name]!!
            val crossesRating = eloRatings[player[1].javaClass.name]!!

            eloRatings[player[0].javaClass.name] =
                noughtsRating.addGame(crossesRating.rating, gameValues[TicTacToePlayerSymbol.O.toString()]!!)
            eloRatings[player[1].javaClass.name] =
                crossesRating.addGame(noughtsRating.rating, gameValues[TicTacToePlayerSymbol.X.toString()]!!)
        }
    }
}
