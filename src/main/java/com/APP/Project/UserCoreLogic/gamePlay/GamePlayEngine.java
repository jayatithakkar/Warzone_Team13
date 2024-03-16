package com.APP.Project.UserCoreLogic.gamePlay;

import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.constants.enums.GamePlayStates;
import com.APP.Project.UserCoreLogic.constants.interfaces.Engine;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.gamePlay.services.ReinforcementService;
import com.APP.Project.UserCoreLogic.game_entities.Order;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class GameEngine implements Engine {
    private static GameEngine d_gameEngineInstance;

    /**
     * This represents the list of all players of the game.
     */
    private List<Player> d_playerList;
    /**
     * This captures the first player which was selected by the
     * engine while in the issue order state of the game
     */
    private int d_currentPlayerForIssuePhase = 0;

    /**
     * This captures the current turn of the player that is issuing the order.
     */
    private int d_currentPlayerTurn = 0;

    /**
     * Keeps the track of the first player who was selected by the engine while <code>GAME_LOOP#EXECUTE_ORDER</code>
     * state.
     */
    private int d_currentPlayerForExecutionPhase = 0;
    /**
     * This is the thread created by the GameEngine. It is responsive to interruption.
     */
    private Thread d_LoopThread;
    /**
     * This depicts the current state of the game loop.
     * Ref. GamePlayStates enum
     */
    private static GamePlayStates d_GamePlayStates;

    /**
     * This method fetches a single instance of GameEngine class.
     * In case of no existing instance, one is created.
     *
     * @return the return value of the instance.
     */
    public static GameEngine getInstance() {
        if (d_gameEngineInstance == null) {
            d_gameEngineInstance = new GameEngine();
        }
        return d_gameEngineInstance;
    }

    private GameEngine() {
        this.initialise();
    }
  
    public void initialise() {
        d_playerList = new ArrayList<>();
        setGamePlayStates(GamePlayStates.NOT_AVAILABLE);
    }

    /**
     * This method Sets the state of game play state loop.
     *
     * @param p_d_GamePlayStates returns the value of the state.
     */
    public static void setGamePlayStates(GamePlayStates p_d_GamePlayStates) {
        d_GamePlayStates = p_d_GamePlayStates;
    }

    /**
     * This method fetches the state of the GamePlayStates.
     *
     * @return the return value of the state.
     */
    public static GamePlayStates getGamePlayStates() {
        return d_GamePlayStates;
    }

    /**
     * This method adds the player to the list.
     *
     * @param p_player Player to be added.
     */
    public void addPlayer(Player p_player) {
        d_playerList.add(p_player);
    }

    /**
     * This method fetched the players of the game.
     *
     * @return Value of the player list.
     */
    public List<Player> getPlayerList() {
        return d_playerList;
    }

    /**
     * This method removes the player from the list.
     *
     * @param p_player Player to be removed.
     */
    public void removePlayer(Player p_player) {
        d_playerList.remove(p_player);
    }

    /**
     * This method is used to fetch the player whose turn it is for issuing the order.
     *
     * @return the value of the player who will issue the order.
     */
    public Player getCurrentPlayer() {
        Player l_currentPlayer = d_playerList.get(d_currentPlayerTurn);
        d_currentPlayerTurn++;
        if (d_currentPlayerTurn >= d_playerList.size()) {
            d_currentPlayerTurn = 0;
        }
        return l_currentPlayer;
    }

    public void onAssignReinforcementPhase() throws GameLoopIllegalStateException {
        if (GameEngine.getGamePlayStates() == GamePlayStates.NOT_AVAILABLE ||
                GameEngine.getGamePlayStates() == GamePlayStates.EXECUTE_ORDER) {
            GameEngine.setGamePlayStates(GamePlayStates.ASSIGN_REINFORCEMENTS);

            try {
                ReinforcementService l_reinforcementService = new ReinforcementService();
                l_reinforcementService.execute();
            } catch (UserCoreLogicException p_vmException) {
                UserCoreLogic.getInstance().stderr(p_vmException.getMessage());
            }

        } else {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
    }

    /**
     * This method is used to starts the game loop state: execute order.
     *
     * This gets the order of the player and executes it using the type of order.
     *
     * @throws GameLoopIllegalStateException is Thrown if the engine tries to jump to an illegal state.
     */
    public void onStartExecuteOrderPhase() throws GameLoopIllegalStateException {
        if (GameEngine.getGamePlayStates() != GamePlayStates.ISSUE_ORDER) {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
        GameEngine.setGamePlayStates(GamePlayStates.EXECUTE_ORDER);
        List<Player> finishedExecutingOrders = new ArrayList<>();


        this.d_currentPlayerTurn = this.d_currentPlayerForExecutionPhase;

        while (finishedExecutingOrders.size() != d_playerList.size()) {

            Player l_currentPlayer;
            do {
                l_currentPlayer = this.getCurrentPlayer();
            } while (finishedExecutingOrders.contains(l_currentPlayer));
            try {
                Order l_currentOrder = l_currentPlayer.nextOrder();
                l_currentOrder.execute();

                UserCoreLogic.getInstance().stdout(String.format("\nExecuted %s", l_currentOrder.toString()));

                if (!l_currentPlayer.hasOrders()) {
                    finishedExecutingOrders.add(l_currentPlayer);
                }
            } catch (OrderOutOfBoundException p_e) {
                UserCoreLogic.getInstance().stderr(p_e.getMessage());
                finishedExecutingOrders.add(l_currentPlayer);
            }
        }

        this.d_currentPlayerForExecutionPhase = this.d_currentPlayerTurn;
    }

    /**
     * This method begins the issue orders phase. It requests all players to issue orders
     * until all of the players have placed all their reinforcement armies on the map.
     *
     * In case the player issues an order with reinforcements more than they possess,
     * it will request the same player again for a valid order.
     *
     * @throws GameLoopIllegalStateException Throws if the engine tries to jump to illegal state.
     */
    public void onStartIssueOrderPhase() throws GameLoopIllegalStateException {
        if (GameEngine.getGamePlayStates() != GamePlayStates.ASSIGN_REINFORCEMENTS) {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
        GameEngine.setGamePlayStates(GamePlayStates.ISSUE_ORDER);
        List<Player> finishedIssuingOrders = new ArrayList<>();

        this.d_currentPlayerTurn = this.d_currentPlayerForIssuePhase;

        while (finishedIssuingOrders.size() != d_playerList.size()) {

            Player l_currentPlayer;
            do {
                l_currentPlayer = this.getCurrentPlayer();
            } while (finishedIssuingOrders.contains(l_currentPlayer));


            boolean l_invalidPreviousOrder;
            boolean l_canTryAgain;
            do {
                l_canTryAgain = true;
                try {
                    l_currentPlayer.issueOrder();
                    l_invalidPreviousOrder = false;
                } catch (ReinforcementOutOfBoundException p_e) {
                    l_invalidPreviousOrder = true;

                    UserCoreLogic.getInstance().stderr(p_e.getMessage());

                    if (l_currentPlayer.getRemainingReinforcementCount() == 0) {
                        l_canTryAgain = false;
                        finishedIssuingOrders.add(l_currentPlayer);
                    }
                } catch (EntityNotFoundException | InvalidCommandException | InvalidArgumentException p_exception) {
                    l_invalidPreviousOrder = true;

                    UserCoreLogic.getInstance().stderr(p_exception.getMessage());
                } catch (InterruptedException | ExecutionException p_e) {
                    l_invalidPreviousOrder = true;
                }
            } while (l_invalidPreviousOrder && l_canTryAgain);
        }

        this.d_currentPlayerForIssuePhase = this.d_currentPlayerTurn;
    }

    /**
     * This method assigns each player the correct number of reinforcement armies
     * according to the standard Warzone rules.
     *
     * @throws GameLoopIllegalStateException Throws if the engine tries to jump to illegal state.
     */
    /**
     * This method begins the thread to iterate through various states of the game.
     */
    public void startGameLoop() {
        if (d_LoopThread != null && d_LoopThread.isAlive()) {
            d_LoopThread.interrupt();
        }
        d_LoopThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    this.onAssignReinforcementPhase();
                    this.onStartIssueOrderPhase();
                    this.onStartExecuteOrderPhase();
                }
            } catch (GameLoopIllegalStateException p_loopIllegalStateException) {
                UserCoreLogic.getInstance().stderr(p_loopIllegalStateException.getMessage());
            }
            finally {
                // Set CLI#UserInteractionState to WAIT
                UserCoreLogic.getInstance().stdout("GAME_ENGINE_TO_WAIT");
            }

        });
        d_LoopThread.start();
    }

    /**
     * This method shuts the GameEngine
     */
    public void shutdown() {
        // Interrupt thread if it is alive.
        if (d_LoopThread != null && d_LoopThread.isAlive())
            d_LoopThread.interrupt();
    }
}
