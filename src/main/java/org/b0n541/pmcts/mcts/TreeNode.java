package org.b0n541.pmcts.mcts;


import java.util.*;

public final class TreeNode {

    private final static double EXPLORATION_FACTOR = 2.0;

    private final GameState gameState;

    private final TreeNode parent;
    private final Map<GameMove, TreeNode> children = new HashMap<>();

    private double totalScore;
    private long visits;

    public TreeNode(final GameState gameState) {
        this(null, gameState);
    }

    public TreeNode(final TreeNode parent, final GameState gameState) {
        this.parent = parent;
        this.gameState = gameState;
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

    public double getTotalScore() {
        return totalScore;
    }

    public long getVisits() {
        return visits;
    }

    public void addVisit(final double gameStateValue) {
        totalScore += gameStateValue;
        visits++;
    }

    public List<TreeNode> getChildren() {
        return Collections.unmodifiableList(new ArrayList<>(children.values()));
    }

    public double getUcb1Value() {
        if (isRootNode() || visits == 0) {
            return Double.MAX_VALUE;
        } else {
            return (totalScore / visits) + EXPLORATION_FACTOR * Math.sqrt((Math.log(parent.visits) / visits));
        }

    }

    public void expandPossibleMoves() {

        final List<GameMove> possibleMoves = gameState.getPossibleMoves();

        possibleMoves.forEach(move -> children.put(move, new TreeNode(this, gameState.addMove(move))));
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameMove getBestMove() {
        double highestScore = Double.MIN_VALUE;
        GameMove bestMove = null;
        for (final Map.Entry<GameMove, TreeNode> entry : children.entrySet()) {
            final TreeNode child = entry.getValue();
            final double childScore = child.totalScore / child.visits;
            if (childScore > highestScore) {
                highestScore = childScore;
                bestMove = entry.getKey();
            }
        }
        return bestMove;
    }
}
