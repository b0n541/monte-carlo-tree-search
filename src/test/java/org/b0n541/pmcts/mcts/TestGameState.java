package org.b0n541.pmcts.mcts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGameState implements GameState {

    private final List<GameMove> moves = new ArrayList<>();

    public TestGameState() {
    }

    private TestGameState(final List<GameMove> moves, final GameMove move) {
        this.moves.addAll(moves);
        this.moves.add(move);
    }

    @Override
    public List<GameMove> getPossibleMoves() {
        return Arrays.asList(TestGameMove.HEADS, TestGameMove.TAILS);
    }

    @Override
    public GameState addMove(final GameMove move) {
        return new TestGameState(moves, move);
    }

    @Override
    public boolean isGameFinished() {
        return moves.size() == 3;
    }

    @Override
    public double getGameResult() {
        return moves.stream()
                .filter(move -> move == TestGameMove.TAILS)
                .count();
    }
}
