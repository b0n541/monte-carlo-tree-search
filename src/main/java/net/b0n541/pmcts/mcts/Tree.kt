package net.b0n541.pmcts.mcts

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
            var bestMove: GameMove? = null

            rootNode.children()
                .forEach {
                    val move = it.gameState.lastMove
                    val visits = it.visits
                    val scores = it.totalScores
                    LOG.info("Move {} got visits {} and scores {}", move, visits, scores)
                    if (it.visits > highestVisits) {
                        highestVisits = visits
                        bestMove = move
                    }
                }

            LOG.info("Best move {} with highest visits {}", bestMove, highestVisits)
            return bestMove!!
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
            println("$node.nodeId [label=\"$node.player\n v=$node.visits s=$node.totalScores\"]")
        }

        private fun printNodeMoves(parent: TreeNode) {
            parent.children()
                .filter { it.visits > 0 }
                .forEach {
                    println("$parent.nodeId -> $it.value.nodeId [label=\"$it.key.toShortString()\"]")
                }
        }
    }
}