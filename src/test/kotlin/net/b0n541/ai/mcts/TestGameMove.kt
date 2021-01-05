package net.b0n541.ai.mcts

enum class TestGameMove : GameMove {
    HEADS, TAILS;

    override fun toShortString(): String {
        return name
    }
}