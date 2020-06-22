package org.b0n541.pmcts.mcts;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class MonteCarloTreeSearch {

    public static void run(final Tree tree, final int rounds) {
        for (int i = 0; i < rounds; i++) {
            final TreeNode leafNode = traverseTree(tree);
            final TreeNode rolloutNode = expandTree(leafNode);
            final double result = rollout(rolloutNode);
            backPropagation(rolloutNode, result);
        }
    }

    private static TreeNode traverseTree(final Tree tree) {
        TreeNode currentNode = tree.getRootNode();
        while (!currentNode.isLeafNode()) {
            double bestUcb1Value = Double.MIN_VALUE;
            TreeNode bestChildNode = null;
            for (final TreeNode node : currentNode.getChildren()) {
                if (node.getUcb1Value() > bestUcb1Value) {
                    bestUcb1Value = node.getUcb1Value();
                    bestChildNode = node;
                }
            }
            currentNode = bestChildNode;
        }
        return currentNode;
    }

    private static TreeNode expandTree(final TreeNode node) {
        if (node.getVisits() == 0) {
            return node;
        }
        node.expandPossibleMoves();
        return node.getChildren().get(0);
    }

    private static double rollout(final TreeNode node) {
        GameState currentState = node.getGameState();
        while (!currentState.isGameFinished()) {
            final List<GameMove> possibleMoves = currentState.getPossibleMoves();
            final GameMove nextMove = possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
            currentState = currentState.addMove(nextMove);
        }
        return currentState.getGameResult();
    }

    private static void backPropagation(final TreeNode node, final double gameValue) {
        TreeNode currentNode = node;
        do {
            currentNode.addVisit(gameValue);
            currentNode = currentNode.getParent();
        } while (!currentNode.isRootNode());
        currentNode.addVisit(gameValue);
    }
}
