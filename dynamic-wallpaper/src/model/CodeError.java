package model;

/**
 * Return error code in validating given "time".
 * This is used to trigger proper listener action.
 * @author Hung Vu
 *
 */
public enum CodeError {
  
  // Error in TimeList.
  TIME_OUT_OF_RANGE, TIME_INVALID_CHAR, TIME_INVALID_FORMAT, 
  
  // Error in RandomFromNet.
  NET_NO_CONNECTION;

}

// Temporary, as of 09/04/20:
// Class: Done Recomment.
// Class: Done Checkstyle.
// Class: Done PMD.