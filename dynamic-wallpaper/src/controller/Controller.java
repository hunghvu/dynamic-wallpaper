/*
 * This program (Dynamic Wallpaper) changes desktop background based on provided timestamp.
 * Copyright (C) 2020  Hung Huu Vu <hunghvu2017@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

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
 * 
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "serial", "PMD.CommentSize", "PMD.LawOfDemeter",
    "PMD.AssignmentToNonFinalStatic", "PMD.CyclomaticComplexity", 
    "PMD.DoNotCallGarbageCollectionExplicitly",
    "PMD.ModifiedCyclomaticComplexity", "PMD.NullAssignment",
    "PMD.StdCyclomaticComplexity", "PMD.UnusedPrivateField"})
//Ignore comment size (GPL copyright notice).
public class Controller implements PropertyChangeListener {
  
  /**
   * String message to print when an error occurs.
   */
  private static final String MY_TRY_AGAIN = "Please try again.";


  /**
   * List of user provided time.
   */
  private static final TimeList MY_TIME = new TimeList();

  /**
   * Background process to change wallpaper using picture from Internet.
   */
  private static final RandomFromNet MY_NET_RUN = new RandomFromNet();

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
  // Ignore Checkstyle error (unused field).
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
   * Constructor. This will allow the GUi run on its own EDT thread.
   */
  public Controller() {

    // The controller is a listener for TimeList.
    MY_TIME.addListener(this);

    // The controller is a listener for RandomFromNet.
    MY_NET_RUN.addListener(this);

    // Start GUI thread.
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {

        myFrame = new WindowFrame();

      }

    });

  }

  /**
   * Create an array of files in the given folder.
   * 
   * @param theDir the path to a picture folder
   */
  public static void createFileArray(final File theDir) {

    myFile = new FileArray(theDir);

  }

  /**
   * Start the process of changing wallpaper using pictures in the given folder.
   */
  public static void startBackgroundFolder() {

    // Stop old instance of wallpaper and preview setter timer before creating a new
    // one. Set to null to persuade GC.
    if (RandomFromFolder.isRunning()) {

      myFolderRun.autoUpdate(false);
      myFolderRun = null;

    }

    // Garbage collection.
    System.gc();

    // Run the program automatically.
    myFolderRun = new RandomFromFolder(myFile);

  }

  /**
   * Stop the process of changing wallpaper using pictures in the given folder.
   */
  public static void stopBackgroundFolder() {

    myFolderRun.autoUpdate(false);

  }

  /**
   * Start the process of changing wallpaper using pictures taken from the
   * Internet.
   */
  public static void startBackgroundNet() {

    // Stop old instance of wallpaper and preview setter timer before creating a new
    // one. Set to null to persuade GC.
    if (RandomFromNet.isRunning()) {

      MY_NET_RUN.autoUpdate(false);

    }

    // Garbage collection.
    System.gc();

    // Run the process.
    MY_NET_RUN.autoUpdate(true);

  }

  /**
   * Stop the process of changing wallpaper using pictures taken from the
   * Internet.
   */
  public static void stopBackgroundNet() {

    MY_NET_RUN.autoUpdate(false);

  }

  /**
   * Get the state of folder-based wallpaper updater process.
   * 
   * @return false means the process is not running, otherwise it is running.
   */
  public static boolean isFolderRunning() {

    return RandomFromFolder.isRunning();

  }
  
  /**
   * Get the state of internet-based wallpaper updater process.
   * 
   * @return false means the process is not running, otherwise it is running.
   */
  public static boolean isNetRunning() {

    return RandomFromNet.isRunning();

  }

  /**
   * Reset the current time list.
   */
  public static void resetTimeList() {

    MY_TIME.clearTimeList();

  }

  /**
   * Add new time to the current time list.
   * 
   * @param theHour   the desired "hour".
   * @param theMinute the desired "minute".
   */
  public static void setTime(final String theHour, final String theMinute) {

    MY_TIME.timeValidator(theHour, theMinute);

  }

  /**
   * Delete a specific time from the current time list.
   * 
   * @param theHour   the desired "hour".
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
   * The action to perform when data (in model) to display a result of interaction
   * with data in model.
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

      RightTextPanel.textSetter(MY_TEXT_TIME_LIST, "Cannot add duplicate time! \n" + MY_TRY_AGAIN);

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

      RightTextPanel.textSetter(MY_TEXT_LOG, "No Internet Connection. \n" + MY_TRY_AGAIN);

      // Stop the process.
      Controller.stopBackgroundNet();

    }
    
  }
  
  // Temporary, as of 09/05/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  
  // Ignore Comment size.
  // Ignore LoD.
  // Ignore Assignment to non final static.
  // Ignore all Cyclomatic complexity related warnings.
  // Ignore Do not call GC explicitly.
  // Ignore Null assignment.
  // Ignore Unused private field.
  
  // Ignore Checkstyle warnings.
  
}
