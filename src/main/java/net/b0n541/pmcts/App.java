/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package net.b0n541.pmcts;

import net.b0n541.pmcts.game.skat.Skat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        //TicTacToe.playGame();
        Skat.playGame();
    }
}