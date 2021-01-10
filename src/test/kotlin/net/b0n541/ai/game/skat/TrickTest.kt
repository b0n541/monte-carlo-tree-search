package net.b0n541.ai.game.skat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TrickTest {
    @Test
    fun newTrick() {
        val trick = Trick(PlayerPosition.FOREHAND)

        assertThat(trick.isFinished).isFalse
        assertThat(trick.trickWinner).isNull()
    }

    @Test
    fun addCards() {
        val trick = Trick(PlayerPosition.FOREHAND)

        trick.addMove(SkatMove(PlayerPosition.FOREHAND, CJ))

        assertThat(trick.isFinished).isFalse
        assertThat(trick.trickWinner).isNull()
        assertThat(trick.cardValues).isEqualTo(2)

        trick.addMove(SkatMove(PlayerPosition.MIDDLEHAND, SA))

        assertThat(trick.isFinished).isFalse
        assertThat(trick.trickWinner).isNull()
        assertThat(trick.cardValues).isEqualTo(13)

        trick.addMove(SkatMove(PlayerPosition.REARHAND, HT))

        assertThat(trick.isFinished).isTrue
        assertThat(trick.trickWinner).isEqualTo(PlayerPosition.FOREHAND)
        assertThat(trick.cardValues).isEqualTo(23)
    }

    @Test
    fun addFourthCard() {
        val trick = Trick(PlayerPosition.FOREHAND)

        trick.addMove(SkatMove(PlayerPosition.FOREHAND, CJ))
        trick.addMove(SkatMove(PlayerPosition.FOREHAND, SJ))
        trick.addMove(SkatMove(PlayerPosition.FOREHAND, HJ))

        assertThrows<IllegalArgumentException> {
            trick.addMove(SkatMove(PlayerPosition.FOREHAND, DJ))
        }
    }

    @Test
    fun cardValues() {
        val trick = Trick(PlayerPosition.FOREHAND)

        trick.addMove(SkatMove(PlayerPosition.FOREHAND, CK))
        trick.addMove(SkatMove(PlayerPosition.FOREHAND, HA))
        trick.addMove(SkatMove(PlayerPosition.FOREHAND, C8))

        assertThat(trick.cardValues).isEqualTo(15)
    }
}