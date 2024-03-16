package com.APP.Project.UserCoreLogic.gamePlay.services;

import com.APP.Project.UserCoreLogic.GameEngine;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidArgumentException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidCommandException;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This service is responsible for requesting players for issuing orders.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class IssueOrderService {
    private final LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getLogger();

    /**
     * Requests all players in round-robin fashion for the issuing order until all the players have placed all their
     * reinforcement armies on the map.
     * <p>
     * If the player issues an order with reinforcements more than enough they possess, it will request the same player
     * again for a valid order.
     */
    public void execute() {
        List<Player> finishedIssuingOrders = new ArrayList<>();
        GamePlayEngine l_gamePlayEngine = GameEngine.GAME_PLAY_ENGINE();
        l_gamePlayEngine.setCurrentPlayerTurn(l_gamePlayEngine.getCurrentPlayerForIssuePhase());

        while (finishedIssuingOrders.size() != l_gamePlayEngine.getPlayerList().size()) {
            // Find player who has reinforcements.
            Player l_currentPlayer;
            do {
                l_currentPlayer = l_gamePlayEngine.getCurrentPlayer();
            } while (finishedIssuingOrders.contains(l_currentPlayer));

            // Until player issues the valid order.
            boolean l_invalidPreviousOrder;
            do {
                try {
                    // Request player to issue the order.
                    if (l_currentPlayer.issueOrder()) {
                        // Player won't be asked again for issuing orders for this phase.
                        finishedIssuingOrders.add(l_currentPlayer);
                    }
                    l_invalidPreviousOrder = false;
                } catch (EntityNotFoundException | InvalidCommandException | InvalidArgumentException p_exception) {
                    l_invalidPreviousOrder = true;
                    // Show UserCoreLogicException error to the user.
                    d_logEntryBuffer.dataChanged("Issue order", p_exception.getMessage());
                    UserCoreLogic.getInstance().stderr(p_exception.getMessage());
                } catch (InterruptedException | ExecutionException p_e) {
                    // If interruption occurred while issuing the order.
                    l_invalidPreviousOrder = true;
                }
            } while (l_invalidPreviousOrder);
        }

        // Store to use when starting the issue phase again.
        l_gamePlayEngine.setCurrentPlayerForIssuePhase(l_gamePlayEngine.getCurrentPlayerTurn());
    }
}
