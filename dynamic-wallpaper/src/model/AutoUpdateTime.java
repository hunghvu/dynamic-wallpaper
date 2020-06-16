package model;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import java.io.File;
import java.time.LocalTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import view.MiddleSettingPanel;
import view.RightTextPanel;

/**
 * This class automatically updates preview and desktop wallpaper.
 * 
 * @author Hung Vu
 *
 */
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.LawOfDemeter",
    "PMD.AssignmentToNonFinalStatic", "PMD.CommentSize", "PMD.DataflowAnomalyAnalysis",
    "PMD.DoNotCallGarbageCollectionExplicitly", "PMD.NullAssignment"})
public class AutoUpdateTime {

  /**
   * Change wallpaper flag.
   */
  public static final int MY_SPI_WALLPAPER = 0x0014;

  /**
   * Send winini change.
   */
  public static final int MY_SENDCHANGE = 1;

  /**
   * Unused parameter.
   */
  public static final int MY_UNUSED = 0;

  /**
   * Program refresh interval (500ms).
   */
  private static final int MY_INTERVAL = 500;

  /**
   * Default signal to start the program.
   */
  private static final boolean MY_RUN_SIGNAL = true;
  
  /**
   * Length of minute and hour string.
   */
  private static final int MY_FORMAT_LENGTH = 2;
  
  /**
   * Timer for auto run.
   */
  private static Timer myAutoRun;

  /**
   * Store local time get form the machine.
   */
  private static LocalTime myTime;

  /**
   * List of file path.
   */
  public static FileArray myFileArray = null;

  /**
   * Picture path (randomly chosen).
   */
  private static File myPicturePath;

  /**
   * Default minute for condition checker. <br>
   * The wallpaper can only be changed once per minute. <br>
   * This variable will be reassigned after the program start.
   */
  private static int myRecentMinute = -1;
  
  /**
   * Interface (native library mapping) to change wallpaper.
   * 
   * @author Hung Vu
   *
   */
  private interface JnaWallpaper extends StdCallLibrary {
    /**
     * Field to load USER32 library. loadLibrary deprecated, use load instead
     */
    JnaWallpaper INSTANCE = (JnaWallpaper) Native.load(
        
        "user32", 
        JnaWallpaper.class
        
        );

    /**
     * Map function (Based on JNA/Microsoft document).
     * @param theUiAction Action to perform on UI
     * @param theUiParam Not used
     * @param thePath Path of a picture for desktop wallpaper
     * @param theFWinIni Not used
     * @return a boolean, not used
     */
    //This is function mapping, ignore warning.
    @SuppressWarnings("PMD.MethodNamingConventions")
    boolean SystemParametersInfoA(
        
        int theUiAction, 
        int theUiParam, 
        String thePath, 
        int theFWinIni

        );

  }

  /**
   * Constructor.
   * 
   * @param theFolderDir picture folder path
   */
  public AutoUpdateTime(final File theFolderDir) {

    myFileArray = new FileArray(theFolderDir);
    autoUpdate(MY_RUN_SIGNAL);

  }

  /**
   * Auto update preview panel based on given time (add change desktop wallpaper
   * later).
   * 
   * @param theSignal indicate whether a program can be run or not (new thread)
   *                  <br>
   *                  true creates an instance of thread, run program based on
   *                  timer <br>
   *                  false cancel current timer (stop thread).
   */
  public void autoUpdate(final boolean theSignal) {

    // Run if theSignal is true, else cancel old thread.
    if (theSignal) {

      // Temporary variable to randomize picture.
      final Random randPicture = new Random();      
      
      // Choose random picture from path list.
      myPicturePath = myFileArray.getFileArray()
          [randPicture.nextInt(myFileArray.getFileArray().length)];      
      
      // Set timer for changing preview panel.
      myAutoRun = new Timer();
      myAutoRun.schedule(new TimerTask() {

        @Override
        public void run() {

          // Get local time.
          myTime = LocalTime.now();

          // In the same minute, only change 1 time.
          // myRecentMinute must be different compared to 
          // real time from getMinute() to proceed.
          if (compareTime(myTime) && myRecentMinute != myTime.getMinute()) {           

            // Set desktop wallpaper.
            JnaWallpaper.INSTANCE.SystemParametersInfoA(
                
                MY_SPI_WALLPAPER, 
                MY_UNUSED, 
                myPicturePath.toString(),
                MY_SENDCHANGE
                
                );
            
            // Get string from of hour and minute.
            StringBuilder hour = new StringBuilder();
            StringBuilder minute = new StringBuilder();    
            hour.append(myTime.getHour());
            minute.append(myTime.getMinute());
            
            // Format hour (HH).
            if (hour.length() != MY_FORMAT_LENGTH) {
              hour.append(0);
              hour.reverse();
            }
                      
            // Format minute (MM).
            if (minute.length() != MY_FORMAT_LENGTH) {
              minute.append(0);
              minute.reverse();
            }
            
            // Print message when wallpaper is changed.
            RightTextPanel.textSetter(
                
                "LOG", 
                hour + ":" + minute + " - "
                + "Change wallpaper successfully!"
                    
                );
            
            // Randomize picture from path list again.
            myPicturePath = myFileArray.getFileArray()
                [randPicture.nextInt(myFileArray.getFileArray().length)];

            // Display picture in the preview panel.
            MiddleSettingPanel.displayPreview(myPicturePath);            
            
            // Set a latest real minute to myRecentMinute (E.g: 2 mins into 1-min-myRecentMinute)
            myRecentMinute = myTime.getMinute();
            
            //Set to null to persuade garbage collection.
            hour = null;
            minute = null;
            
            //Collect garbage.
            System.gc();
            
          }

        }
      }, MY_UNUSED, MY_INTERVAL);

    } else {

      // Cancel the old thread before starting a new one.
      myAutoRun.cancel();

    }

  }

  /**
   * Compare the local time with time in given TIME LIST.
   * 
   * @param theLocalTime real clock time at the moment
   * @return whether real time is in time list <br>
   *         true, it's in <br>
   *         false, otherwise
   * 
   */
  private boolean compareTime(final LocalTime theLocalTime) {
    
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

  // Class: Done Comment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore Method naming conventions warning in interface.
  // Ignore Assignment to non final static. (Object is initialized only once)
  // Ignore Comment size.
  // Ignore LoD.
  // Ignore Redundant field initializer.
  // Ignore checkstyle warning in interface.
  // Ignore DataflowAnomalyAnalysis (contain = false in compareTime).
  // Ignore null assignment.
  // Ignore do not call gc explicitly.

}
