package org.b0n541.pmcts.mcts;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class TreeNodeTest {
    @Test
    public void rootNode() {
        final TreeNode rootNode = new TreeNode(new TestGameState(2));

        assertThat(rootNode.isRootNode()).isTrue();
        assertThat(rootNode.isLeafNode()).isTrue();
        assertThat(rootNode.getChildren()).hasSize(0);
        assertThat(rootNode.getVisits()).isEqualTo(0);

        rootNode.expandPossibleMoves();

        assertThat(rootNode.isRootNode()).isTrue();
        assertThat(rootNode.isLeafNode()).isFalse();
        assertThat(rootNode.getChildren()).hasSize(2);
        assertThat(rootNode.getVisits()).isEqualTo(0);

        rootNode.addVisit(10.0);

        assertThat(rootNode.getVisits()).isEqualTo(1);
        assertThat(rootNode.getUcb1Value()).isEqualTo(Double.MAX_VALUE);
    }

    @Test
    public void visitNodes() {
        final TreeNode rootNode = new TreeNode(new TestGameState(2));

        rootNode.expandPossibleMoves();

        final TreeNode leftChildNode = rootNode.getChildren().get(0);

        assertThat(leftChildNode.isRootNode()).isFalse();
        assertThat(leftChildNode.isLeafNode()).isTrue();
        assertThat(leftChildNode.getParent()).isEqualTo(rootNode);
        assertThat(leftChildNode.getChildren()).hasSize(0);
        assertThat(leftChildNode.getVisits()).isEqualTo(0);
        assertThat(leftChildNode.getUcb1Value()).isEqualTo(Double.MAX_VALUE);

        leftChildNode.addVisit(20.0);
        rootNode.addVisit(20.0);

        assertThat(rootNode.getTotalScores()).isEqualTo(new double[]{20.0, 0.0});
        assertThat(rootNode.getVisits()).isEqualTo(1);
        assertThat(rootNode.getUcb1Value()).isEqualTo(Double.MAX_VALUE);

        assertThat(leftChildNode.getTotalScores()).isEqualTo(new double[]{20.0, 0.0});
        assertThat(leftChildNode.getVisits()).isEqualTo(1);
        assertThat(leftChildNode.getUcb1Value()).isEqualTo(20.0);

        final TreeNode rightChildNode = rootNode.getChildren().get(1);

        assertThat(rightChildNode.getTotalScores()).isEqualTo(new double[]{0.0, 0.0});
        assertThat(rightChildNode.getVisits()).isEqualTo(0);
        assertThat(rightChildNode.getUcb1Value()).isEqualTo(Double.MAX_VALUE);

        rightChildNode.addVisit(10.0);
        rootNode.addVisit(10.0);

        assertThat(rootNode.getTotalScores()).isEqualTo(new double[]{30.0, 0.0});
        assertThat(rootNode.getVisits()).isEqualTo(2);
        assertThat(rootNode.getUcb1Value()).isEqualTo(Double.MAX_VALUE);

        assertThat(leftChildNode.getTotalScores()).isEqualTo(new double[]{20.0, 0.0});
        assertThat(leftChildNode.getVisits()).isEqualTo(1);
        assertThat(leftChildNode.getUcb1Value()).isCloseTo(21.67, within(0.01));

        assertThat(rightChildNode.getTotalScores()).isEqualTo(new double[]{10.0, 0.0});
        assertThat(rightChildNode.getVisits()).isEqualTo(1);
        assertThat(rightChildNode.getUcb1Value()).isCloseTo(11.67, within(0.01));

        leftChildNode.expandPossibleMoves();

        final TreeNode leftGrandChildNode = leftChildNode.getChildren().get(0);

        leftGrandChildNode.addVisit(15.0);
        leftChildNode.addVisit(15.0);
        rootNode.addVisit(15.0);

        assertThat(rootNode.getTotalScores()).isEqualTo(new double[]{45.0, 0.0});
        assertThat(rootNode.getVisits()).isEqualTo(3);
        assertThat(rootNode.getUcb1Value()).isEqualTo(Double.MAX_VALUE);

        assertThat(leftChildNode.getTotalScores()).isEqualTo(new double[]{35.0, 0.0});
        assertThat(leftChildNode.getVisits()).isEqualTo(2);
        assertThat(leftChildNode.getUcb1Value()).isCloseTo(18.98, within(0.01));

        assertThat(rightChildNode.getTotalScores()).isEqualTo(new double[]{10.0, 0.0});
        assertThat(rightChildNode.getVisits()).isEqualTo(1);
        assertThat(rightChildNode.getUcb1Value()).isCloseTo(12.10, within(0.01));

        assertThat(leftGrandChildNode.getTotalScores()).isEqualTo(new double[]{15.0, 0.0});
        assertThat(leftGrandChildNode.getVisits()).isEqualTo(1);
        assertThat(leftGrandChildNode.getUcb1Value()).isCloseTo(16.67, within(0.01));

        final TreeNode rightGrandChildNode = leftChildNode.getChildren().get(1);

        assertThat(rightGrandChildNode.getTotalScores()).isEqualTo(new double[]{0.0, 0.0});
        assertThat(rightGrandChildNode.getVisits()).isEqualTo(0);
        assertThat(rightGrandChildNode.getUcb1Value()).isEqualTo(Double.MAX_VALUE);
    }
}
