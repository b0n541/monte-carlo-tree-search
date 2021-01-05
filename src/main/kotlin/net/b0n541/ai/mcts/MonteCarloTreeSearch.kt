package net.b0n541.ai.mcts

import org.slf4j.LoggerFactory

object MonteCarloTreeSearch {
    private val LOG = LoggerFactory.getLogger(MonteCarloTreeSearch::class.java)

    @JvmStatic
    fun run(tree: Tree, rounds: Int) {
        for (i in 0 until rounds) {
            val leafNode = traverseTree(tree)
            val rollOutNode = expandTree(leafNode)
            val gameValues = rollOut(rollOutNode)
            backPropagation(rollOutNode, gameValues)
        }
    }

    private fun traverseTree(tree: Tree): TreeNode {
        var nextNode = tree.rootNode
        while (!nextNode.isLeafNode) {
            var bestChildNode: TreeNode? = null
            var bestUcb1Value = -1 * Double.MAX_VALUE
            for (node in nextNode.children()) {
                val ucb1Value = node.ucb1Value
                if (ucb1Value > bestUcb1Value) {
                    bestChildNode = node
                    bestUcb1Value = ucb1Value
                }
            }
            nextNode = bestChildNode!!
        }
        return nextNode
    }

    private fun expandTree(node: TreeNode): TreeNode {
        if (node.isRootNode && node.isLeafNode) {
            return expandPossibleMoves(node)
        }

        return if (node.visits == 0L || node.gameState.isGameFinished) {
            node
        } else {
            expandPossibleMoves(node)
        }
    }

    private fun expandPossibleMoves(node: TreeNode): TreeNode {
        node.expandPossibleMoves()
        return node.children()[0]
    }

    private fun rollOut(node: TreeNode): Map<String, Double> {
        var currentState = node.gameState
        while (!currentState.isGameFinished) {
            // TODO add other roll out functions
            currentState = currentState.addMove(currentState.possibleMoves.random())
        }
        return currentState.gameValues
    }

    private fun backPropagation(node: TreeNode, gameValues: Map<String, Double>) {
        var currentNode = node
        do {
            currentNode.addVisit(gameValues)
            currentNode = currentNode.parent!!
        } while (!currentNode.isRootNode)
        currentNode.addVisit(gameValues)
    }
}