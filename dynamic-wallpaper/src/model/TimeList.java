package model;

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
@SuppressWarnings({"PMD.AssignmentToNonFinalStatic", "PMD.OnlyOneReturn"})
public class TimeList {
  /**
   * List of time.
   */
  //Use List instead of ArrayList to avoid LooseCoupling.
  //This makes easier to change the underlying implementation.
  //E.g: ArrayList is List, but List is not ArrayList => need to change return type.
  private static List<String> myTimeList;

  private PropertyChangeSupport myTimeNotifier;

  /**
   * Constructor.
   */
  public TimeList() {

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
      myTimeNotifier.firePropertyChange(
          "myTimeList",
          myTimeList,
          myTimeList.add(theTime));
      Collections.sort(myTimeList);
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
