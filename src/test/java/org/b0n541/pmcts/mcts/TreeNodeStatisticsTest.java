package org.b0n541.pmcts.mcts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TreeNodeStatisticsTest {
    @Test
    void emptyStatistics() {
        final TreeNodeStatistics statistics = new TreeNodeStatistics(List.of("A", "B"));

        assertThat(statistics.getVisits()).isZero();
        assertThat(statistics.getTotalScore("A")).isZero();
        assertThat(statistics.getTotalScore("B")).isZero();
    }

    @Test
    void addScores() {
        final TreeNodeStatistics statistics = new TreeNodeStatistics(List.of("A", "B"));

        statistics.addScores(Map.of("A", 10.0, "B", 0.0));

        assertThat(statistics.getVisits()).isEqualTo(1);
        assertThat(statistics.getTotalScore("A")).isEqualTo(10.0);
        assertThat(statistics.getTotalScore("B")).isZero();

        statistics.addScores(Map.of("A", 0.0, "B", 20.0));

        assertThat(statistics.getVisits()).isEqualTo(2);
        assertThat(statistics.getTotalScore("A")).isEqualTo(10.0);
        assertThat(statistics.getTotalScore("B")).isEqualTo(20.0);
    }

    @Test
    void noPlayer() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TreeNodeStatistics(Collections.emptyList()));
    }

    @Test
    void wrongPlayer() {
        final TreeNodeStatistics statistics = new TreeNodeStatistics(List.of("A", "B"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> statistics.getTotalScore("C"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> statistics.addScores(Map.of("C", 123.0)));
    }
}
