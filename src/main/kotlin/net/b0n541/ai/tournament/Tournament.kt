package net.b0n541.ai.tournament

import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.GamePlayer
import net.b0n541.ai.game.common.PlayerSymbol

fun <S : PlayerSymbol, M : GameMove> getPlayerCombinations(
    numberOfPlayerPerGame: Int,
    player: List<GamePlayer<S, M>>
): List<List<GamePlayer<S, M>>> {

    val tournamentTree = TreeNode(emptyList<GamePlayer<S, M>>())

    addChildren(tournamentTree, player, numberOfPlayerPerGame)

    return tournamentTree.getLeafNodeValues()
}

private fun <S : PlayerSymbol, M : GameMove> addChildren(
    parent: TreeNode<List<GamePlayer<S, M>>>,
    player: List<GamePlayer<S, M>>,
    depth: Int
) {
    if (depth > 1) {
        player.forEach {
            val child = addChild(parent, it)
            addChildren(child, player, depth - 1)
        }
    } else {
        player.forEach {
            if (containsNotOnly(parent, it)) {
                addChild(parent, it)
            }
        }
    }
}

private fun <M : GameMove, S : PlayerSymbol> addChild(
    parent: TreeNode<List<GamePlayer<S, M>>>,
    it: GamePlayer<S, M>
): TreeNode<List<GamePlayer<S, M>>> {
    val childPlayer = parent.value.toMutableList() + it
    val child = TreeNode(childPlayer)
    parent.addChild(child)
    return child
}

private fun <M : GameMove, S : PlayerSymbol> containsNotOnly(
    parent: TreeNode<List<GamePlayer<S, M>>>,
    it: GamePlayer<S, M>
): Boolean {
    val parentPlayer = parent.value.toMutableSet()
    parentPlayer.remove(it)
    return parentPlayer.isNotEmpty()
}

internal class TreeNode<T>(value: T) {
    var value: T = value
    var parent: TreeNode<T>? = null

    var children: MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(node: TreeNode<T>) {
        children.add(node)
        node.parent = this
    }

    fun getLeafNodeValues(): List<T> {
        val result = mutableListOf<T>()

        children.forEach {
            result.addAll(getLeafNodeValues(it))
        }

        return result
    }

    private fun getLeafNodeValues(parent: TreeNode<T>): List<T> {
        val result = mutableListOf<T>()

        parent.children.forEach {
            if (it.children.isEmpty()) {
                result.add(it.value)
            } else {
                result.addAll(getLeafNodeValues(it))
            }
        }

        return result
    }
}