package net.b0n541.ai.elo

import kotlin.math.pow
import kotlin.math.round

data class EloRating(val gameCount: Long = 0, val rating: Int = 1000) {

    private val kFactor = 32

    fun addGame(opponentRating: Int, gameResult: Double): EloRating {
        return addGames(listOf(Pair(opponentRating, gameResult)))
    }

    fun addGames(gameResults: List<Pair<Int, Double>>): EloRating {
        val expectedScore = gameResults.map { expectedScore(rating, it.first) }.sum()
        val actualScore = gameResults.map { it.second }.sum()
        val newRating = round(rating + kFactor * (actualScore - expectedScore)).toInt()
        return EloRating(gameCount + gameResults.size, newRating)
    }
}

fun expectedScore(eloRatingPlayerA: Int, eloRatingPlayerB: Int): Double {
    return 1.0 / (1 + 10.0.pow((eloRatingPlayerB - eloRatingPlayerA) / 400.0))
}
