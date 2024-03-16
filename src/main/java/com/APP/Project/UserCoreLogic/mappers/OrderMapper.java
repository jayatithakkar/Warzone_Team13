package com.APP.Project.UserCoreLogic.mappers;

import com.APP.Project.UserCoreLogic.constants.enums.OrderTypes;
import com.APP.Project.UserCoreLogic.constants.interfaces.Order;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.game_entities.orders.*;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidArgumentException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidCommandException;
import com.APP.Project.UserCoreLogic.responses.CommandResponses;

/**
 * This class contains the functionality to map CommandResponses to Order
 *
 * @author Rupal Kapoor
 * @version 1.0
 */
public class OrderMapper {
    /**
     * This method is used to map CommandResponses to Order object.
     * This method is static and returns a new
     * order instance created from the CommandResponses class.
     *
     * @param p_commandResponse This command object is received at the UserCoreLogic class which represents the user command input.
     * @param p_player          This is the player object which has issued the order.
     * @return This is the value of the new order created from user command response.
     * @throws EntityNotFoundException  This is thrown if the target entity not found.
     * @throws InvalidCommandException  This is thrown if the required command values are not present.
     * @throws InvalidArgumentException This is thrown if the argument can not be converted to the required type.
     */
    public Order toOrder(CommandResponses p_commandResponse, Player p_player)
            throws EntityNotFoundException,
            InvalidCommandException,
            InvalidArgumentException {
        OrderTypes l_orderType = OrderTypes.valueOf(p_commandResponse.getHeadCommand().toLowerCase());
        try {
            if (l_orderType == OrderTypes.advance) {
                return new AdvanceOrder(p_commandResponse.getCommandValues().get(0), p_commandResponse.getCommandValues().get(1), p_commandResponse.getCommandValues().get(2), p_player);
            } else if (l_orderType == OrderTypes.airlift) {
                return new AirliftOrder(p_commandResponse.getCommandValues().get(0), p_commandResponse.getCommandValues().get(1), p_commandResponse.getCommandValues().get(2), p_player);
            } else if (l_orderType == OrderTypes.blockade) {
                return new BlockadeOrder(p_commandResponse.getCommandValues().get(0), p_player);
            } else if (l_orderType == OrderTypes.bomb) {
                return new BombOrder(p_commandResponse.getCommandValues().get(0), p_player);
            } else if (l_orderType == OrderTypes.deploy) {
                return new DeployOrder(p_commandResponse.getCommandValues().get(0), p_commandResponse.getCommandValues().get(1), p_player);
            } else if (l_orderType == OrderTypes.negotiate) {
                return new NegotiateOrder(p_player, p_commandResponse.getCommandValues().get(0));
            }
            // If not known order, throws an exception.
        } catch (ArrayIndexOutOfBoundsException p_e) {
            // If not handled here, it will throw a InvalidCommandException.
        }
        throw new InvalidCommandException("Invalid command!");
    }
}
