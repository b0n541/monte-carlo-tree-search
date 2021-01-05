package net.b0n541.pmcts.mcts

enum class TestGameMove : GameMove {
    HEADS, TAILS;

    override fun toShortString(): String {
        return name
    }
}