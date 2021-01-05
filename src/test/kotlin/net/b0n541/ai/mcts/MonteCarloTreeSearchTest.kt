package net.b0n541.ai.mcts

import net.b0n541.ai.mcts.MonteCarloTreeSearch.run
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Duration

internal class MonteCarloTreeSearchTest {
    @Test
    fun multipleRuns() {
        val tree = Tree(TestGameState(listOf("HEADS", "TAILS")))
        Assertions.assertThat(tree.size).isEqualTo(1)
        run(tree, 1)
        val rootNode = tree.rootNode
        Assertions.assertThat(tree.size).isEqualTo(3)
        Assertions.assertThat(rootNode.visits).isEqualTo(1)
        Assertions.assertThat(rootNode.children()).hasSize(2)
        run(tree, 1)
        Assertions.assertThat(tree.size).isEqualTo(3)
        Assertions.assertThat(rootNode.visits).isEqualTo(2)
        Assertions.assertThat(rootNode.children()).hasSize(2)
        run(tree, 1)
        Assertions.assertThat(tree.size).isEqualTo(5)
        Assertions.assertThat(rootNode.visits).isEqualTo(3)
        Assertions.assertThat(rootNode.children()).hasSize(2)
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
            val tree = Tree(TestGameState(listOf("HEADS", "TAILS")))
            do {
                run(tree, 1)
                rounds++
            } while (System.nanoTime() < finishTime)
            println("$rounds simulations done in $duration")
            println("Tree size is " + tree.size + " nodes.")
            println("Root node was visited " + tree.rootNode.visits + " times.")
        }

        private fun runSimulation(noOfRounds: Int) {
            val tree = Tree(TestGameState(listOf("HEADS", "TAILS")))
            run(tree, noOfRounds)
        }
    }
}