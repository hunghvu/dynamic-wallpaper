package view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The main class to run a program.
 * This program will changes desktop wallpaper
 * based on the time set.
 * @author Hung Vu
 *
 */
@SuppressWarnings({"PMD.DoNotCallSystemExit", "PMD.ExcessiveMethodLength",
    "PMD.LawOfDemeter", "PMD.ModifiedCyclomaticComplexity",
    "PMD.SystemPrintln", "PMD.UseUtilityClass", "PMD.DataflowAnomalyAnalysis",
    "PMD.CloseResource"})
public class MainProgram {
  
  /**
   * Number of clicks.
   */
  private static final int DOUBLE_CLICK = 2;
  
  /**
   * Lock file path.
   */
  private static File lockFile;
  
  /**
   * Access lock.
   */
  private static FileLock ioLock;
  
  /**
   * Method to run dynamic wallpaper program.
   * @param theArgs argument to run the program.
   */
  public static void main(final String[] theArgs) {
    
    //System tmp directory. We don't care where it is located.
    final String tmpPath = new File(System.getProperty("java.io.tmpdir")).getPath();
    
    //The following code will return dir where jar is located instead.
    //For testing purpose only
    //String tmpPath = ClassLoader.getSystemClassLoader().getResource(".").getPath();
    
    //Create a path for lock file.
    lockFile = new File(tmpPath + "jvm.lock");   
    
    //The following section ensure only 1 instance of the program
    //can be executed at a time.
    try {
      
      //Create a lock file in read/write mode in case it doesn't exit.
      final RandomAccessFile ioFile = new RandomAccessFile(lockFile, "rw");
      
      //Get a channel to access file.
      final FileChannel ioChannel = ioFile.getChannel();
      
      if (isRunning(ioChannel)) {
        //Display message box then quit.
        
        final JFrame msgFrame = new JFrame();
        JOptionPane.showMessageDialog(msgFrame, "Another instance of this program is running! \n"
            + "Please stop or exit the current instance first before opening a new one.");
        
        //Close resource.
        ioFile.close();
        ioChannel.close();
        
        return;
        
      }
      
    } catch (IOException theE) {
      //For internal use only.
      
      System.out.println("Some thing is wrong with the IO. \n"
          + "Please check again!");
      
    } 
    //Cannot explicitly close resources using finally.
    //Let JVM handle it upon termination.
    ////////////
    
    //Initialize frame for program.
    final WindowFrame frame = new WindowFrame();
    frame.addWindowListener(new WindowAdapter() {
      
      /**
       * Fully close the program (JVM) when X-button is pressed.
       */
      @Override
      public void windowClosing(final WindowEvent theE) {
        
        System.exit(0);
        try {
          
          ioLock.release();

        } catch (IOException e) {
          //For internal use only.
          
          System.out.println("Some thing is wrong with the IO. \n"
              + "Please check again!");
          
        }
        
        lockFile.deleteOnExit();
        
      }
      
      /**
       * Hide the program when minimize button is pressed.
       */
      @Override
      public void windowIconified(final WindowEvent theE) {
        
        frame.setVisible(false);
        
      }
      
    });
    
    //Check if system support tray.
    if (SystemTray.isSupported()) {
      
      //Get systemTray instance.
      final SystemTray systemTray = SystemTray.getSystemTray();      
      
      //Popup menu for the tray icon (left click).   
      final PopupMenu trayPopup = new PopupMenu(); 
      
      //Components for tray icon's popup menu.
      final MenuItem restore = new MenuItem("Restore.");
      final MenuItem exit = new MenuItem("Exit!");

      //Properties of tray icon's component.
      restore.addActionListener(new ActionListener() {
        
        /**
         * Make the program reappear when restore is pressed.
         */
        @Override
        public void actionPerformed(final ActionEvent theE) {
          
          if (theE.getSource() == restore) {
            
            frame.setVisible(true);
            //Bring GUI to the front.
            frame.setExtendedState(JFrame.NORMAL);
            
          }
          
        }
        
      });
      
      exit.addActionListener(new ActionListener() {
        
        /**
         * Fully close the program (JVM) when "Exit!" is pressed.
         */
        @Override
        public void actionPerformed(final ActionEvent theE) {
          
          if (theE.getSource() == exit) {
            
            System.exit(0);
            try {
              
              ioLock.release();

            } catch (IOException e) {
              //For internal use only.
              
              System.out.println("Some thing is wrong with the IO. \n"
                  + "Please check again!");
              
            }
            
            lockFile.deleteOnExit();
            
          }
          
        }
        
      });
      
      //Add component to tray popup menu.
      trayPopup.add(restore);
      trayPopup.add(exit);
      
      //Icon image of the icon.
      //This method is used to have a proper executable file.
      final ImageIcon trayIconImg = new ImageIcon(
          
          MainProgram.class.getResource("/icon/iconDW.png")
          
          );
      
      //Image for tray icon.
      final Image trayImage = trayIconImg.getImage();
      
      //Initialize tray icon for system tray.
      final TrayIcon trayIcon = new TrayIcon(trayImage, "Demo", trayPopup);
     
      //Add double click (restore) for tray icon.
      trayIcon.addMouseListener(new MouseAdapter() {

        /**
         * Make the program reappear when double click the tray icon.
         */
        @Override
        public void mouseClicked(final MouseEvent theE) {
          
          if (theE.getClickCount() >= DOUBLE_CLICK) {
            frame.setVisible(true);
            //Bring GUI to the front.
            frame.setExtendedState(JFrame.NORMAL);
          }
          
        }
        
      });
      try {
        //Add tray icon to system tray.
        //Throw exception when system tray is not available.
        
        systemTray.add(trayIcon);
        
      } catch (AWTException e) {    
        //For internal use only
        
        System.out.println("System tray is not available.");
        
      }
      
    } else {
      //For internal use only
      
      System.out.println("System tray is not available.");
    }

  }
  
  /**
   * Show whether the program is running.
   * @return true means running <br>
   false otherwise
   */
  @SuppressWarnings("resource")
  private static boolean isRunning(final FileChannel theChannel) throws IOException {
    //Return only once variable.
    boolean running;
    
    //Acquire a lock from a file.
    //tryLock() will return null if the lock 
    //is still hold by another instance (cannot be acquired).
    //Else, acquire a lock.
    //
    //Note, lock() will return always return a lock, 
    //but "queued". Meaning the the program
    //will continue to execute once the old lock is released. 
    //(E.g: 1 hold lock, 2 also hold-but-wait-for-1)
    ioLock = theChannel.tryLock();    
    
    //Check if the lock is acquired.
    if (ioLock == null) {
      //The lock cannot be acquired.
      //Another instance is running and holding the lock.
      
      running = true;
      
    } else {
      
      running = false;
      
    }
    
    return running;
      
  }
  //Class: Done Recomment.
  //Class: Done Checkstyle.
  //Class: Done PMD.
  //Ignore Do not call system exit (don't kill VM).
  //Ignore Excessive method length.
  //Ignore LoD.
  //Ignore ModifiedCyclomaticComplexity.
  //Ignore SystemPrintln.
  //Ignore UseUtilityClass.
  //Ignore data flow anomaly analysis (Initialize frame).
  //Ignore close resource (File channel and random access file).

  
  //Update 6/14 Attempt to create file lock. Done.

}
