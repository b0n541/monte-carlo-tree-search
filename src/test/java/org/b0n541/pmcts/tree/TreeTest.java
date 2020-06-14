package org.b0n541.pmcts.tree;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TreeTest {
    @Test
    public void emptyTree() {
        Tree<Integer> tree = new Tree(new RootNode());

        assertThat(tree.getRootNode()).isNotNull();
    }
}
