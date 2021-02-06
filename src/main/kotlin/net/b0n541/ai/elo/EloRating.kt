package net.b0n541.ai.elo

import kotlin.math.pow
import kotlin.math.round

class EloRating {

    val playerGames = mutableMapOf<String, Int>()
    val playerRanking = mutableMapOf<String, Int>()

    companion object {
        @JvmStatic
        fun winPropability(eloRankPlayerA: Int, eloRankPlayerB: Int): Double {
            return 1.0 / (1 + 10.0.pow((eloRankPlayerB - eloRankPlayerA) / 400.0))
        }

        @JvmStatic
        fun newRating(playerRating: Int, gameResults: List<Pair<Int, Double>>): Int {
            val expectedScore = gameResults.map { winPropability(playerRating, it.first) }.sum()
            val actualScore = gameResults.map { it.second }.sum()
            return round(playerRating + 32 * (actualScore - expectedScore)).toInt()
        }
    }

    fun addGame(gameResult: Double, vararg players: String) {

    }
}