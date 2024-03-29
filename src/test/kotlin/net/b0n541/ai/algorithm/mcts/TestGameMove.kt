package net.b0n541.ai.algorithm.mcts

import net.b0n541.ai.game.common.GameMove

enum class TestGameMove : GameMove {
    HEADS, TAILS;

    override fun toShortString(): String {
        return name
    }
}