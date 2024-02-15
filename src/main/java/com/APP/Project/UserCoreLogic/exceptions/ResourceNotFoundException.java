package com.APP.Project.UserCoreLogic.exceptions;

/**
 * Exception indicating that a requested resource was not found.
 * This exception is typically thrown when a resource, such as a user or entity,
 * is expected to exist but cannot be located in the system.
 * It extends the UserCoreLogicException class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class ResourceNotFoundException extends UserCoreLogicException{

     /**
     * Constructs an ResourceNotFoundException with the specified detail message.
     *
     * @param p_exceptionMessage detailed Exception message
     */
     public ResourceNotFoundException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     /**
     * Constructs an ResourceNotFoundException with the specified detail message and cause.
     *
     * @param p_exceptionMessage detailed Exception message
     * @param p_exceptionCause detailed cause that causes the current exception
     */
     public ResourceNotFoundException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
