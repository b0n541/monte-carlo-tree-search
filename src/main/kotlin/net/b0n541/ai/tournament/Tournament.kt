package net.b0n541.ai.tournament

import net.b0n541.ai.game.tictactoe.player.TicTacToePlayer

fun getPlayerCombinations(
    numberOfPlayerPerGame: Int,
    player: List<TicTacToePlayer>
): List<List<TicTacToePlayer>> {
    val result = mutableListOf<MutableList<TicTacToePlayer>>()

    repeat((player.size * numberOfPlayerPerGame)) { result.add(mutableListOf()) }

    result[0].add(player[0])
    result[0].add(player[0])
    result[1].add(player[0])
    result[1].add(player[1])
    result[2].add(player[1])
    result[2].add(player[0])
    result[3].add(player[1])
    result[3].add(player[1])

    return result.toList()
}