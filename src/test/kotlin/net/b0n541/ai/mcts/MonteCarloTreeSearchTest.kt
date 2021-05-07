package net.b0n541.ai.mcts

import net.b0n541.ai.tree.Tree
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Duration

internal class MonteCarloTreeSearchTest {
    @Test
    fun multipleRuns() {
        val tree = Tree(TestGameState(listOf("A", "B")))

        assertThat(tree.size).isEqualTo(1)

        MonteCarloTreeSearch.run(tree, 1, RandomPlayoutPolicy())
        val rootNode = tree.rootNode

        assertThat(tree.size).isEqualTo(3)
        assertThat(rootNode.visits).isEqualTo(1)
        assertThat(rootNode.children()).hasSize(2)

        MonteCarloTreeSearch.run(tree, 1, RandomPlayoutPolicy())

        assertThat(tree.size).isEqualTo(3)
        assertThat(rootNode.visits).isEqualTo(2)
        assertThat(rootNode.children()).hasSize(2)

        MonteCarloTreeSearch.run(tree, 1, RandomPlayoutPolicy())

        assertThat(tree.size).isEqualTo(5)
        assertThat(rootNode.visits).isEqualTo(3)
        assertThat(rootNode.children()).hasSize(2)
    }

    @Test
    @Disabled
    fun testMeassureTime() {
        // warm up
        runSimulation(1000000)
        meassureTime(Duration.ofSeconds(2))
        meassureTime(Duration.ofSeconds(2))
        meassureTime(Duration.ofSeconds(2))
        meassureTime(Duration.ofSeconds(2))
    }

    companion object {
        private fun meassureTime(duration: Duration) {
            val finishTime = System.nanoTime() + duration.toNanos()
            var rounds: Long = 0
            val tree = Tree(TestGameState(listOf("A", "B")))
            do {
                MonteCarloTreeSearch.run(tree, 1, RandomPlayoutPolicy())
                rounds++
            } while (System.nanoTime() < finishTime)
            println("$rounds simulations done in $duration")
            println("Tree size is " + tree.size + " nodes.")
            println("Root node was visited " + tree.rootNode.visits + " times.")
        }

        private fun runSimulation(noOfRounds: Int) {
            val tree = Tree(TestGameState(listOf("A", "B")))
            MonteCarloTreeSearch.run(tree, noOfRounds, RandomPlayoutPolicy())
        }
    }
}