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

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import view.MiddleSettingPanel;
import view.RightTextPanel;

/**
 * This class get a random image from Internet and start a process of
 * automatically updating wallpaper based on given timestamp.
 * 
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.AssignmentToNonFinalStatic",
    "PMD.DataflowAnomalyAnalysis", "PMD.DoNotCallGarbageCollectionExplicitly",
    "PMD.LawOfDemeter", "PMD.NullAssignment", "PMD.SystemPrintln", "PMD.UnusedAssignment" })
//Ignore comment size (GPL copyright notice).
public class RandomFromNet extends AbstractUpdater {

  /**
   * User's screen width.
   */
  private static final Double MY_SCREEN_WIDTH = Toolkit
      .getDefaultToolkit()
      .getScreenSize()
      .getWidth();

  /**
   * User's screen height.
   */
  private static final Double MY_SCREEN_HEIGHT = Toolkit
      .getDefaultToolkit()
      .getScreenSize()
      .getHeight();

  /**
   * Direction to temporary folder, provided by the OS.
   */
  private static final String MY_TMP_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();

  /**
   * URI of the image server.
   */
  private static String myUri;

  /**
   * Request to image server.
   */
  private static HttpRequest myRequest;

  /**
   * Cache the image on local system.
   */
  private static File myCachePath;

  /**
   * Change notifier to catch error.
   */
  private static PropertyChangeSupport myNetNotifier;

  /**
   * Flag to indicate if the process is running.
   */
  private static boolean myRunFlag;

  /**
   * Constructor.
   */
  public RandomFromNet() {
    
    super();
    
    // Initialize notifier
    myNetNotifier = new PropertyChangeSupport(this);

    // A base URI to Lorem Picsum server.
    myUri = "https://picsum.photos/" + MY_SCREEN_WIDTH.intValue() + "/" + MY_SCREEN_HEIGHT.intValue();

  }
  
  /**
   * Add listener to this object.
   * @param theListener the designated listener.
   */
  public void addListener(final PropertyChangeListener theListener) {
    myNetNotifier.addPropertyChangeListener(theListener);
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

      // Sett myRunFlag to true
      myRunFlag = theSignal;

      this.provideRegularImage();

      // Set timer for changing preview panel and set desktop wallpaper.
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

                MY_SPI_WALLPAPER, MY_UNUSED, myCachePath.toString(), MY_SENDCHANGE

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

            // Randomize picture again.
            provideRegularImage();

            // Display picture in the preview panel.
            MiddleSettingPanel.displayPreview(myCachePath);

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

        // End of Timer task;
      }, MY_UNUSED, MY_INTERVAL);

      // End of schedule.

    } else {

      // Set myRunFlag to false.
      myRunFlag = theSignal;

      // Cancel the old thread before starting a new one.
      myAutoRun.cancel();

      // Set minute checker to default state (04/09)
      myRecentMinute = -1;

    }

  }

  /**
   * Get a random picture with the same resolution as user's screen, then cache it
   * on local system.
   */
  private void provideRegularImage() {

    // Create a request to image server.
    myRequest = HttpRequest.newBuilder().GET().uri(URI.create(myUri)).build();

    // A response from server.
    HttpResponse<InputStream> response;
    try {

      // Generate a request and get a response from it.
      response = HttpClient.newBuilder()
          .followRedirects(HttpClient.Redirect.ALWAYS)
          .version(HttpClient.Version.HTTP_2)
          .build()
          .send(myRequest, HttpResponse.BodyHandlers.ofInputStream());

      // Buffer the received image.
      final BufferedImage img = ImageIO.read(response.body());

      // Path to cache the image.
      myCachePath = new File(MY_TMP_DIR + "\\cacheBg.png");

      // Cache the image.
      ImageIO.write(img, "png", myCachePath);

    } catch (ConnectException e) {

      // Make a call to controller/view to update message or dialog
      // when there is no Internet connection.
      myNetNotifier.firePropertyChange(null, null, CodeError.NET_NO_CONNECTION);

    } catch (IOException e) {
      // For internal use only

      System.out.println("Something is wrong with IO in RandomFromNet.");

    } catch (InterruptedException e) {
      // For internal use only

      System.out.println("Something is wrong with a connection made by RandomFromNet");
    }

  }
  
  // Temporary, as of 09/05/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  
  // Ignore Comment size.
  // Ignore Assignment to non final static.
  // Ignore Data flow anomaly analysis.
  // Ignore Do not call GC explicitly.
  // Ignore LoD.
  // Ignore Null assignment.
  // Ignore System println.
  // Ignore Unused assignment.
  
}
