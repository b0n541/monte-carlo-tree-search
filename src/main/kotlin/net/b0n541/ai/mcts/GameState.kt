package net.b0n541.ai.mcts

/**
 * Interface for all game state implementations to be used in the [MonteCarloTreeSearch].
 */
interface GameState<M : GameMove> {
    /**
     * Gets all player identifications.
     *
     * return List of player identifications
     */
    val players: List<String>

    /**
     * Gets the identification of the player at the current game state.
     *
     * @return String representing the current player
     */
    val nextPlayer: String

    /**
     * Gets all possible moves from the current game state.
     *
     * @return All possible moves
     */
    val possibleMoves: List<M>

    /**
     * Adds a move to the game state and returns the resulting game state.
     *
     * @param move Move to make
     * @return Resulting game state
     */
    fun addMove(move: M): GameState<M>

    /**
     * Checks whether the game is finished.
     *
     * @return True, if the game is finished
     */
    val isGameFinished: Boolean

    /**
     * Gets the game value for each player.
     *
     * @return Game result for each player
     */
    val gameValues: Map<String, Double>

    /**
     * Gets the last move done.
     *
     * @return Last move
     */
    val lastMove: M
}