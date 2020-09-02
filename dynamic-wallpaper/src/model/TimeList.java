package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The code that notify what the change action is.
 * @author Hung Vu
 *
 */
enum ChangeCode{
  ADD, DELETE, CLEAR;
}

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
  public boolean addTime(final String theTime) {

    //Ensure the time can appear only once.
    if (myTimeList.contains(theTime)) {

      return false;

    } else {
      
      myTimeList.add(theTime);
      Collections.sort(myTimeList);
      
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          myTimeList,
          ChangeCode.ADD);

      return true;

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
  public boolean deleteTime(final String theTime) {

    //Remove when the time is in the list.
    if (myTimeList.contains(theTime)) {
      
      myTimeList.remove(theTime);
      //Ignore old object (02/09)
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          myTimeList,
          ChangeCode.DELETE);

      return true;

    } else {

      return false;

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
        myTimeList,
        ChangeCode.CLEAR);

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
  public boolean isEmpty() {

    return myTimeList.isEmpty();

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
