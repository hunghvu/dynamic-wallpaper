package model;

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
public class RandomFromFolder extends RandomWpUpdate {

  /**
   * List of file path.
   */
  public static FileArray myFileArray = null;

  /**
   * Picture path (randomly chosen).
   */
  private static File myPicturePath;
  

  /**
   * Constructor.
   * 
   * @param theFileArray picture folder path
   */
  public RandomFromFolder(final FileArray theFileArray) {

    myFileArray = theFileArray;
    autoUpdate(MY_RUN_SIGNAL);

  }
  
  public RandomFromFolder(final RandomFromNet theNet) {
    myFileArray = null;
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
            
            System.out.println(myPicturePath);

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
      
      // Set minute checker to default state (04/09)
      myRecentMinute = -1;

    }

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
