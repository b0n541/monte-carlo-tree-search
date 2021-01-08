package net.b0n541.ai.mcts

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test

internal class TreeNodeTest {
    @Test
    fun rootNodeSinglePlayerGame() {
        val rootNode = TreeNode(null, TestGameState(listOf("A")))

        assertThat(rootNode.isRootNode).isTrue
        assertThat(rootNode.isLeafNode).isTrue
        assertThat(rootNode.children()).hasSize(0)
        assertThat(rootNode.visits).isEqualTo(0)
        assertThat(rootNode.currentTraversals).isEqualTo(0)

        rootNode.expandPossibleMoves()

        assertThat(rootNode.isRootNode).isTrue
        assertThat(rootNode.isLeafNode).isFalse
        assertThat(rootNode.children()).hasSize(2)
        assertThat(rootNode.visits).isEqualTo(0)
        assertThat(rootNode.currentTraversals).isEqualTo(0)

        rootNode.addTraversal()

        assertThat(rootNode.isRootNode).isTrue
        assertThat(rootNode.isLeafNode).isFalse
        assertThat(rootNode.children()).hasSize(2)
        assertThat(rootNode.visits).isEqualTo(0)
        assertThat(rootNode.currentTraversals).isEqualTo(1)

        rootNode.addScores(mapOf("A" to 10.0))

        assertThat(rootNode.visits).isEqualTo(1)
        assertThat(rootNode.currentTraversals).isEqualTo(0)
        assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
    }

    @Test
    fun visitNodesSinglePlayerGame() {
        val rootNode = TreeNode(null, TestGameState(listOf("A")))
        rootNode.expandPossibleMoves()
        val leftChildNode = rootNode.children()[0]

        assertThat(leftChildNode.isRootNode).isFalse
        assertThat(leftChildNode.isLeafNode).isTrue
        assertThat(leftChildNode.parent).isEqualTo(rootNode)
        assertThat(leftChildNode.children()).hasSize(0)
        assertThat(leftChildNode.visits).isEqualTo(0)
        assertThat(leftChildNode.ucb1Value).isEqualTo(Double.MAX_VALUE)

        rootNode.addTraversal()
        leftChildNode.addTraversal()
        leftChildNode.addScores(mapOf("A" to 20.0))
        rootNode.addScores(mapOf("A" to 20.0))

        assertThat(rootNode.totalScores).isEqualTo(mapOf("A" to 20.0))
        assertThat(rootNode.visits).isEqualTo(1)
        assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        assertThat(leftChildNode.totalScores).isEqualTo(mapOf("A" to 20.0))
        assertThat(leftChildNode.visits).isEqualTo(1)
        assertThat(leftChildNode.ucb1Value).isEqualTo(20.0)

        val rightChildNode = rootNode.children()[1]

        assertThat(rightChildNode.totalScores).isEqualTo(mapOf("A" to 0.0))
        assertThat(rightChildNode.visits).isEqualTo(0)
        assertThat(rightChildNode.ucb1Value).isEqualTo(Double.MAX_VALUE)

        rootNode.addTraversal()
        rightChildNode.addTraversal()
        rightChildNode.addScores(mapOf("A" to 10.0))
        rootNode.addScores(mapOf("A" to 10.0))

        assertThat(rootNode.totalScores).isEqualTo(mapOf("A" to 30.0))
        assertThat(rootNode.visits).isEqualTo(2)
        assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        assertThat(leftChildNode.totalScores).isEqualTo(mapOf("A" to 20.0))
        assertThat(leftChildNode.visits).isEqualTo(1)
        assertThat(leftChildNode.ucb1Value).isCloseTo(21.67, within(0.01))
        assertThat(rightChildNode.totalScores).isEqualTo(mapOf("A" to 10.0))
        assertThat(rightChildNode.visits).isEqualTo(1)
        assertThat(rightChildNode.ucb1Value).isCloseTo(11.67, within(0.01))

        leftChildNode.expandPossibleMoves()
        val leftGrandChildNode = leftChildNode.children()[0]
        rootNode.addTraversal()
        leftChildNode.addTraversal()
        leftGrandChildNode.addTraversal()
        leftGrandChildNode.addScores(mapOf("A" to 15.0))
        leftChildNode.addScores(mapOf("A" to 15.0))
        rootNode.addScores(mapOf("A" to 15.0))

        assertThat(rootNode.totalScores).isEqualTo(mapOf("A" to 45.0))
        assertThat(rootNode.visits).isEqualTo(3)
        assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        assertThat(leftChildNode.totalScores).isEqualTo(mapOf("A" to 35.0))
        assertThat(leftChildNode.visits).isEqualTo(2)
        assertThat(leftChildNode.ucb1Value).isCloseTo(18.98, within(0.01))
        assertThat(rightChildNode.totalScores).isEqualTo(mapOf("A" to 10.0))
        assertThat(rightChildNode.visits).isEqualTo(1)
        assertThat(rightChildNode.ucb1Value).isCloseTo(12.10, within(0.01))
        assertThat(leftGrandChildNode.totalScores).isEqualTo(mapOf("A" to 15.0))
        assertThat(leftGrandChildNode.visits).isEqualTo(1)
        assertThat(leftGrandChildNode.ucb1Value).isCloseTo(16.67, within(0.01))

        val rightGrandChildNode = leftChildNode.children()[1]

        assertThat(rightGrandChildNode.totalScores).isEqualTo(mapOf("A" to 0.0))
        assertThat(rightGrandChildNode.visits).isEqualTo(0)
        assertThat(rightGrandChildNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
    }

    @Test
    fun parallelTraversal() {
        val rootNode = TreeNode(null, TestGameState(listOf("A")))
        rootNode.expandPossibleMoves()
        val leftChildNode = rootNode.children()[0]
        val rightChildNode = rootNode.children()[1]

        setNodeStatistics(rootNode, "A", 10, 6.0)
        setNodeStatistics(leftChildNode, "A", 5, 3.0)
        setNodeStatistics(rightChildNode, "A", 5, 2.0)

        rootNode.addTraversal()
        leftChildNode.addTraversal()

        var leftUcb1Value = leftChildNode.ucb1Value
        var rightUcb1Value = rightChildNode.ucb1Value

        assertThat(leftUcb1Value).isNotCloseTo(rightUcb1Value, Offset.offset(0.01))
        assertThat(rightUcb1Value).isGreaterThan(leftUcb1Value)

        rootNode.addTraversal()
        rightChildNode.addTraversal()

        leftUcb1Value = leftChildNode.ucb1Value
        rightUcb1Value = rightChildNode.ucb1Value

        assertThat(leftUcb1Value).isNotCloseTo(rightUcb1Value, Offset.offset(0.01))
        assertThat(leftUcb1Value).isGreaterThan(rightUcb1Value)
    }

    private fun setNodeStatistics(node: TreeNode, player: String, visits: Long, score: Double) {
        node.addTraversal()
        node.addScores(mapOf(player to score))
        while (node.visits < visits) {
            node.addTraversal()
            node.addScores(mapOf(player to 0.0))
        }
    }
}