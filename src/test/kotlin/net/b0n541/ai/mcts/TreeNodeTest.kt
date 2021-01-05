package net.b0n541.ai.mcts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class TreeNodeTest {
    @Test
    fun rootNodeSinglePlayerGame() {
        val rootNode = TreeNode(null, TestGameState(listOf("A")))
        Assertions.assertThat(rootNode.isRootNode).isTrue
        Assertions.assertThat(rootNode.isLeafNode).isTrue
        Assertions.assertThat(rootNode.children()).hasSize(0)
        Assertions.assertThat(rootNode.visits).isEqualTo(0)
        rootNode.expandPossibleMoves()
        Assertions.assertThat(rootNode.isRootNode).isTrue
        Assertions.assertThat(rootNode.isLeafNode).isFalse
        Assertions.assertThat(rootNode.children()).hasSize(2)
        Assertions.assertThat(rootNode.visits).isEqualTo(0)
        rootNode.addVisit(mapOf("A" to 10.0))
        Assertions.assertThat(rootNode.visits).isEqualTo(1)
        Assertions.assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
    }

    @Test
    fun visitNodesSinglePlayerGame() {
        val rootNode = TreeNode(null, TestGameState(listOf("A")))
        rootNode.expandPossibleMoves()
        val leftChildNode = rootNode.children()[0]
        Assertions.assertThat(leftChildNode.isRootNode).isFalse
        Assertions.assertThat(leftChildNode.isLeafNode).isTrue
        Assertions.assertThat(leftChildNode.parent).isEqualTo(rootNode)
        Assertions.assertThat(leftChildNode.children()).hasSize(0)
        Assertions.assertThat(leftChildNode.visits).isEqualTo(0)
        Assertions.assertThat(leftChildNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        leftChildNode.addVisit(mapOf("A" to 20.0))
        rootNode.addVisit(mapOf("A" to 20.0))
        Assertions.assertThat(rootNode.totalScores).isEqualTo(mapOf("A" to 20.0))
        Assertions.assertThat(rootNode.visits).isEqualTo(1)
        Assertions.assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        Assertions.assertThat(leftChildNode.totalScores).isEqualTo(mapOf("A" to 20.0))
        Assertions.assertThat(leftChildNode.visits).isEqualTo(1)
        Assertions.assertThat(leftChildNode.ucb1Value).isEqualTo(20.0)
        val rightChildNode = rootNode.children()[1]
        Assertions.assertThat(rightChildNode.totalScores).isEqualTo(mapOf("A" to 0.0))
        Assertions.assertThat(rightChildNode.visits).isEqualTo(0)
        Assertions.assertThat(rightChildNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        rightChildNode.addVisit(mapOf("A" to 10.0))
        rootNode.addVisit(mapOf("A" to 10.0))
        Assertions.assertThat(rootNode.totalScores).isEqualTo(mapOf("A" to 30.0))
        Assertions.assertThat(rootNode.visits).isEqualTo(2)
        Assertions.assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        Assertions.assertThat(leftChildNode.totalScores).isEqualTo(mapOf("A" to 20.0))
        Assertions.assertThat(leftChildNode.visits).isEqualTo(1)
        Assertions.assertThat(leftChildNode.ucb1Value).isCloseTo(21.67, Assertions.within(0.01))
        Assertions.assertThat(rightChildNode.totalScores).isEqualTo(mapOf("A" to 10.0))
        Assertions.assertThat(rightChildNode.visits).isEqualTo(1)
        Assertions.assertThat(rightChildNode.ucb1Value).isCloseTo(11.67, Assertions.within(0.01))
        leftChildNode.expandPossibleMoves()
        val leftGrandChildNode = leftChildNode.children()[0]
        leftGrandChildNode.addVisit(mapOf("A" to 15.0))
        leftChildNode.addVisit(mapOf("A" to 15.0))
        rootNode.addVisit(mapOf("A" to 15.0))
        Assertions.assertThat(rootNode.totalScores).isEqualTo(mapOf("A" to 45.0))
        Assertions.assertThat(rootNode.visits).isEqualTo(3)
        Assertions.assertThat(rootNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
        Assertions.assertThat(leftChildNode.totalScores).isEqualTo(mapOf("A" to 35.0))
        Assertions.assertThat(leftChildNode.visits).isEqualTo(2)
        Assertions.assertThat(leftChildNode.ucb1Value).isCloseTo(18.98, Assertions.within(0.01))
        Assertions.assertThat(rightChildNode.totalScores).isEqualTo(mapOf("A" to 10.0))
        Assertions.assertThat(rightChildNode.visits).isEqualTo(1)
        Assertions.assertThat(rightChildNode.ucb1Value).isCloseTo(12.10, Assertions.within(0.01))
        Assertions.assertThat(leftGrandChildNode.totalScores).isEqualTo(mapOf("A" to 15.0))
        Assertions.assertThat(leftGrandChildNode.visits).isEqualTo(1)
        Assertions.assertThat(leftGrandChildNode.ucb1Value).isCloseTo(16.67, Assertions.within(0.01))
        val rightGrandChildNode = leftChildNode.children()[1]
        Assertions.assertThat(rightGrandChildNode.totalScores).isEqualTo(mapOf("A" to 0.0))
        Assertions.assertThat(rightGrandChildNode.visits).isEqualTo(0)
        Assertions.assertThat(rightGrandChildNode.ucb1Value).isEqualTo(Double.MAX_VALUE)
    }
}