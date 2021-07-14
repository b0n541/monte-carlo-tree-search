package net.b0n541.ai.tournament

import net.b0n541.ai.game.common.GameMove
import net.b0n541.ai.game.common.GamePlayer
import net.b0n541.ai.game.common.PlayerSymbol
import kotlin.math.pow

fun <S : PlayerSymbol, M : GameMove> getPlayerCombinations(
    numberOfPlayerPerGame: Int,
    player: List<GamePlayer<S, M>>
): List<List<GamePlayer<S, M>>> {
    val result = mutableListOf<MutableList<GamePlayer<S, M>>>()

    repeat(numberOfGames(numberOfPlayerPerGame, player.size)) {
        result.add(mutableListOf())
    }

    // TODO Generalize with tree
    if (numberOfPlayerPerGame == 2) {
        if (player.size == 2) {
            result[0].add(player[0])
            result[1].add(player[1])

            result[0].add(player[1])
            result[1].add(player[0])
        }
        if (player.size == 3) {
            result[0].add(player[0])
            result[1].add(player[0])
            result[2].add(player[1])
            result[3].add(player[1])
            result[4].add(player[2])
            result[5].add(player[2])

            result[0].add(player[1])
            result[1].add(player[2])
            result[2].add(player[0])
            result[3].add(player[2])
            result[4].add(player[0])
            result[5].add(player[1])
        }
    }
    if (numberOfPlayerPerGame == 3) {
        if (player.size == 2) {
            result[0].add(player[0])
            result[1].add(player[0])
            result[2].add(player[0])
            result[3].add(player[1])
            result[4].add(player[1])
            result[5].add(player[1])

            result[0].add(player[0])
            result[1].add(player[1])
            result[2].add(player[1])
            result[3].add(player[1])
            result[4].add(player[0])
            result[5].add(player[0])

            result[0].add(player[1])
            result[1].add(player[0])
            result[2].add(player[1])
            result[3].add(player[0])
            result[4].add(player[1])
            result[5].add(player[0])
        }
        if (player.size == 3) {
            result[0].add(player[0])
            result[1].add(player[0])
            result[2].add(player[0])
            result[3].add(player[0])
            result[4].add(player[0])
            result[5].add(player[0])
            result[6].add(player[0])
            result[7].add(player[0])
            result[8].add(player[1])
            result[9].add(player[1])
            result[10].add(player[1])
            result[11].add(player[1])
            result[12].add(player[1])
            result[13].add(player[1])
            result[14].add(player[1])
            result[15].add(player[1])
            result[16].add(player[2])
            result[17].add(player[2])
            result[18].add(player[2])
            result[19].add(player[2])
            result[20].add(player[2])
            result[21].add(player[2])
            result[22].add(player[2])
            result[23].add(player[2])

            result[0].add(player[0])
            result[1].add(player[0])
            result[2].add(player[1])
            result[3].add(player[1])
            result[4].add(player[2])
            result[5].add(player[2])
            result[6].add(player[1])
            result[7].add(player[2])
            result[8].add(player[1])
            result[9].add(player[1])
            result[10].add(player[0])
            result[11].add(player[0])
            result[12].add(player[2])
            result[13].add(player[2])
            result[14].add(player[0])
            result[15].add(player[2])
            result[16].add(player[2])
            result[17].add(player[2])
            result[18].add(player[0])
            result[19].add(player[0])
            result[20].add(player[1])
            result[21].add(player[1])
            result[22].add(player[0])
            result[23].add(player[1])

            result[0].add(player[1])
            result[1].add(player[2])
            result[2].add(player[0])
            result[3].add(player[1])
            result[4].add(player[0])
            result[5].add(player[2])
            result[6].add(player[2])
            result[7].add(player[1])
            result[8].add(player[0])
            result[9].add(player[2])
            result[10].add(player[1])
            result[11].add(player[0])
            result[12].add(player[1])
            result[13].add(player[2])
            result[14].add(player[2])
            result[15].add(player[0])
            result[16].add(player[0])
            result[17].add(player[1])
            result[18].add(player[2])
            result[19].add(player[0])
            result[20].add(player[2])
            result[21].add(player[1])
            result[22].add(player[1])
            result[23].add(player[0])
        }
    }

    return result.toList()
}

private fun numberOfGames(
    numberOfPlayerPerGame: Int,
    numberOfPlayerTypes: Int
) = (numberOfPlayerTypes.toDouble().pow(numberOfPlayerPerGame.toDouble()) - numberOfPlayerTypes).toInt()