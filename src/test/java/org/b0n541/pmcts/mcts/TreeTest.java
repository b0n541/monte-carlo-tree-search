package org.b0n541.pmcts.mcts;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TreeTest {
    @Test
    public void emptyTree() {
        final Tree tree = new Tree(new TestGameState(List.of("HEADS", "TAILS")));

        assertThat(tree.getSize()).isEqualTo(1);

        tree.getRootNode().expandPossibleMoves();

        assertThat(tree.getSize()).isEqualTo(3);

        tree.getRootNode().getChildren().get(0).expandPossibleMoves();

        assertThat(tree.getSize()).isEqualTo(5);

        tree.getRootNode().getChildren().get(1).expandPossibleMoves();

        assertThat(tree.getSize()).isEqualTo(7);
    }
}
