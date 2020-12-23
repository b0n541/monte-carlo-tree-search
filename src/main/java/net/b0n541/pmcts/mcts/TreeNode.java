package net.b0n541.pmcts.mcts;


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

    private static final AtomicLong NODE_COUNTER = new AtomicLong();

    private final long nodeId = NODE_COUNTER.incrementAndGet();

    private final GameState gameState;

    private final TreeNode parent;
    private final Map<GameMove, TreeNode> children = new HashMap<>();

    private final TreeNodeStatistics statistics;

    public TreeNode(final GameState gameState) {
        this(null, gameState);
    }

    public TreeNode(final TreeNode parent, final GameState gameState) {
        this.parent = parent;
        this.gameState = gameState;
        statistics = new TreeNodeStatistics(gameState.getPlayers());
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

    public Map<String, Double> getTotalScores() {
        return statistics.getTotalScores();
    }

    public long getVisits() {
        return statistics.getVisits();
    }

    public void addVisit(final Map<String, Double> scores) {
        statistics.addScores(scores);
    }

    public List<TreeNode> getChildren() {
        return List.copyOf(children.values());
    }

    public String getPlayer() {
        return gameState.getNextPlayer();
    }

    public Map<GameMove, TreeNode> getMovesAndChildren() {
        return Collections.unmodifiableMap(children);
    }

    public double getUcb1Value() {
        if (isRootNode() || statistics.getVisits() == 0) {
            return Double.MAX_VALUE;
        } else {
            return (statistics.getTotalScore(parent.getPlayer()) / getVisits()) + EXPLORATION_FACTOR * Math.sqrt((Math.log(parent.getVisits()) / getVisits()));
        }
    }

    public void expandPossibleMoves() {

        final List<GameMove> possibleMoves = gameState.getPossibleMoves();

        possibleMoves.forEach(move -> children.put(move, new TreeNode(this, gameState.addMove(move))));
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public String toString() {
        return "Visits: " + statistics.getVisits() +
                " Total scores: " + statistics.getTotalScores() +
                " UCB1: " + getUcb1Value();
    }
}
