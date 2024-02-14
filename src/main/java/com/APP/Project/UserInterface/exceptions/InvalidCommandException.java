package com.APP.Project.UserInterface.exceptions;

/**
 * Exception thrown when an invalid command is encountered.
 * It extends the RuntimeException class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class InvalidCommandException extends RuntimeException{
     /**
      * Constructs an InvalidCommandException with the specified detail message.
      *
      * @param p_exceptionMessage detailed Exception message
      */
     public InvalidCommandException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     /**
      * Constructs an InvalidCommandException with the specified detail message and
      * cause.
      *
      * @param p_exceptionMessage detailed Exception message
      * @param p_exceptionCause   detailed cause that causes the current exception
      */
     public InvalidCommandException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
