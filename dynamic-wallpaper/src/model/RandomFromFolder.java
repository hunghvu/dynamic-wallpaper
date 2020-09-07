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
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "PMD.LawOfDemeter", "PMD.AssignmentToNonFinalStatic", "PMD.CommentSize",
    "PMD.DataflowAnomalyAnalysis", "PMD.UnusedAssignment",
    "PMD.DoNotCallGarbageCollectionExplicitly", "PMD.NullAssignment", 
    "PMD.ExcessiveMethodLength"})
//Ignore comment size (GPL copyright notice).
public class RandomFromFolder extends AbstractUpdater {

  /**
   * List of file path.
   */
  public static FileArray myFileArray;

  /**
   * Picture path (randomly chosen).
   */
  private static File myPicturePath;

  /**
   * Indicate whether the process is running.
   */
  private static boolean myRunFlag;

  /**
   * Constructor.
   * 
   * @param theFileArray picture folder path
   */
  public RandomFromFolder(final FileArray theFileArray) {

    super();
    myFileArray = theFileArray;
    autoUpdate(MY_RUN_SIGNAL);

  }

  /**
   * Get running state.
   * 
   * @return true if the process is running, false otherwise.
   */
  public static boolean isRunning() {
    return myRunFlag;
  }

  @Override
  public void autoUpdate(final boolean theSignal) {

    // Run if theSignal is true, else cancel old thread.
    if (theSignal) {

      // Set run flag.
      myRunFlag = true;

      // Temporary variable to randomize picture.
      final Random randPicture = new Random();

      // Choose random picture from path list.
      myPicturePath = myFileArray
          .getFileArray()[randPicture.nextInt(myFileArray.getFileArray().length)];

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

                MY_SPI_WALLPAPER, MY_UNUSED, myPicturePath.toString(), MY_SENDCHANGE

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

                "LOG", hour + ":" + minute + " - " + "Change wallpaper successfully!"

            );

            // Randomize picture from path list again.
            myPicturePath = myFileArray
                .getFileArray()[randPicture.nextInt(myFileArray.getFileArray().length)];

            // Display picture in the preview panel.
            MiddleSettingPanel.displayPreview(myPicturePath);

            // Set a latest real minute to myRecentMinute (E.g: 2 mins into
            // 1-min-myRecentMinute)
            myRecentMinute = myTime.getMinute();

            // Set to null to persuade garbage collection.
            hour = null;
            minute = null;

            // Collect garbage.
            System.gc();

          }

        }
        // End of timer task.
      }, MY_UNUSED, MY_INTERVAL);

      // End of schedule.

    } else {

      // Set run flag.
      myRunFlag = false;

      // Cancel the old thread before starting a new one.
      myAutoRun.cancel();

      // Set minute checker to default state (04/09)
      myRecentMinute = -1;

    }

  }

  // Done, as of 09/07/20:
  // Class: Done Comment.
  // Class: Done Checkstyle.
  // Class: Done PMD.

  // Ignore LoD.
  // Ignore Assignment to non final static.
  // Ignore Comment size.
  // Ignore DataflowAnomalyAnalysis (contain = false in compareTime).
  // Ignore Unused assignment.
  // Ignore Do not call GC explicitly.
  // Ignore Null assignment.
  // Ignore ExcessiveMethodLength.

  // Fix Redundant field initializer.
}
