package org.b0n541.pmcts.mcts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGameState implements GameState {

    private final List<GameMove> moves = new ArrayList<>();
    private final int playerCount;

    public TestGameState(final int playerCount) {
        this.playerCount = playerCount;
    }

    private TestGameState(final int playerCount, final List<GameMove> moves, final GameMove move) {
        this.playerCount = playerCount;
        this.moves.addAll(moves);
        this.moves.add(move);
    }

    @Override
    public List<GameMove> getPossibleMoves() {
        return Arrays.asList(TestGameMove.HEADS, TestGameMove.TAILS);
    }

    @Override
    public GameState addMove(final GameMove move) {
        return new TestGameState(playerCount, moves, move);
    }

    @Override
    public boolean isGameFinished() {
        return moves.size() == 100;
    }

    @Override
    public double getGameResult() {
        return moves.stream()
                .filter(move -> move == TestGameMove.TAILS)
                .count();
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public int getRootPlayerIndex() {
        return 0;
    }

    @Override
    public int getPlayerIndex() {
        return 0;
    }
}
