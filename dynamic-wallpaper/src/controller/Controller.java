package controller;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import model.RandomFromFolder;
import model.CodeTimeError;
import model.RandomFromNet;
import model.RandomWpUpdate;
import model.CodeChange;
import model.FileArray;
import model.TimeList;
import view.NorthCheckListPanel;
import view.RightTextPanel;
import view.WindowFrame;

public class Controller implements PropertyChangeListener{
  
  // 02/09
  private static final TimeList MY_TIME = new TimeList();
//  private static final ClientForPicture MY_CLIENT = new ClientForPicture();
  private static RandomFromFolder myFolderRun;
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
  
  public static void stopBackgroundFolder() {
    myFolderRun.autoUpdate(false);
  }
  
  public static void startBackgroundNet() {
    
  }
  
  public static void stopBackgroundNet() {
    
  }
  
  public static RandomWpUpdate getAuto() {
    return myFolderRun;
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
    if(evt.getNewValue().equals(CodeChange.ADD_SUCCESS)) {
      
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, "Time list is succesfully updated.");
  
      // Update requirement status.
      NorthCheckListPanel.requirementSetter(21);
      
    } else if (evt.getNewValue().equals(CodeChange.ADD_FAIL)){
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST , "Cannot add duplicate time! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(CodeTimeError.OUT_OF_RANGE)) {
      RightTextPanel.textSetter(MY_TEXT_LOG , "Time is out of range! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(CodeTimeError.INVALID_CHAR)) {
      RightTextPanel.textSetter(MY_TEXT_LOG, "Invalid character! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(CodeTimeError.INVALID_FORMAT)) {
      RightTextPanel.textSetter(MY_TEXT_LOG, "Input should contains only \n"
      + "2 characters per field! \n"
      + "Please try again.");
    } else if (evt.getNewValue().equals(CodeChange.DELETE_SUCCESS)) {
      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, "Delete time successfully.");
    } else if (evt.getNewValue().equals(CodeChange.DELETE_FAIL)) {
      RightTextPanel.textSetter(MY_TEXT_LOG, "Provided time is not in the list.");
    } else if (evt.getNewValue().equals(CodeChange.EMPTY_TRUE)) {
      NorthCheckListPanel.requirementSetter(20);
    }
  }
}
