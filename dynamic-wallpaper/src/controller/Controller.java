package controller;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import model.RandomInFolder;
import model.TimeErrorCode;
import model.ChangeCode;
import model.FileArray;
import model.TimeList;
import view.NorthCheckListPanel;
import view.RightTextPanel;
import view.WindowFrame;

public class Controller implements PropertyChangeListener{
  
  // 02/09
  private static final TimeList MY_TIME = new TimeList();
  
  private static RandomInFolder myAutoRun;
  private static FileArray myFile;
  
  private WindowFrame myFrame;

  private static final String MY_TEXT_LOG = "LOG";

  private  static final String MY_TEXT_TIME_LIST = "TIME_LIST";

  public Controller() { 
    MY_TIME.addListener(this);
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {

        myFrame = new WindowFrame();

      }

    });
  }
  
  public static void createFileArray(File theDir) {
    
    myFile = new FileArray(theDir);
//    myFile.addListener(this);
    
  }
  
  public static void startBackgroundChanger() {
    // Stop old instance of wallpaper and preview setter timer before creating a new
    // one. Set to null to persuade GC.
    if (myAutoRun != null) {

      myAutoRun.autoUpdate(false);
      myAutoRun = null;

    }
    
    // Garbage collection.
    System.gc();
    
    // Run the program automatically.
    myAutoRun = new RandomInFolder(myFile);
  }
  
  public static void stopBackgroundChanger() {
    myAutoRun.autoUpdate(false);
  }
  
  public static RandomInFolder getAuto() {
    return myAutoRun;
  }
  
  public static void resetTimeList() {
    MY_TIME.clearTimeList();
  }
  
  public static void setTime(String theHour, String theMinute) {
    MY_TIME.timeValidator(theHour, theMinute);
  }
  
  public static void deleteTime(String theHour, String theMinute) {
    MY_TIME.deleteTime(theHour + ":" + theMinute);
  }
  
  public static void emptyTime() {
    MY_TIME.isEmpty();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // TODO Auto-generated method stub
    if(evt.getNewValue().equals(ChangeCode.ADD_SUCCESS)) {
      
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, "Time list is succesfully updated.");
  
      // Update requirement status.
      NorthCheckListPanel.requirementSetter(21);
      
    } else if (evt.getNewValue().equals(ChangeCode.ADD_FAIL)){
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST , "Cannot add duplicate time! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(TimeErrorCode.OUT_OF_RANGE)) {
      RightTextPanel.textSetter(MY_TEXT_LOG , "Time is out of range! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(TimeErrorCode.INVALID_CHAR)) {
      RightTextPanel.textSetter(MY_TEXT_LOG, "Invalid character! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(TimeErrorCode.INVALID_FORMAT)) {
      RightTextPanel.textSetter(MY_TEXT_LOG, "Input should contains only \n"
      + "2 characters per field! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(ChangeCode.DELETE_SUCCESS)) {
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, "Delete time successfully.");
    } else if (evt.getNewValue().equals(ChangeCode.DELETE_FAIL)) {
      RightTextPanel.textSetter(MY_TEXT_LOG, "Provided time is not in the list.");
    } else if (evt.getNewValue().equals(ChangeCode.EMPTY_TRUE)) {
      NorthCheckListPanel.requirementSetter(20);
    }
  }
}
