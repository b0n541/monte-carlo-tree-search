package net.b0n541.pmcts.game.tictactoe;

import net.b0n541.pmcts.mcts.GameMove;
import net.b0n541.pmcts.mcts.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TicTacToeGameState implements GameState<TicTacToeMove> {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToeGameState.class);

    private final TicTacToeBoard board;

    public TicTacToeGameState(final PlayerSymbol firstPlayer) {

        board = new TicTacToeBoard(firstPlayer);
    }

    private TicTacToeGameState(final TicTacToeBoard oldBoard, final TicTacToeMove nextMove) {
        this(oldBoard.getFirstPlayer());
        oldBoard.getMoves().forEach(move -> board.addMove(move));
        board.addMove(nextMove);
    }

    @Override
    public List<String> getPlayers() {
        return List.of(PlayerSymbol.O.toString(), PlayerSymbol.X.toString());
    }

    @Override
    public List<TicTacToeMove> getPossibleMoves() {
        return board.getPossibleMoves();
    }

    @Override
    public TicTacToeGameState addMove(final TicTacToeMove move) {
        return new TicTacToeGameState(board, move);
    }

    @Override
    public boolean isGameFinished() {
        return board.isFinished();
    }

    @Override
    public Map<String, Double> getGameValues() {
        final double playerOResult;
        switch (board.getGameResult()) {
            case O_WON:
                playerOResult = 1.0;
                break;
            case X_WON:
                playerOResult = 0.0;
                break;
            default:
                playerOResult = 0.5;
        }

        final Map<String, Double> result = new HashMap<>();
        result.put(PlayerSymbol.O.toString(), playerOResult);
        result.put(PlayerSymbol.X.toString(), 1.0 - playerOResult);

        return result;
    }

    @Override
    public GameMove getLastMove() {
        return board.getMoves().get(board.getMoves().size() - 1);
    }

    @Override
    public String getNextPlayer() {
        return board.getNextPlayer().toString();
    }

    @Override
    public String toString() {
        return board.format();
    }
}
