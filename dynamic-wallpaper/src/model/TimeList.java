package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import view.NorthCheckListPanel;
import view.RightTextPanel;

/**
 * This class construct list of time and manipulate it.
 *
 * @author Hung Vu
 *
 */
@SuppressWarnings({"PMD.AssignmentToNonFinalStatic", "PMD.OnlyOneReturn"})
public class TimeList {
  /**
   * List of time.
   */
  //Use List instead of ArrayList to avoid LooseCoupling.
  //This makes easier to change the underlying implementation.
  //E.g: ArrayList is List, but List is not ArrayList => need to change return type.
  private static List<String> myTimeList;
  
  //add notifier (02/09)
  /**
   * Change notifier for TimeList
   */
  private PropertyChangeSupport myTimeNotifier;

  /**
   * Constructor.
   */
  public TimeList() {
    
    //Initialize notifier(02/09)
    myTimeNotifier = new PropertyChangeSupport(this);
    
    //Use diamond operator
    myTimeList = new ArrayList<>();

  }

  /**
   * Add time to the list.
   *
   * @param theTime time given by a user.
   * @return true when add is success, false otherwise.
   */
  private void addTime(final String theHour, final String theMinute) {
    
    String time = theHour + ":" + theMinute;
    //Ensure the time can appear only once.
    if (myTimeList.contains(time)) {
      
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
         null,
          CodeChange.ADD_FAIL);
      

    } else {
      
      myTimeList.add(time);
      Collections.sort(myTimeList);
      
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          null,
          CodeChange.ADD_SUCCESS);


    }

  }

  /**
   * Delete the a given time if it appears in the list.
   *
   * @param theTime time given by a user.
   * @return whether a deletion is success: <br>
   *         false is fail <br>
   *         true is success
   */
  public void deleteTime(final String theTime) {

    //Remove when the time is in the list.
    if (myTimeList.contains(theTime)) {
      
      myTimeList.remove(theTime);
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          null,
          CodeChange.DELETE_SUCCESS);


    } else {
      
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          null,
          CodeChange.DELETE_FAIL);


    }

  }

  /**
   * Clear data of time list.
   */
  public void clearTimeList() {
    
    myTimeList.clear();
    
    //Ignore old object (02/09)
    myTimeNotifier.firePropertyChange(
        "myTimeList",
        null,
        CodeChange.CLEAR);

  }
  
  /**
   * Add listener to this object.
   * @param theListener A listener.
   */
  public void addListener (PropertyChangeListener theListener) {

    myTimeNotifier.addPropertyChangeListener(theListener);

  }

  /**
   * Provide the time list.
   * @return list of time.
   */
  public static List<String> getTimeList() {
    return myTimeList;
  }

  /**
   * Check whether the time list is empty.
   * @return true means empty <br>
   *         false otherwise
   */
  public void isEmpty() {

    if(myTimeList.isEmpty()) {
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          null,
          CodeChange.EMPTY_TRUE);
    } else {
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          null,
          CodeChange.EMPTY_FALSE);
    }

  }
  
  public void timeValidator(String theHour, String theMinute) {
 // See if string is in ## format (length == 2).

    if (theHour.length() == 2 && theMinute.length() == 2) {

      try {

        // Turn hour and minute from string to int. If fail then throws exception.
        int myHourValue = Integer.parseInt(theHour);
        int myMinuteValue = Integer.parseInt(theMinute);

        // Hour (00-23), minute (00-59).
        if (myHourValue >= 0 && myHourValue <= 23 && myMinuteValue >= 0 && myMinuteValue <= 59) {

          addTime(theHour, theMinute);
          

        } else { // Time is out of range.

          //Ignore old object (02/09)
          myTimeNotifier.firePropertyChange(
              "myTimeList",
              null,
              CodeTimeError.OUT_OF_RANGE);


        }


      } catch (NumberFormatException e) {   
        
        //Ignore old object (02/09)
        myTimeNotifier.firePropertyChange(
            "myTimeList",
            null,
            CodeTimeError.INVALID_CHAR);
        
        // Throw exception when string can't be parsed. 
        // Invalid input format.
        

//

      }

    } else { // Invalid input format: length != 2.
      
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          null,
          CodeTimeError.INVALID_FORMAT);
      


    }


  }

  /**
   * String representation of time list object.
   */
  @Override
  public String toString() {

    //Format the element in the form: element \n\n element.
    return String.join("\n\n", myTimeList);

  }

  //Class: Done Recomment.
  //Class: Done Checkstyle.
  //Class: PMD Done.
  //Ignore AssignmentToNonFinalStatic (Object is initialized only once).
  //Ignore OnlyOneReturn
}
