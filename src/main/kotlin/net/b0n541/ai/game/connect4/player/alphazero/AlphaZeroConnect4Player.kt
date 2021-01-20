package net.b0n541.ai.game.connect4.player.alphazero

import net.b0n541.ai.game.connect4.Connect4Move
import net.b0n541.ai.game.connect4.Connect4PlayerSymbol
import net.b0n541.ai.game.connect4.player.AbstractConnect4Player
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.graph.ComputationGraph
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.LoggerFactory

class AlphaZeroConnect4Player(playerSymbol: Connect4PlayerSymbol, firstPlayer: Connect4PlayerSymbol) :
    AbstractConnect4Player(playerSymbol, firstPlayer) {

    private val network: ComputationGraph = initGraph()

    private fun initGraph(): ComputationGraph {
        val config = NeuralNetConfiguration.Builder()
            .graphBuilder()
            .addInputs("input")
            .addLayer(
                "L1",
                DenseLayer.Builder().nIn(43).nOut(1024).build(),
                "input"
            )
            .addLayer(
                "out1",
                OutputLayer.Builder().lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD).nIn(1024).nOut(2)
                    .build(),
                "L1"
            )
            .addLayer(
                "out2",
                OutputLayer.Builder().lossFunction(LossFunctions.LossFunction.MSE).nIn(1024).nOut(7).build(),
                "L1"
            )
            .setOutputs("out1", "out2")
            .build()

        val graph = ComputationGraph(config)
        graph.init()

        return graph
    }

    override fun play(): Connect4Move {

        val input = gameState.networkInputs
        val outputs = network.output(input)

        LOG.info("Game value prediction: ${outputs[0]}")

        var bestMove = -1
        var highestMoveValue = Double.MIN_VALUE
        LOG.info("Move prediction:")
        for (i in 0..6) {
            var moveValue = outputs[1].getDouble(i)
            LOG.info("Move $i value: $moveValue.")
            if (moveValue > highestMoveValue) {
                highestMoveValue = moveValue
                bestMove = i
            }
        }
        LOG.info("Best move: $bestMove")
        return Connect4Move(playerSymbol, bestMove)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(AlphaZeroConnect4Player::class.java)
    }
}