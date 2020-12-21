package org.b0n541.pmcts.mcts;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Tree {

    private static final Logger LOG = LoggerFactory.getLogger(Tree.class);

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
                        " [label=\"" + node.getGameState().getPlayerString() + "\\n" +
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

    public GameMove getBestMove(final int playerIndex) {

        long highestVisits = 0;
        GameMove bestMove = null;
        for (final TreeNode child : rootNode.getChildren()) {
            final GameMove move = child.getGameState().getLastMove();
            final long visits = child.getVisits();
            final double score0 = child.getTotalScores()[0] / visits;
            final double score1 = child.getTotalScores()[1] / visits;
            LOG.info("Move {} got visits {} and scores {} {}", move, visits, score0, score1);

            if (child.getVisits() > highestVisits) {
                highestVisits = visits;
                bestMove = move;
            }
        }

        LOG.info("Best move {} with highest visits {}", bestMove, highestVisits);

        return bestMove;

//        final double highestScore = -1 * Double.MAX_VALUE;
//        long highestVisits = 0;
//        GameMove bestMove = null;
//        for (final Map.Entry<GameMove, TreeNode> entry : children.entrySet()) {
//            final TreeNode child = entry.getValue();
//            final double childScore = child.totalScores[playerIndex] / child.visits;
//            LOG.info("Move {} got visits {} and score {}", entry.getKey(), child.visits, childScore);
////            if (childScore > highestScore) {
////                highestScore = childScore;
////                bestMove = entry.getKey();
////            }
//            if (child.visits > highestVisits) {
//                highestVisits = child.visits;
//                bestMove = entry.getKey();
//            }
//        }
//
////        LOG.info("Best move {} with highest score {}", bestMove, highestScore);
//        LOG.info("Best move {} with highest visits {}", bestMove, highestVisits);
//
//        return bestMove;
    }
}
