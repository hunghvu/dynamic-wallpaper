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

package model;

import java.time.LocalTime;
import java.util.Timer;

/**
 * This is a base class to provide wallpaper update functionality.
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "PMD.DataflowAnomalyAnalysis", "PMD.AtLeastOneConstructor",
    "PMD.DoNotCallGarbageCollectionExplicitly", "PMD.CommentSize" })
//Ignore comment size (GPL copyright notice).
public abstract class AbstractUpdater {

  /**
   * Change wallpaper flag.
   */
  protected static final int MY_SPI_WALLPAPER = 0x0014;
  /**
   * Send winini change.
   */
  protected static final int MY_SENDCHANGE = 1;
  /**
   * Unused parameter.
   */
  protected static final int MY_UNUSED = 0;
  /**
   * Program refresh interval (500ms).
   */
  protected static final int MY_INTERVAL = 500;
  /**
   * Default signal to start the program.
   */
  protected static final boolean MY_RUN_SIGNAL = true;
  /**
   * Length of minute and hour string.
   */
  protected static final int MY_FORMAT_LENGTH = 2;
  /**
   * Timer for auto run.
   */
  protected static Timer myAutoRun;
  /**
   * Store local time get form the machine.
   */
  protected static LocalTime myTime;
  /**
   * Default minute for condition checker. <br>
   * The wallpaper can only be changed once per minute. <br>
   * This variable will be reassigned after the program start.
   */
  protected static int myRecentMinute = -1;
  
  /**
   * Auto update preview panel based on given timestamp 
   * and change desktop wallpaper.
   * 
   * @param theSignal indicate whether a program can be run or not (new thread)
   *                  <br>
   *                  true creates an instance of thread, run program based on
   *                  timer <br>
   *                  false cancel current timer (stop thread).
   */
  public abstract void autoUpdate(boolean theSignal);

  /**
   * Compare the local time with time in given TIME LIST.
   * 
   * @param theLocalTime real clock time at the moment
   * @return whether real time is in time list <br>
   *         true, it's in <br>
   *         false, otherwise
   * 
   */
  protected boolean compareTime(final LocalTime theLocalTime) {
    
    //Default value to return
    boolean contain = false;
    
    // Loop through the TIME LIST.
    for (final String time : TimeList.getTimeList()) {
  
      // String representation format : HH:MM
      // subString 0-2 to get HH.
      // subString 3-end to get MM.
      final int myHourValue = Integer.parseInt(time.substring(0, 2));
      final int myMinuteValue = Integer.parseInt(time.substring(3));
  
      // Perform comparison.
      if (myHourValue == theLocalTime.getHour() && myMinuteValue == theLocalTime.getMinute()) {
        
        //If time is in the list, return value is set to true, then break the loop.
        contain = true;
        break;
        
      }
  
    }
    
    // Call gc to collect LocalTime and String (from subString) objects. 
    // This is required based on observation in VisualVM.
    System.gc();
    return contain;
  
  }
  
  // Done, as of 09/07/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  
  // Ignore Data flow anomaly analysis.
  // Ignore At least one constructor.
  // Ignore Do not call GC explicitly.

}