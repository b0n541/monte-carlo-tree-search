package org.b0n541.pmcts.game.tictactoe;

import org.b0n541.pmcts.mcts.GameMove;
import org.b0n541.pmcts.mcts.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TicTacToeGameState implements GameState<TicTacToeMove> {

    private static final Logger LOG = LoggerFactory.getLogger(TicTacToeGameState.class);

    private final PlayerSymbol gameStatePlayer;
    private final PlayerSymbol opponentPlayer;
    private final PlayerSymbol firstPlayer;
    private final TicTacToeBoard board;

    public TicTacToeGameState(final PlayerSymbol gameStatePlayer, final PlayerSymbol opponentPlayer, final PlayerSymbol firstPlayer) {

        this.gameStatePlayer = gameStatePlayer;
        this.opponentPlayer = opponentPlayer;
        this.firstPlayer = firstPlayer;
        board = new TicTacToeBoard(firstPlayer);
    }

    private TicTacToeGameState(final PlayerSymbol gameStatePlayer, final PlayerSymbol opponentPlayer, final PlayerSymbol firstPlayer, final TicTacToeBoard oldBoard, final TicTacToeMove nextMove) {
        this(gameStatePlayer, opponentPlayer, firstPlayer);
        oldBoard.getMoves().forEach(move -> board.addMove(move));
        board.addMove(nextMove);
    }

    public PlayerSymbol getNextPlayer() {
        return board.getNextPlayer();
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
        return new TicTacToeGameState(gameStatePlayer, opponentPlayer, firstPlayer, board, move);
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
    public String getPlayer() {
        return getNextPlayer().toString();
    }

    @Override
    public String toString() {
        return board.format();
    }
}
