package net.b0n541.pmcts.game.skat

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

        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.CJ))

        assertThat(trick.isFinished).isFalse
        assertThat(trick.trickWinner).isNull()
        assertThat(trick.cardValues).isEqualTo(2)

        trick.addMove(SkatGameMove(PlayerPosition.MIDDLEHAND, Card.SA))

        assertThat(trick.isFinished).isFalse
        assertThat(trick.trickWinner).isNull()
        assertThat(trick.cardValues).isEqualTo(13)

        trick.addMove(SkatGameMove(PlayerPosition.REARHAND, Card.HT))

        assertThat(trick.isFinished).isTrue
        assertThat(trick.trickWinner).isEqualTo(PlayerPosition.FOREHAND)
        assertThat(trick.cardValues).isEqualTo(23)
    }

    @Test
    fun addFourthCard() {
        val trick = Trick(PlayerPosition.FOREHAND)

        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.CJ))
        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.SJ))
        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.HJ))

        assertThrows<IllegalArgumentException> {
            trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.DJ))
        }
    }

    @Test
    fun cardValues() {
        val trick = Trick(PlayerPosition.FOREHAND)

        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.CK))
        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.HA))
        trick.addMove(SkatGameMove(PlayerPosition.FOREHAND, Card.C8))

        assertThat(trick.cardValues).isEqualTo(15)
    }
    

}