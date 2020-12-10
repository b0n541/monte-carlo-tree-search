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

    public void printDigraph() {
        System.out.println("======================================");
        printTreeLevel(rootNode);
        System.out.println("======================================");
    }

    private static void printTreeLevel(final TreeNode node) {
        printNodeLabel(node);
        printNodeMoves(node);

        if (!node.isLeafNode()) {
            node.getChildren().stream().filter(child -> child.getVisits() > 0).forEach(Tree::printTreeLevel);
        }
    }

    private static void printNodeLabel(final TreeNode node) {
        System.out.println(
                node.getNodeId() +
                        " [label=\"" + node.getGameState().getPlayerIndex() + "\\n" +
                        " v=" + node.getVisits() +
                        " s=" + node.getTotalScores()[0] + " " + node.getTotalScores()[1] + "\"" +
                        "]");
    }

    private static void printNodeMoves(final TreeNode parent) {
        parent.getMovesAndChildren().entrySet().stream()
                .filter(entry -> entry.getValue().getVisits() > 0)
                .forEach(entry -> System.out.println(
                        parent.getNodeId() +
                                " -> " + entry.getValue().getNodeId() +
                                " [label=\"" + entry.getKey().toShortString() + "\"]"));
    }
}
