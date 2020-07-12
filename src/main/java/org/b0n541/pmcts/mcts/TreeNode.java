package org.b0n541.pmcts.mcts;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class TreeNode {

    private static final Logger LOG = LoggerFactory.getLogger(TreeNode.class);

    private final static double EXPLORATION_FACTOR = 2.0;

    private final GameState gameState;

    private final TreeNode parent;
    private final Map<GameMove, TreeNode> children = new HashMap<>();

    private final double[] totalScores;
    private long visits;

    public TreeNode(final GameState gameState) {
        this(null, gameState);
    }

    public TreeNode(final TreeNode parent, final GameState gameState) {
        this.parent = parent;
        this.gameState = gameState;
        totalScores = new double[gameState.getPlayerCount()];
    }

    public boolean isRootNode() {
        return parent == null;
    }

    public boolean isLeafNode() {
        return children.isEmpty();
    }

    public TreeNode getParent() {
        return parent;
    }

    public double[] getTotalScores() {
        return totalScores;
    }

    public long getVisits() {
        return visits;
    }

    public void addVisit(final double... gameValues) {
        int index = 0;
        for (final double gameValue : gameValues) {
            totalScores[index] += gameValue;
            index++;
        }
        visits++;
    }

    public List<TreeNode> getChildren() {
        return Collections.unmodifiableList(new ArrayList<>(children.values()));
    }

    public double getUcb1Value(final int playerIndex) {
        if (isRootNode() || visits == 0) {
            return Double.MAX_VALUE;
        } else {
            return (totalScores[playerIndex] / visits) + EXPLORATION_FACTOR * Math.sqrt((Math.log(parent.visits) / visits));
        }

    }

    public void expandPossibleMoves() {

        final List<GameMove> possibleMoves = gameState.getPossibleMoves();

        possibleMoves.forEach(move -> children.put(move, new TreeNode(this, gameState.addMove(move))));
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameMove getBestMove(final int playerIndex) {
        double highestScore = -1 * Double.MAX_VALUE;
        GameMove bestMove = null;
        for (final Map.Entry<GameMove, TreeNode> entry : children.entrySet()) {
            final TreeNode child = entry.getValue();
            final double childScore = child.totalScores[playerIndex] / child.visits;
            LOG.info("Move {} got visits {} and score {}", entry.getKey(), child.visits, childScore);
            if (childScore > highestScore) {
                highestScore = childScore;
                bestMove = entry.getKey();
            }
        }

        LOG.info("Best move {} with highest score {}", bestMove, highestScore);

        return bestMove;
    }

    @Override
    public String toString() {
        return "Visits: " + visits +
                " Total scores: " + totalScores[0] +
                " Score: " + totalScores[0] / visits +
                " UCB1: " + getUcb1Value(0);
    }
}
