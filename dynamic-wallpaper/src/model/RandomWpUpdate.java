package model;

import java.time.LocalTime;
import java.util.Timer;

public abstract class RandomWpUpdate {

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

  public RandomWpUpdate() {
    super();
  }
  
  public abstract void autoUpdate (boolean theSignal);

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

}