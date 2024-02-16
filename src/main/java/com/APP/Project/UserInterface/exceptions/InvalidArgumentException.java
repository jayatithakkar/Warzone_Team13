package com.APP.Project.UserInterface.exceptions;

/** 
 * This exception is thrown when an invalid argument / value is passed.
 * It extends the RuntimeException class.
 * 
 * @author Rikin Dipakkumar Chauhan
 */
public class InvalidArgumentException extends RuntimeException {
     /**
      * Constructs an InvalidArgumentException with the specified detail message.
      *
      * @param p_exceptionMessage detailed Exception message
      */
     public InvalidArgumentException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     /**
      * Constructs an InvalidArgumentException with the specified detail message and
      * cause.
      *
      * @param p_exceptionMessage detailed Exception message
      * @param p_exceptionCause   detailed cause that causes the current exception
      */
     public InvalidArgumentException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
