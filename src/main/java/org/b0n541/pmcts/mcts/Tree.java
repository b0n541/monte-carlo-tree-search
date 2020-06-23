package org.b0n541.pmcts.mcts;


public final class Tree {

    private final TreeNode rootNode;

    public Tree(final GameState gameState) {
        rootNode = new TreeNode(gameState);
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public long getSize() {
        return getSize(rootNode);
    }

    private static long getSize(final TreeNode node) {
        return 1 + node.getChildren().stream()
                .map(child -> getSize(child))
                .reduce(0L, Long::sum);
    }
}
