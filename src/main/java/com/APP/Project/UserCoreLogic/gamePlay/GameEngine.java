package com.APP.Project.UserCoreLogic.gamePlay;

import com.APP.Project.UserCoreLogic.GameEntities.Continent;
import com.APP.Project.UserCoreLogic.GameEntities.Country;
import com.APP.Project.UserCoreLogic.GameEntities.Player;
import com.APP.Project.UserCoreLogic.constants.enums.GamePlayStates;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidCommandException;
import com.APP.Project.UserCoreLogic.exceptions.OrderOutOfBoundException;
import com.APP.Project.UserCoreLogic.exceptions.ReinforcementOutOfBoundException;

import java.util.*;
import java.util.concud_gameEngineInstancerrent.ExecutionException;

public class GameEngine {    
    private static GameEngine d_gameEngineInstance;

    /**
     * Players of the game.
     */
    private List<Player> d_playerList;

    /**
     * Current turn of the player for issuing the order.
     */
    private int d_currentPlayerTurn = 0;

    /**
     * Keeps the track of the first player who was selected by the engine while <code>GAME_LOOP#ISSUE_ORDER</code>
     * state.
     */
    private int d_currentPlayerForIssuePhase = 0;

    /**
     * Keeps the track of the first player who was selected by the engine while <code>GAME_LOOP#EXECUTE_ORDER</code>
     * state.
     */
    private int d_currentPlayerForExecutionPhase = 0;

    /**
     * Represents the current state of the game loop.
     * <ul>
     *     <li>not_available</li>
     *     <li>assign_reinforcements</li>
     *     <li>issue_order</li>
     *     <li>execute_order</li>
     * </ul>
     *
     * @see GamePlayStates for more information.
     */
    private static GamePlayStates d_GamePlayStates;

    /**
     * Thread created by <code>GameEngine</code>. This thread should be responsive to interruption.
     */
    private Thread d_LoopThread;

    /**
     * Gets the single instance of the <code>GameEngine</code> class.
     * <p>If not created before, it creates the one.
     *
     * @return Value of the instance.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise() {
        d_playerList = new ArrayList<>();
        setGamePlayStates(GamePlayStates.NOT_AVAILABLE);
    }

    /**
     * Gets the state of <code>GameLoop</code>.
     *
     * @return Value of the state.
     */
    public static GamePlayStates getGamePlayStates() {
        return d_GamePlayStates;
    }

    /**
     * Sets the state of <code>GameLoop</code>.
     *
     * @param p_d_GamePlayStates Value of the state.
     */
    public static void setGamePlayStates(GamePlayStates p_d_GamePlayStates) {
        d_GamePlayStates = p_d_GamePlayStates;
    }

    /**
     * Adds the player to the list.
     *
     * @param p_player Player to be added.
     */
    public void addPlayer(Player p_player) {
        d_playerList.add(p_player);
    }

    /**
     * Removes the player from the list.
     *
     * @param p_player Player to be removed.
     */
    public void removePlayer(Player p_player) {
        d_playerList.remove(p_player);
    }

    /**
     * Gets the players of the game.
     *
     * @return Value of the player list.
     */
    public List<Player> getPlayerList() {
        return d_playerList;
    }

    /**
     * Get the player who's turn for issuing the order.
     *
     * @return Value of the player which will issue the order.
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
     * Starts the thread to iterate through various <code>GamePlayStates</code> states. Channels the exception to
     * <code>stderr</code> method.
     */
    public void startGameLoop() {
        if (d_LoopThread != null && d_LoopThread.isAlive()) {
            d_LoopThread.interrupt();
        }
        d_LoopThread = new Thread(() -> {
            try {
                // Responsive to thread interruption.
                while (!Thread.currentThread().isInterrupted()) {
                    this.onAssignReinforcementPhase();
                    this.onStartIssueOrderPhase();
                    this.onStartExecuteOrderPhase();
                }
            } catch (GameLoopIllegalStateException p_loopIllegalStateException) {
                VirtualMachine.getInstance().stderr(p_loopIllegalStateException.getMessage());
            } finally {
                // Set CLI#UserInteractionState to WAIT
                VirtualMachine.getInstance().stdout("GAME_ENGINE_TO_WAIT");
            }
        });
        d_LoopThread.start();
    }

    /**
     * Assigns each player the correct number of reinforcement armies according to the Warzone rules.
     *
     * @throws GameLoopIllegalStateException Throws if the engine tries to jump to illegal state.
     */
    public void onAssignReinforcementPhase() throws GameLoopIllegalStateException {
        if (GameEngine.getGamePlayStates() == GamePlayStates.NOT_AVAILABLE ||
                GameEngine.getGamePlayStates() == GamePlayStates.EXECUTE_ORDER) {
            GameEngine.setGamePlayStates(GamePlayStates.ASSIGN_REINFORCEMENTS);

            try {
                AssignReinforcementService l_reinforcementService = new AssignReinforcementService();
                l_reinforcementService.execute();
            } catch (VMException p_vmException) {
                VirtualMachine.getInstance().stderr(p_vmException.getMessage());
            }
        } else {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
    }

    /**
     * Starts the <code>GamePlayStates#ISSUE_ORDER</code> phase. Requests all players in round-robin fashion for the
     * issuing order until all the players have placed all their reinforcement armies on the map.
     * <p>
     * If the player issues an order with reinforcements more than enough they possess, it will request the same player
     * again for a valid order.
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
            // Find player who has reinforcements.
            Player l_currentPlayer;
            do {
                l_currentPlayer = this.getCurrentPlayer();
            } while (finishedIssuingOrders.contains(l_currentPlayer));

            // Until player issues the valid order.
            boolean l_invalidPreviousOrder;
            boolean l_canTryAgain;
            do {
                l_canTryAgain = true;
                try {
                    // Request player to issue the order.
                    l_currentPlayer.issueOrder();
                    l_invalidPreviousOrder = false;
                } catch (ReinforcementOutOfBoundException p_e) {
                    l_invalidPreviousOrder = true;

                    // Send exception message to CLI.
                    VirtualMachine.getInstance().stderr(p_e.getMessage());

                    // If all of its reinforcements have been placed, don't ask the player again.
                    if (l_currentPlayer.getRemainingReinforcementCount() == 0) {
                        l_canTryAgain = false;
                        finishedIssuingOrders.add(l_currentPlayer);
                    }
                } catch (EntityNotFoundException | InvalidCommandException | InvalidArgumentException p_exception) {
                    l_invalidPreviousOrder = true;
                    // Show VMException error to the user.
                    VirtualMachine.getInstance().stderr(p_exception.getMessage());
                } catch (InterruptedException | ExecutionException p_e) {
                    // If interruption occurred while issuing the order.
                    l_invalidPreviousOrder = true;
                }
            } while (l_invalidPreviousOrder && l_canTryAgain);
        }

        // Store to use when starting the issue phase again.
        this.d_currentPlayerForIssuePhase = this.d_currentPlayerTurn;
    }

    /**
     * Starts the <code>EXECUTE_ORDER</code> game loop state.
     * <p>
     * Gets the order of the player using <code>Player#nextOrder</code> method and executes it using the type of order.
     *
     * @throws GameLoopIllegalStateException Throws if the engine tries to jump to illegal state.
     */
    public void onStartExecuteOrderPhase() throws GameLoopIllegalStateException {
        if (GameEngine.getGamePlayStates() != GamePlayStates.ISSUE_ORDER) {
            throw new GameLoopIllegalStateException("Illegal state transition!");
        }
        GameEngine.setGamePlayStates(GamePlayStates.EXECUTE_ORDER);
        List<Player> finishedExecutingOrders = new ArrayList<>();

        VirtualMachine.getInstance().stdout("Execution of orders started!");

        this.d_currentPlayerTurn = this.d_currentPlayerForExecutionPhase;

        while (finishedExecutingOrders.size() != d_playerList.size()) {
            // Find player who has remaining orders to execute.
            Player l_currentPlayer;
            do {
                l_currentPlayer = this.getCurrentPlayer();
            } while (finishedExecutingOrders.contains(l_currentPlayer));
            try {
                // Get the next order
                Order l_currentOrder = l_currentPlayer.nextOrder();
                // Use VirtualMachine.stdout()
                l_currentOrder.execute();

                VirtualMachine.getInstance().stdout(String.format("\nExecuted %s", l_currentOrder.toString()));

                // If the current player does not have any orders left.
                if (!l_currentPlayer.hasOrders()) {
                    finishedExecutingOrders.add(l_currentPlayer);
                }
            } catch (OrderOutOfBoundException p_e) {
                VirtualMachine.getInstance().stderr(p_e.getMessage());
                finishedExecutingOrders.add(l_currentPlayer);
            }
        }

        // Store to use when starting the issue phase again.
        this.d_currentPlayerForExecutionPhase = this.d_currentPlayerTurn;
    }

    /**
     * {@inheritDoc} Shuts the <code>GameEngine</code>.
     */
    public void shutdown() {
        // Interrupt thread if it is alive.
        if (d_LoopThread != null && d_LoopThread.isAlive())
            d_LoopThread.interrupt();
    }
}
