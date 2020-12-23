//package net.b0n541.pmcts.game.skat;
//
//import net.b0n541.pmcts.mcts.GameMove;
//import net.b0n541.pmcts.mcts.GameState;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class SkatGameState implements GameState<SkatGameMove> {
//
//    private final SkatGameType gameType;
//    private final PlayerPosition gameForeHand;
//    private final List<SkatGameMove> gameMoves;
//    private final List<Trick> tricks;
//    private final PlayerPosition nextPlayer;
//
//    public SkatGameState(final SkatGameType gameType, final PlayerPosition gameForeHand) {
//        this.gameType = gameType;
//        this.gameForeHand = gameForeHand;
//        nextPlayer = gameForeHand;
//        gameMoves = new ArrayList<>();
//        tricks = new ArrayList<>();
//        //tricks.add(new Trick(gameForeHand));
//    }
//
//    @Override
//    public String getNextPlayer() {
//        return nextPlayer.toString();
//    }
//
//    @Override
//    public List<String> getPlayers() {
//        return List.of(PlayerPosition.FOREHAND.toString(), PlayerPosition.MIDDLEHAND.toString(), PlayerPosition.REARHAND.toString());
//    }
//
//    @Override
//    public List<SkatGameMove> getPossibleMoves() {
//        return null;
//    }
//
//    @Override
//    public SkatGameState addMove(final SkatGameMove move) {
////        SkatGameState newState = withGameMoves(new ArrayList<>(gameMoves));
////        newState.gameMoves.add(move);
////        final Trick lastTrick = newState.getLastTrick();
////        lastTrick.addMove(move);
////        if (lastTrick.isFinished()) {
////            newState = newState.withNextPlayer(lastTrick.getTrickWinner());
////            newState = newState.withTricks(new ArrayList<>(tricks));
////            newState.tricks.add(new Trick(lastTrick.getTrickWinner()));
////        } else {
////            newState = newState.withNextPlayer(move.player.getNextPlayer());
////        }
////        return newState;
//
//
////        final SkatGameStateBuilder newState = SkatGameState.builder()
////                .gameType(gameType)
////                .gameForeHand(gameForeHand)
////                .gameMoves(new ArrayList<>(gameMoves));
//////        getLastTrick().copy()
////        final List<Trick> newTricks = new ArrayList<>(tricks);
////        final Trick newLastTrick = newTricks.get(newTricks.size() - 1);
////        newLastTrick.addMove(move);
////
////        if (newLastTrick.isFinished()) {
////            //newTricks.add(new Trick(newLastTrick.getTrickWinner()));
////            newState.nextPlayer(newLastTrick.getTrickWinner());
////        } else {
////            newState.nextPlayer(move.player.getNextPlayer());
////        }
////
////        return newState.build();
//        return null;
//    }
//
//    private Trick getLastTrick() {
//        return tricks.get(tricks.size() - 1);
//    }
//
//    @Override
//    public boolean isGameFinished() {
//        return tricks.size() == 10 && tricks.get(9).isFinished();
//    }
//
//    @Override
//    public Map<String, Double> getGameValues() {
//        return null;
//    }
//
//    @Override
//    public GameMove getLastMove() {
//        return null;
//    }
//}
