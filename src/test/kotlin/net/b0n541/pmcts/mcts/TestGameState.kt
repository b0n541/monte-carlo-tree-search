package net.b0n541.pmcts.mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestGameState implements GameState<GameMove> {

    private final List<GameMove> moves = new ArrayList<>();
    private final List<String> players;

    public TestGameState(final List<String> players) {
        this.players = Collections.unmodifiableList(players);
    }

    private TestGameState(final List<String> players, final List<GameMove> moves, final GameMove move) {
        this(players);
        this.moves.addAll(moves);
        this.moves.add(move);
    }

    @Override
    public List<String> getPlayers() {
        return players;
    }

    @Override
    public List<GameMove> getPossibleMoves() {
        return List.of(TestGameMove.HEADS, TestGameMove.TAILS);
    }

    @Override
    public GameState addMove(final GameMove move) {
        return new TestGameState(players, moves, move);
    }

    @Override
    public boolean isGameFinished() {
        return moves.size() == 100;
    }

    @Override
    public Map<String, Double> getGameValues() {
        final double headsGameValue = 1.0 *
                moves.stream()
                        .filter(move -> move == TestGameMove.HEADS)
                        .count();
        return Map.of(TestGameMove.HEADS.toString(), headsGameValue, TestGameMove.TAILS.toString(), 100 - headsGameValue);
    }

    @Override
    public GameMove getLastMove() {
        return moves.get(moves.size() - 1);
    }

    @Override
    public String getNextPlayer() {
        return players.get(0);
    }
}
