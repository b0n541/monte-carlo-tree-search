package org.b0n541.pmcts.mcts;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MonteCarloTreeSearchTest {
    @Test
    public void multipleRuns() {
        final Tree tree = new Tree(new TestGameState());

        MonteCarloTreeSearch.run(tree, 1);

        final TreeNode rootNode = tree.getRootNode();

        assertThat(rootNode.getVisits()).isEqualTo(1);
        assertThat(rootNode.getChildren()).hasSize(2);

        MonteCarloTreeSearch.run(tree, 1);

        assertThat(rootNode.getVisits()).isEqualTo(2);
        assertThat(rootNode.getChildren()).hasSize(2);

        MonteCarloTreeSearch.run(tree, 1);

        assertThat(rootNode.getVisits()).isEqualTo(3);
        assertThat(rootNode.getChildren()).hasSize(2);
    }
}
