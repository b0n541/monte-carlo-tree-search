package net.b0n541.ai.game.tictactoe.player

import net.b0n541.ai.game.tictactoe.TicTacToeMove
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.distribution.UniformDistribution
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.lossfunctions.LossFunctions

class NeuralNetworkTicTacToePlayer : TicTacToePlayer() {
    override fun play(): TicTacToeMove {
        val inputs = Nd4j.zeros(18)
        val outputs = Nd4j.zeros(2)

        val neuralNetworkConfig = NeuralNetConfiguration.Builder()
            .seed(1234567890)
            .list()
            .layer(
                DenseLayer.Builder()
                    .nIn(27)
                    .nOut(1024)
                    .build()
            )
            .layer(
                DenseLayer.Builder()
                    .nIn(1024)
                    .nOut(2)
                    .build()
            )
            .layer(
                OutputLayer.Builder()
                    .nOut(2)
                    .activation(Activation.SOFTMAX)
                    .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                    .weightInit(UniformDistribution(0.0, 1.0))
                    .build()
            )
            .build()

        val net = MultiLayerNetwork(neuralNetworkConfig)
        net.init()

        return TicTacToeMove(playerSymbol!!, 0, 0)
    }
}