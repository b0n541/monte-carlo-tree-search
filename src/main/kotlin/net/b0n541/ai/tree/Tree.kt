package net.b0n541.ai.tree

import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.GameState
import org.slf4j.LoggerFactory

class Tree(gameState: GameState<GameMove>) {

    val rootNode: TreeNode = TreeNode(null, gameState)

    val size: Long
        get() = getSize(rootNode)

    fun printDigraph() {
        println("======================================")
        printTreeLevel(rootNode)
        println("======================================")
    }

    val bestMove: GameMove
        get() {
            var highestVisits: Long = 0
            var bestMoves = mutableListOf<GameMove>()

            rootNode.children()
                .forEach {
                    val move = it.gameState.lastMove
                    val visits = it.visits
                    val scores = it.totalScores
                    LOG.info("Move $move got visits $visits and scores $scores")
                    if (it.visits >= highestVisits) {
                        if (visits > highestVisits) {
                            bestMoves.clear()
                        }
                        highestVisits = visits
                        bestMoves.add(move)
                    }
                }

            LOG.info("Best move(s) $bestMoves with highest visits $highestVisits")

            return bestMoves.random()
        }

    companion object {
        private val LOG = LoggerFactory.getLogger(Tree::class.java)
        private fun getSize(node: TreeNode): Long {
            return 1 + node.children()
                .map { getSize(it) }
                .sum()
        }

        private fun printTreeLevel(node: TreeNode) {
            printNodeLabel(node)
            printNodeMoves(node)
            if (!node.isLeafNode) {
                node.children()
                    .filter { it.visits > 0 }
                    .forEach { printTreeLevel(it) }
            }
        }

        private fun printNodeLabel(node: TreeNode) {
            println("${node.nodeId} [label=\"${node.player}\\nv=${node.visits} s=${node.totalScores}\"]")
        }

        private fun printNodeMoves(parent: TreeNode) {
            parent.movesAndChildren()
                .filter { it.value.visits > 0 }
                .forEach {
                    println("${parent.nodeId} -> ${it.value.nodeId} [label=\"${it.key.toShortString()}\"]")
                }
        }
    }
}