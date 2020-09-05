package controller;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import model.CodeError;
import model.CodeTimeAction;
import model.FileArray;
import model.RandomFromFolder;
import model.RandomFromNet;
import model.TimeList;
import view.NorthCheckListPanel;
import view.RightTextPanel;
import view.WindowFrame;

/**
 * This class is the bridge for interaction between view and model.
 * @author Hung Vu
 *
 */
@SuppressWarnings({"serial"})
public class Controller implements PropertyChangeListener {

  /**
   * List of user provided time.
   */
  private static final TimeList MY_TIME = new TimeList();
  
  /**
   * Background process to change wallpaper using picture from Internet.
   */
  private static final RandomFromNet myNetRun = new RandomFromNet();
  
  /**
   * String message to print when an error occurs.
   */
  private static final String MY_TRY_AGAIN = "Please try again.";
  
  /**
   * Background process to change wallpaper using picture in selected folder.
   */
  private static RandomFromFolder myFolderRun;
  
  /**
   * Picture files inside a folder.
   */
  private static FileArray myFile;

  /**
   * The GUI frame.
   */
  private static WindowFrame myFrame;
  
  /**
   * The signal message to perform display proper text (LOG).
   */
  private static final String MY_TEXT_LOG = "LOG";
  
  /**
   * The signal message to perform display proper text (TIME LIST).
   */
  private static final String MY_TEXT_TIME_LIST = "TIME_LIST";
  
  /**
   * Constructor.
   * This will allow the GUi run on its own EDT thread.
   */
  public Controller() {
    
    // The controller is a listener for TimeList.
    MY_TIME.addListener(this);
    
    // The controller is a listener for RandomFromNet.
    myNetRun.addListener(this);
    
    // Start GUI thread.
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {

        myFrame = new WindowFrame();

      }

    });
//    myNetRun.getRegularPicture(); //for testing
  }
  
  /**
   * Create an array of files in the given folder.
   * @param theDir the path to a picture folder
   */
  public static void createFileArray(final File theDir) {

    myFile = new FileArray(theDir);

  }
  
  /**
   * Start the process of changing wallpaper using
   * pictures in the given folder.
   */
  public static void startBackgroundFolder() {
    
    // Stop old instance of wallpaper and preview setter timer before creating a new
    // one. Set to null to persuade GC.
    if (myFolderRun != null) {

      myFolderRun.autoUpdate(false);
      myFolderRun = null;

    }

    // Garbage collection.
    System.gc();

    // Run the program automatically.
    myFolderRun = new RandomFromFolder(myFile);
    
  }
  
  /**
   * Stop the process of changing wallpaper using
   * pictures in the given folder.
   */
  public static void stopBackgroundFolder() {
    
    myFolderRun.autoUpdate(false);
    
  }
  
  /**
   * Start the process of changing wallpaper using
   * pictures taken from the Internet.
   */
  public static void startBackgroundNet() {

  }
  
  /**
   * Stop the process of changing wallpaper using
   * pictures taken from the Internet.
   */
  public static void stopBackgroundNet() {

  }
  
  /**
   * Get the state of folder-based wallpaper updater process.
   * @return null means the process is not running, otherwise it is running.
   */
  public static RandomFromFolder getAuto() {
    
    return myFolderRun;
    
  }
  
  /**
   * Reset the current time list.
   */
  public static void resetTimeList() {
    
    MY_TIME.clearTimeList();
    
  }
  
  /**
   * Add new time to the current time list.
   * @param theHour the desired "hour".
   * @param theMinute the desired "minute".
   */
  public static void setTime(final String theHour, final String theMinute) {
    
    MY_TIME.timeValidator(theHour, theMinute);
    
  }
  
  /**
   * Delete a specific time from the current time list.
   * @param theHour the desired "hour".
   * @param theMinute the desired "minute".
   */
  public static void deleteTime(final String theHour, final String theMinute) {
    
    MY_TIME.deleteTime(theHour + ":" + theMinute);
    
  }
  
  /**
   * Empty current time list.
   */
  public static void emptyTime() {
    
    MY_TIME.isEmpty();
    
  }
  
  /**
   * The action to perform when data (in model) to display
   * a result of interaction with data in model.
   */
  @Override
  public void propertyChange(final PropertyChangeEvent evt) {

    if (evt.getNewValue().equals(CodeTimeAction.ADD_SUCCESS)) {
      // Successfully add time to the list.
      
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, "Time list is succesfully updated.");

      // Update requirement status.
      NorthCheckListPanel.requirementSetter(21);

    } else if (evt.getNewValue().equals(CodeTimeAction.ADD_FAIL)) {
      // Fail to add time to the list.
      
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, 
          "Cannot add duplicate time! \n" + MY_TRY_AGAIN);
      
    } else if (evt.getNewValue().equals(CodeError.TIME_OUT_OF_RANGE)) {
      // Fail to add time to the list (time is out of range).
      
      RightTextPanel.textSetter(MY_TEXT_LOG, "Time is out of range! \n" + MY_TRY_AGAIN);
    
    } else if (evt.getNewValue().equals(CodeError.TIME_INVALID_CHAR)) {
      // Fail to add time to the list (invalid characters).
      
      RightTextPanel.textSetter(MY_TEXT_LOG, "Invalid character! \n" + MY_TRY_AGAIN);
      
    } else if (evt.getNewValue().equals(CodeError.TIME_INVALID_FORMAT)) {
      // Fail to add time to the list (invalid format).
      RightTextPanel.textSetter(MY_TEXT_LOG,
          "Input should contains only \n" + "2 characters per field! \n" + MY_TRY_AGAIN);
      
    } else if (evt.getNewValue().equals(CodeTimeAction.DELETE_SUCCESS)) {
      // Successfully delete time from the list.
      
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, "Delete time successfully.");
      
    } else if (evt.getNewValue().equals(CodeTimeAction.DELETE_FAIL)) {
      // Fail to delete time from the list.
      
      RightTextPanel.textSetter(MY_TEXT_LOG, "Provided time is not in the list.");
      
    } else if (evt.getNewValue().equals(CodeTimeAction.EMPTY_TRUE)) {
      // After empty the time list.
      
      NorthCheckListPanel.requirementSetter(20);
      
    } else if (evt.getNewValue().equals(CodeError.NET_NO_CONNECTION)) {
      // Action when there is no Internet connection.
      
      RightTextPanel.textSetter(MY_TEXT_LOG, "No Internet Connection. \n" 
          + MY_TRY_AGAIN);
      
      // Stop the process.
      Controller.stopBackgroundNet();
      
    }
  }
}
