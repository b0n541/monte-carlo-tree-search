package net.b0n541.ai.mcts

import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicLong

class TreeNode(val parent: TreeNode? = null, val gameState: GameState<GameMove>) {

    val nodeId = NODE_COUNTER.incrementAndGet().toString()

    private val children: MutableMap<GameMove, TreeNode> = mutableMapOf()
    fun children() = children.values.toList()
    fun movesAndChildren() = children.toMap()

    private val statistics: TreeNodeStatistics = TreeNodeStatistics(gameState.players)

    companion object {
        private val LOG = LoggerFactory.getLogger(TreeNode::class.java)
        private const val EXPLORATION_FACTOR = 2.0
        private val NODE_COUNTER = AtomicLong()
    }

    val isRootNode = parent == null

    val isLeafNode: Boolean
        get() = children.isEmpty()

    val totalScores: Map<String, Double>
        get() = statistics.totalScores()

    val visits: Long
        get() = statistics.visits

    val currentTraversals: Long
        get() = statistics.currentTraversals

    fun addTraversal() {
        statistics.addTraversal()
    }

    fun addScores(scores: Map<String, Double>) {
        statistics.addScores(scores)
    }

    val player: String
        get() = gameState.nextPlayer

    // TODO use better versions like UCB1-Tuned algorithm
    val ucb1Value: Double
        get() = if (isRootNode || statistics.visits == 0L) {
            Double.MAX_VALUE
        } else {
            val nodeVisits = visits.toDouble() + currentTraversals.toDouble()
            val parentVisits = parent!!.visits.toDouble() + parent!!.currentTraversals.toDouble()
            statistics.getTotalScore(parent!!.player) / nodeVisits +
                    EXPLORATION_FACTOR * Math.sqrt(Math.log(parentVisits) / nodeVisits)
        }

    fun expandPossibleMoves() {
        val possibleMoves = gameState.possibleMoves
        possibleMoves.forEach { children[it] = TreeNode(this, gameState.addMove(it)) }
    }

    override fun toString(): String {
        return "Visits: $visits Total scores: $totalScores UCB1: $ucb1Value"
    }
}