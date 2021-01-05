package net.b0n541.pmcts.mcts;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MonteCarloTreeSearchTest {
    @Test
    public void multipleRuns() {
        final Tree tree = new Tree(new TestGameState(List.of("HEADS", "TAILS")));

        assertThat(tree.getSize()).isEqualTo(1);

        MonteCarloTreeSearch.run(tree, 1);

        final TreeNode rootNode = tree.getRootNode();

        assertThat(tree.getSize()).isEqualTo(3);
        assertThat(rootNode.getVisits()).isEqualTo(1);
        assertThat(rootNode.children()).hasSize(2);

        MonteCarloTreeSearch.run(tree, 1);

        assertThat(tree.getSize()).isEqualTo(3);
        assertThat(rootNode.getVisits()).isEqualTo(2);
        assertThat(rootNode.children()).hasSize(2);

        MonteCarloTreeSearch.run(tree, 1);

        assertThat(tree.getSize()).isEqualTo(5);
        assertThat(rootNode.getVisits()).isEqualTo(3);
        assertThat(rootNode.children()).hasSize(2);
    }

    @Test
    @Disabled
    public void testMeassureTime() {
        // warm up
        runSimulation(1_000_000);

        meassureTime(Duration.ofSeconds(2));
        meassureTime(Duration.ofSeconds(2));
        meassureTime(Duration.ofSeconds(2));
        meassureTime(Duration.ofSeconds(2));
    }

    private static void meassureTime(final Duration duration) {

        final long finishTime = System.nanoTime() + duration.toNanos();

        long rounds = 0;
        final Tree tree = new Tree(new TestGameState(List.of("HEADS", "TAILS")));
        do {
            MonteCarloTreeSearch.run(tree, 1);
            rounds++;
        } while (System.nanoTime() < finishTime);

        System.out.println(rounds + " simulations done in " + duration);
        System.out.println("Tree size is " + tree.getSize() + " nodes.");
        System.out.println("Root node was visited " + tree.getRootNode().getVisits() + " times.");
    }

    private static void runSimulation(final int noOfRounds) {
        final Tree tree = new Tree(new TestGameState(List.of("HEADS", "TAILS")));
        MonteCarloTreeSearch.run(tree, noOfRounds);
    }
}
