package com.APP.Project.UserCoreLogic.gamePlay;

import com.APP.Project.UserCoreLogic.GameEngine;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.phases.Execute;
import com.APP.Project.UserCoreLogic.phases.IssueOrder;
import com.APP.Project.UserCoreLogic.phases.PlaySetup;
import com.APP.Project.UserCoreLogic.phases.Reinforcement;
import com.APP.Project.UserCoreLogic.constants.interfaces.Engine;
import com.APP.Project.UserCoreLogic.constants.interfaces.Order;
import com.APP.Project.UserCoreLogic.exceptions.GameLoopIllegalStateException;
import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages players and their orders runtime information; Responsible for executing orders in round-robin fashion.
 *
 * @author Rupal Kapoor
 * @version 1.0
 */
public class GamePlayEngine implements Engine {
    /**
     * This represents the instance of the GamePlayEngine.
     */
    private static GamePlayEngine d_Instance;

    /**
     * This is a list of the players of the game.
     */
    private List<Player> d_playerList;

    /**
     * Denotes the current turn of the player for issuing the order.
     */
    private int d_currentPlayerTurn = 0;

    /**
     * This attribute enables to keeps the track of the first player that was selected by the engine while the game loop issue order
     * state.
     */
    private int d_currentPlayerForIssuePhase = 0;

    /**
     * This attribute helps to keep the track of the first player selected by the engine while game loop executes the order state.
     */
    private int d_currentPlayerForExecutionPhase = 0;

    /**
     * This is the thread created by the GamePlayEngine. It is responsive to interruption.
     */
    private Thread d_LoopThread;

    /**
     * This metric keeps track of the execution-index, i.e. it helps to decide order of execution and expiration phase.
     */
    private static int d_currentExecutionIndex = 0;

    /**
     * This captures the list of the future orders which are supposed to be executed later in the iterations.
     */
    private final List<Order> d_futurePhaseOrders = new ArrayList<>();

    /**
     * This method is used to get the single instance of the GamePlayEngine class.
     * @return Value of the instance.
     */
    public static GamePlayEngine getInstance() {
        if (d_Instance == null) {
            d_Instance = new GamePlayEngine();
        }
        return d_Instance;
    }

    /**
     * This is to privatize the instance creation of this class
     */
    private GamePlayEngine() {
        this.initialise();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise() {
        d_playerList = new ArrayList<>();
    }

    /**
     * This method add the player to the list.
     *
     * @param p_player This denotes the player to be added.
     */
    public void addPlayer(Player p_player) {
        d_playerList.add(p_player);
    }

    /**
     * This method removes the player from the list.
     *
     * @param p_player This denotes the player to be removed.
     */
    public void removePlayer(Player p_player) {
        d_playerList.remove(p_player);
    }

    /**
     * This method gets the players of the game.
     *
     * @return the value of the list of players.
     */
    public List<Player> getPlayerList() {
        return d_playerList;
    }

    /**
     * This method gets the player who has the turn for issuing the order.
     *
     * @return Player object of the player which will issue the order.
     */
    public Player getCurrentPlayer() {
        Player l_currentPlayer = d_playerList.get(d_currentPlayerTurn);
        d_currentPlayerTurn++;
        // Round-robin fashion
        if (d_currentPlayerTurn >= d_playerList.size()) {
            d_currentPlayerTurn = 0;
        }
        return l_currentPlayer;
    }

    /**
     * This method gets the index of current player
     *
     * @return The value of index of current player
     */
    public int getCurrentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    /**
     * This method sets the index of current player.
     *
     * @param p_currentPlayerTurn This is the value of index of current player.
     */
    public void setCurrentPlayerTurn(int p_currentPlayerTurn) {
        d_currentPlayerTurn = p_currentPlayerTurn;
    }

    /**
     * This method gets the previously stored player index who has the turn to issue an order.
     *
     * @return Value of the index.
     */
    public int getCurrentPlayerForIssuePhase() {
        return d_currentPlayerForIssuePhase;
    }

    /**
     * This method sets the index of the player that is going to issue an order in the next iteration.
     *
     * @param p_currentPlayerForIssuePhase The value of the index to be set
     */
    public void setCurrentPlayerForIssuePhase(int p_currentPlayerForIssuePhase) {
        d_currentPlayerForIssuePhase = p_currentPlayerForIssuePhase;
    }

    /**
     * This method gets the previously stored player index to get an order of the player for the execution
     *
     * @return The value of the index.
     */
    public int getCurrentPlayerForExecutionPhase() {
        return d_currentPlayerForExecutionPhase;
    }

    /**
     * This method sets the index of the player for which the order is going to be executed first in the next iteration.
     *
     * @param p_currentPlayerForExecutionPhase The value of the index to be set.
     */
    public void setCurrentPlayerForExecutionPhase(int p_currentPlayerForExecutionPhase) {
        d_currentPlayerForExecutionPhase = p_currentPlayerForExecutionPhase;
    }

    /**
     * This method gets the current execution index. This index helps to keep track of orders, some of which should be executed and
     * others should be expired during this loop iteration.
     *
     * @return The integer value of the index.
     */
    public static int getCurrentExecutionIndex() {
        return d_currentExecutionIndex;
    }

    /**
     * This method gets the list of future orders which should be executed during this phase.
     *
     * @return The value of the list of orders.
     */
    public List<Order> getCurrentFutureOrders() {
        return d_futurePhaseOrders.stream().filter(p_futureOrder ->
                p_futureOrder.getExecutionIndex() == d_currentExecutionIndex
        ).collect(Collectors.toList());
    }

    /**
     * This method gets the list of future orders which are going to be expired after current loop iteration.
     *
     * @return The value of the list of orders.
     */
    public List<Order> getExpiredFutureOrders() {
        return d_futurePhaseOrders.stream().filter(p_futureOrder ->
                p_futureOrder.getExpiryIndex() >= d_currentExecutionIndex
        ).collect(Collectors.toList());
    }

    /**
     * This method adds the order to be executed in future iteration.
     *
     * @param p_futureOrder The value of the order to be added.
     */
    public void addFutureOrder(Order p_futureOrder) {
        this.d_futurePhaseOrders.add(p_futureOrder);
    }

    /**
     * This method is used to remove the order. It is called if the order has been expired.
     *
     * @param p_futureOrder The value of the order to be added.
     */
    public void removeFutureOrder(Order p_futureOrder) {
        this.d_futurePhaseOrders.remove(p_futureOrder);
    }

    /**
     * This method increments the current execution index.
     */
    public static void incrementIndex() {
        d_currentExecutionIndex++;
    }

    /**
     * This method starts the thread to iterate through various game loop states. Channels the exception to
     * stderr method.
     */
    public void startGameLoop() {
        if (d_LoopThread != null && d_LoopThread.isAlive()) {
            d_LoopThread.interrupt();
        }
        d_LoopThread = new Thread(() -> {
            GameEngine l_gameEngine = GameEngine.getInstance();
            try {
                if (l_gameEngine.getGamePhase().getClass().equals(PlaySetup.class)) {
                    l_gameEngine.getGamePhase().nextState();
                } else {
                    throw new GameLoopIllegalStateException("Illegal state transition!");
                }
                // Responsive to thread interruption.
                while (!Thread.currentThread().isInterrupted()) {
                    if (l_gameEngine.getGamePhase().getClass().equals(Reinforcement.class)) {
                        l_gameEngine.getGamePhase().reinforce();
                    }
                    if (l_gameEngine.getGamePhase().getClass().equals(IssueOrder.class)) {
                        l_gameEngine.getGamePhase().issueOrder();
                    }
                    if (l_gameEngine.getGamePhase().getClass().equals(Execute.class)) {
                        l_gameEngine.getGamePhase().fortify();
                    }
                    l_gameEngine.getGamePhase().nextState();
                }
            } catch (UserCoreLogicException p_userCoreLogicException) {
                UserCoreLogic.getInstance().stderr(p_userCoreLogicException.getMessage());
            } finally {
                // This will set UserInterface#UserInteractionState to WAIT
                UserCoreLogic.getInstance().stdout("GAME_ENGINE_STOPPED");
            }
        });
        d_LoopThread.start();
    }

    /**
     * {@inheritDoc} Shuts the <code>GamePlayEngine</code>.
     */
    public void shutdown() {
        // Interrupt thread if it is alive.
        if (d_LoopThread != null && d_LoopThread.isAlive())
            d_LoopThread.interrupt();
    }
}