package com.APP.Project.UserCoreLogic.constants.interfaces;

import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;

import java.util.List;

/**
 * Defines the structure for commands executed directly with values instead of
 * key-value pairs.
 * <p>
 * Commands implementing this interface are intended to perform operations based
 * solely on a list of values,
 * which can vary in number. These types of commands are especially useful for
 * actions that do not require
 * complex parameters or where the parameters do not naturally map to key-value
 * pairs. Implementations
 * should handle both the case of receiving multiple values and no values at
 * all, ensuring robustness and
 * flexibility in command execution.
 * </p>
 *
 * @author Bhoomiben Bhatt
 * @version 2.0
 */
public interface StandaloneCommand {
     /**
      * Executes the command using the provided list of string values as input.
      * <p>
      * This method should encapsulate the logic necessary for performing the
      * command's function based on
      * the provided input values. Implementers must ensure that the command can
      * handle varying numbers of
      * input values gracefully, including the scenario where no values are provided.
      * The method returns a
      * string intended for user feedback, indicating the outcome of the command
      * execution.
      * </p>
      *
      * @param p_commandValues A list of string values provided as input to the
      *                        command. This list can be empty but not null.
      * @return A string representing the result of the command execution, intended
      *         for display to the user.
      * @throws UserCoreLogicException If an error occurs during command execution,
      *                                such as invalid input or
      *                                other conditions that prevent the command from
      *                                completing successfully.
      */
     String execute(List<String> p_commandValues) throws UserCoreLogicException;
}
