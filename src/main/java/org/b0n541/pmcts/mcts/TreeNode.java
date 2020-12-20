package org.b0n541.pmcts.mcts;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class TreeNode {

    private static final Logger LOG = LoggerFactory.getLogger(TreeNode.class);

    private final static double EXPLORATION_FACTOR = 2.0;

    private static final AtomicLong nodeCounter = new AtomicLong();

    private final long nodeId = nodeCounter.incrementAndGet();

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

    public String getNodeId() {
        return Long.toString(nodeId);
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
        return List.copyOf(children.values());
    }

    public Map<GameMove, TreeNode> getMovesAndChildren() {
        return Collections.unmodifiableMap(children);
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
        final double highestScore = -1 * Double.MAX_VALUE;
        long highestVisits = 0;
        GameMove bestMove = null;
        for (final Map.Entry<GameMove, TreeNode> entry : children.entrySet()) {
            final TreeNode child = entry.getValue();
            final double childScore = child.totalScores[playerIndex] / child.visits;
            LOG.info("Move {} got visits {} and score {}", entry.getKey(), child.visits, childScore);
//            if (childScore > highestScore) {
//                highestScore = childScore;
//                bestMove = entry.getKey();
//            }
            if (child.visits > highestVisits) {
                highestVisits = child.visits;
                bestMove = entry.getKey();
            }
        }

//        LOG.info("Best move {} with highest score {}", bestMove, highestScore);
        LOG.info("Best move {} with highest visits {}", bestMove, highestVisits);

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
