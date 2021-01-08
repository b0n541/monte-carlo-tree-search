package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.tictactoe.PlayerSymbol
import net.b0n541.ai.game.tictactoe.TicTacToeMove
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.learning.config.Nesterovs
import org.nd4j.linalg.lossfunctions.LossFunctions

class AlphaZeroTicTacToePlayer(playerSymbol: PlayerSymbol, firstPlayer: PlayerSymbol) :
    AbstractTicTacToePlayer(playerSymbol, firstPlayer) {

    private val computationGraphConfig: ComputationGraphConfiguration = NeuralNetConfiguration.Builder()
        .seed(123)
        .updater(Nesterovs(0.1, 0.9)) //High Level Configuration
        .graphBuilder()  //For configuring ComputationGraph we call the graphBuilder method
        .addInputs("input") //Configuring Layers
        .addLayer("hidden1", DenseLayer.Builder().nIn(18).nOut(1024).dropOut(0.8).build(), "input")
        .addLayer("hidden2", DenseLayer.Builder().nIn(1024).nOut(1024).dropOut(0.8).build(), "hidden1")
        .addLayer("hidden3", DenseLayer.Builder().nIn(1024).nOut(512).dropOut(0.8).build(), "hidden2")
        .addLayer("hidden4", DenseLayer.Builder().nIn(512).nOut(256).dropOut(0.8).build(), "hidden3")
        .addLayer(
            "gameResult",
            OutputLayer.Builder().lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD).nIn(256).nOut(3)
                .build(),
            "hidden4"
        )
        .addLayer(
            "bestMove",
            OutputLayer.Builder()
                .nIn(256).nOut(9)
                .activation(Activation.SOFTMAX)
                .lossFunction(LossFunctions.LossFunction.MSE)
                .build(),
            "hidden4"
        )
        .setOutputs("gameResult", "bestMove")
        .build() //Building configuration

    private val computationGraph = ComputationGraph(computationGraphConfig)

    init {
        computationGraph.init()
        computationGraph.setListeners(ScoreIterationListener(1))
    }

    override fun play(): TicTacToeMove {

        val inputs = Nd4j.createFromArray(
            arrayOf(
                doubleArrayOf(
                    1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0,
                    1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0
                )
            )
        )

        computationGraph.setInputs(inputs)

        val result = computationGraph.feedForward()

        println("Game result: ${result["gameResult"]}")
        println("Best move:   ${result["bestMove"]}")

        val gameResults = Nd4j.createFromArray(arrayOf(doubleArrayOf(0.0, 0.0, 1.0)))
        val bestMoves = Nd4j.createFromArray(
            arrayOf(doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))
        )

        computationGraph.setLabels(gameResults, bestMoves)

        for (i in 0 until 20) {
            computationGraph.computeGradientAndScore()
            println("Score: ${computationGraph.score()}")
        }

        return TicTacToeMove(playerSymbol, 0, 0)
    }
}