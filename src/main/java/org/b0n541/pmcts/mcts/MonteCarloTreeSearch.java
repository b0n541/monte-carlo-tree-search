package org.b0n541.pmcts.mcts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class MonteCarloTreeSearch {

    private static final Logger LOG = LoggerFactory.getLogger(MonteCarloTreeSearch.class);

    public static void run(final Tree tree, final int rounds) {
        for (int i = 0; i < rounds; i++) {
            final TreeNode leafNode = traverseTree(tree);
            final TreeNode rollOutNode = expandTree(leafNode);
            final double result = rollOut(rollOutNode);
            backPropagation(rollOutNode, result);
        }
    }

    private static TreeNode traverseTree(final Tree tree) {
        TreeNode nextNode = tree.getRootNode();
        while (!nextNode.isLeafNode()) {
            TreeNode bestChildNode = null;
            double bestUcb1Value = -1 * Double.MAX_VALUE;
            for (final TreeNode node : nextNode.getChildren()) {
                if (node.getUcb1Value() > bestUcb1Value) {
                    bestChildNode = node;
                    bestUcb1Value = node.getUcb1Value();
                }
            }
            nextNode = bestChildNode;
        }
        return nextNode;
    }

    private static TreeNode expandTree(final TreeNode node) {
        if (node.isRootNode() && node.isLeafNode()) {
            return expandPossibleMoves(node);
        }

        if (node.getVisits() == 0 || node.getGameState().isGameFinished()) {
            return node;
        }

        return expandPossibleMoves(node);
    }

    private static TreeNode expandPossibleMoves(final TreeNode node) {
        node.expandPossibleMoves();
        return node.getChildren().get(0);
    }

    private static double rollOut(final TreeNode node) {
        GameState currentState = node.getGameState();
        while (!currentState.isGameFinished()) {
            final List<GameMove> possibleMoves = currentState.getPossibleMoves();
            final int randomIndex = ThreadLocalRandom.current().nextInt(possibleMoves.size());
            final GameMove nextMove = possibleMoves.get(randomIndex);
            currentState = currentState.addMove(nextMove);
        }
        return currentState.getGameResult();
    }

    private static void backPropagation(final TreeNode node, final double gameValue) {
        TreeNode currentNode = node;
        do {
            if (currentNode.getGameState().isMax()) {
                currentNode.addVisit(gameValue);
            } else {
                currentNode.addVisit(-1 * gameValue);
            }
            currentNode = currentNode.getParent();
        } while (!currentNode.isRootNode());
        currentNode.addVisit(gameValue);
    }
}
