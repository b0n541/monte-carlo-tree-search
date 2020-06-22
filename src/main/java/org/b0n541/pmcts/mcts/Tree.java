package org.b0n541.pmcts.mcts;


public final class Tree {

    private final TreeNode rootNode;

    public Tree(final GameState gameState) {
        rootNode = new TreeNode(gameState);
        rootNode.expandPossibleMoves();
    }

    public TreeNode getRootNode() {
        return rootNode;
    }
}
