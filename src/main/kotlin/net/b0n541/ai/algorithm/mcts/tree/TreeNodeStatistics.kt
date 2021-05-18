package net.b0n541.ai.algorithm.mcts.tree

class TreeNodeStatistics(players: List<String>) {
    private val totalScores: MutableMap<String, Double> = mutableMapOf()
    fun totalScores() = totalScores.toMap()

    init {
        require(players.isNotEmpty()) { "At least one player needs to be defined." }

        for (player in players) {
            totalScores[player] = 0.0
        }
    }

    var visits: Long = 0
        private set

    var currentTraversals: Long = 0
        private set

    fun addTraversal() {
        //currentTraversals++
    }

    fun addScores(scores: Map<String, Double>) {
        for (player in scores.keys) {
            checkPlayer(player)
        }

        visits++
        //currentTraversals--
        scores.forEach { (key: String, value: Double) -> totalScores[key] = totalScores[key]!! + value }
    }

    fun getTotalScore(player: String?): Double {
        checkPlayer(player)
        return totalScores[player]!!
    }

    private fun checkPlayer(player: String?) {
        require(totalScores.containsKey(player)) { "Unknown player $player" }
    }
}