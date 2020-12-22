package org.b0n541.pmcts.mcts;

import java.util.List;
import java.util.Map;

/**
 * Interface for all game state implementations to be used in the {@link MonteCarloTreeSearch}.
 */
public interface GameState<M extends GameMove> {

    /**
     * Gets all player identifications.
     * <p>
     * return List of player identifications
     */
    List<String> getPlayers();

    /**
     * Gets the identification of the player at the current game state.
     *
     * @return String representing the current player
     */
    String getPlayer();

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
     * Gets the game value for each player.
     *
     * @return Game result for each player
     */
    Map<String, Double> getGameValues();

    /**
     * Gets the last move done.
     *
     * @return Last move
     */
    GameMove getLastMove();
}
