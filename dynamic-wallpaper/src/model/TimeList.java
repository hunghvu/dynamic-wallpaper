package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class construct list of time and manipulate it.
 *
 * @author Hung Vu
 *
 */
@SuppressWarnings({"serial", "PMD.AssignmentToNonFinalStatic"})
public class TimeList {
  
  /**
   * String-name of the field myTimeList.
   */
  private static final String MY_TIME_LIST_NAME = "myTimeList";
  
  // Add notifier (02/09)
  /**
   * Change notifier for TimeList.
   */
  private final PropertyChangeSupport myTimeNotifier;
  
  /**
   * List of time.
   */
  // Use List instead of ArrayList to avoid LooseCoupling.
  // This makes easier to change the underlying implementation.
  // E.g: ArrayList is List, but List is not ArrayList => need to change return
  // type.
  private static List<String> myTimeList;

  /**
   * Constructor.
   */
  public TimeList() {

    // Initialize notifier(02/09)
    myTimeNotifier = new PropertyChangeSupport(this);

    // Use diamond operator
    myTimeList = new ArrayList<>();

  }

  /**
   * Delete the a given time if it appears in the list.
   *
   * @param theTime time given by a user.
   */
  public void deleteTime(final String theTime) {

    // Remove when the time is in the list.
    if (myTimeList.contains(theTime)) {

      myTimeList.remove(theTime);
      // Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeChange.DELETE_SUCCESS);

    } else {

      // Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeChange.DELETE_FAIL);

    }

  }

  /**
   * Clear data of time list.
   */
  public void clearTimeList() {

    myTimeList.clear();

    // Ignore old object (02/09)
    myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeChange.CLEAR);

  }

  /**
   * Add listener to this object.
   * 
   * @param theListener A listener.
   */
  public void addListener(final PropertyChangeListener theListener) {

    myTimeNotifier.addPropertyChangeListener(theListener);

  }

  /**
   * Provide the time list.
   * 
   * @return list of time.
   */
  public static List<String> getTimeList() {
    
    return myTimeList;
    
  }

  /**
   * Check whether the time list is empty.
   */
  public void isEmpty() {

    if (myTimeList.isEmpty()) {
      
      // Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeChange.EMPTY_TRUE);
      
    } else {
      
      // Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeChange.EMPTY_FALSE);
      
    }

  }
  
  /**
   * Validate if the given time is usable.
   * @param theHour the desired "hour".
   * @param theMinute the desired "time".
   */
  public void timeValidator(final String theHour, final String theMinute) {
    
    // See if string is in ## format (length == 2).
    if (theHour.length() == 2 && theMinute.length() == 2) {

      try {

        // Turn hour and minute from string to int. If fail then throws exception.
        final int myHourValue = Integer.parseInt(theHour);
        final int myMinuteValue = Integer.parseInt(theMinute);

        // Hour (00-23), minute (00-59).
        if (myHourValue >= 0 && myHourValue <= 23 && myMinuteValue >= 0 && myMinuteValue <= 59) {

          addTime(theHour, theMinute);

        } else { // Time is out of range.

          // Ignore old object (02/09)
          myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeTimeError.OUT_OF_RANGE);

        }

      } catch (NumberFormatException e) {

        // Ignore old object (02/09)
        myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeTimeError.INVALID_CHAR);

        // Throw exception when string can't be parsed.
        // Invalid input format.

      }

    } else { // Invalid input format: length != 2.

      // Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeTimeError.INVALID_FORMAT);

    }

  }

  /**
   * String representation of time list object.
   */
  @Override
  public String toString() {

    // Format the element in the form: element \n\n element.
    return String.join("\n\n", myTimeList);

  }
  
  /**
   * Add time to the list.
   *
   * @param theTime time given by a user.
   */
  private void addTime(final String theHour, final String theMinute) {

    final String time = theHour + ":" + theMinute;
    
    // Ensure the time can appear only once.
    if (myTimeList.contains(time)) {

      // Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeChange.ADD_FAIL);

    } else {

      myTimeList.add(time);
      Collections.sort(myTimeList);

      // Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(MY_TIME_LIST_NAME, null, CodeChange.ADD_SUCCESS);

    }

  }
  
  // Temporary, as of 09/04/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: PMD Done.
  // Ignore AssignmentToNonFinalStatic (Object is initialized only once).
  
  //Fix Only one return.
}
