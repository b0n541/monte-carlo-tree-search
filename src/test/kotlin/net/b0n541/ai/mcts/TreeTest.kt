package net.b0n541.ai.mcts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class TreeTest {
    @Test
    fun emptyTree() {
        val tree = Tree(TestGameState(listOf("HEADS", "TAILS")))
        Assertions.assertThat(tree.size).isEqualTo(1)
        tree.rootNode.expandPossibleMoves()
        Assertions.assertThat(tree.size).isEqualTo(3)
        tree.rootNode.children()[0].expandPossibleMoves()
        Assertions.assertThat(tree.size).isEqualTo(5)
        tree.rootNode.children()[1].expandPossibleMoves()
        Assertions.assertThat(tree.size).isEqualTo(7)
    }
}