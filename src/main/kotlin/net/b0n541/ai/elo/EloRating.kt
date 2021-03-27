package net.b0n541.ai.elo

import kotlin.math.pow
import kotlin.math.round

class EloRating {

    val players = mutableMapOf<String, PlayerRating>()

    companion object {
        @JvmStatic
        fun winPropability(eloRankPlayerA: Int, eloRankPlayerB: Int): Double {
            return 1.0 / (1 + 10.0.pow((eloRankPlayerB - eloRankPlayerA) / 400.0))
        }

        private val K_FACTOR = 32

        @JvmStatic
        fun newRating(playerRating: Int, gameResults: List<Pair<Int, Double>>): Int {
            val expectedScore = gameResults.map { winPropability(playerRating, it.first) }.sum()
            val actualScore = gameResults.map { it.second }.sum()
            return round(playerRating + K_FACTOR * (actualScore - expectedScore)).toInt()
        }
    }

    data class PlayerRating(val gameCount: Long = 0, val rating: Int = 1000)
}