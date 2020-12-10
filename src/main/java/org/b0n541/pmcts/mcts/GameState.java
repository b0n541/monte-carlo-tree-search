package org.b0n541.pmcts.mcts;

import java.util.List;

/**
 * Interface for all game state implementations to be used in the {@link MonteCarloTreeSearch}.
 */
public interface GameState<M extends GameMove> {
    /**
     * Gets all possible moves from the current game state.
     *
     * @return All possible moves
     */
    List<M> getPossibleMoves();

    /**
     * Adds a move to the game state and returns the resulting game state.
     *
     * @param move Move to make
     * @return Resulting game state
     */
    GameState<M> addMove(M move);

    /**
     * Checks whether the game is finished.
     *
     * @return True, if the game is finished
     */
    boolean isGameFinished();

    /**
     * Gets the game result.
     *
     * @return Game result
     */
    double getGameResult();

    /**
     * Gets the number of players.
     *
     * @return
     */
    int getPlayerCount();

    /**
     * Gets the position of the root player.
     *
     * @return Index of the root player
     */
    int getRootPlayerIndex();

    /**
     * Gets the position of the current player.
     *
     * @return Index of the currrent player
     */
    int getPlayerIndex();
}
