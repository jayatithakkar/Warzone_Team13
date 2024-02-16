package com.APP.Project.UserCoreLogic.exceptions;

/**
 * Custom exception class representing an invalid input scenario entered by user.
 * It extends the UserCoreLogicException class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class InvalidInputException extends UserCoreLogicException {

     /**
     * Constructs an InvalidInputException with the specified detail message.
     *
     * @param p_exceptionMessage detailed Exception message
     */
     public InvalidInputException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     /**
     * Constructs an InvalidInputException with the specified detail message and cause.
     *
     * @param p_exceptionMessage detailed Exception message
     * @param p_exceptionCause detailed cause that causes the current exception
     */
     public InvalidInputException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
