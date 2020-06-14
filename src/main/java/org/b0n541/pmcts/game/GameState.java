package org.b0n541.pmcts.game;

import org.b0n541.pmcts.game.tictactoe.GameResult;
import org.b0n541.pmcts.game.tictactoe.PlayerSymbol;

import java.util.List;

public interface GameState<M> {

    List<M> getPossibleMoves(PlayerSymbol playerSymbol);

    void addMove(M move);

    boolean isFinished();

    GameResult getGameResult();
}
