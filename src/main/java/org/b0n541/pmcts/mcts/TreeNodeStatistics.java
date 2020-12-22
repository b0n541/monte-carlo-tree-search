package org.b0n541.pmcts.mcts;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNodeStatistics {
    private final Map<String, Double> totalScores = new HashMap<>();
    private long visits;

    public TreeNodeStatistics(final List<String> players) {
        if (players.size() == 0) {
            throw new IllegalArgumentException("At least one player needs to be defined.");
        }
        for (final String player : players) {
            totalScores.put(player, 0.0);
        }
    }

    public void addScores(final Map<String, Double> scores) {
        for (final String player : scores.keySet()) {
            checkPlayer(player);
        }

        visits++;
        scores.forEach((key, value) -> totalScores.put(key, totalScores.get(key) + value));
    }

    public long getVisits() {
        return visits;
    }

    public double getTotalScore(final String player) {
        checkPlayer(player);

        return totalScores.get(player);
    }

    private void checkPlayer(final String player) {
        if (!totalScores.containsKey(player)) {
            throw new IllegalArgumentException("Unknown player " + player);
        }
    }

    public Map<String, Double> getTotalScores() {
        return Collections.unmodifiableMap(totalScores);
    }
}
