package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import model.AutoUpdateTime;
import model.FileArray;

/**
 * This class creates south region panel, which includes Apply button and
 * directory browser.
 * 
 * @author Hung Vu
 *
 */
@SuppressWarnings({"serial", "PMD.RedundantFieldInitializer", "PMD.LawOfDemeter",
    "PMD.ExcessiveMethodLength", "PMD.ModifiedCyclomaticComplexity",
    "PMD.DoNotCallGarbageCollectionExplicitly", "PMD.NullAssignment"})
public class SouthDirPanel extends JPanel implements ActionListener {

  /**
   * Button to open file chooser.
   */
  private static final JButton MY_DIR_BROWSE = new JButton("Browse");
  
  /**
   * Stop button.
   */
  private static final JButton MY_STOP = new JButton("Stop");
  
  /**
   * Apply button.
   */
  private static final JButton MY_APPLY = new JButton("Apply");

  /**
   * Directory display. Size is managed by panel layout.
   */
  private static final JTextArea MY_TEXT = new JTextArea();
  
  /**
   * Parameter to print to log area.
   */
  private static final String MY_LOG_OPTION = "LOG";

  /**
   * Store picture folder path. It will be initialized after a user selects
   * directory.
   */
  private static File myFolderDir;

  /**
   * Store an array of picture (file path) of given folder. <br>
   * It will be initialized after a user selects directory.
   */
  private static FileArray myFileArray = null;

  /**
   * Make program run automatically when Apply is pressed. <br>
   * This will be initialized after Apply is successfully pressed.
   */
  private static AutoUpdateTime myAutoRun;

  /**
   * Timer to automatically update preview panel (refresh GUI). <br>
   * This will be initialized after Apply is successfully pressed.
   */
  private Timer myUpdateTimer;

  /**
   * Constructor.
   */
  public SouthDirPanel() {
    //Call super.
    super();
    
    // Panel properties.
    setLayout(new GridLayout(2, 2));
    // Define size so the panel can have better appearance.
    setPreferredSize(new Dimension(100, 200));

    // Component properties
    MY_TEXT.setEditable(false);
    MY_DIR_BROWSE.addActionListener(this);
    MY_STOP.addActionListener(this);
    MY_APPLY.addActionListener(this);

    // Add component to panel
    add(MY_TEXT);
    add(MY_DIR_BROWSE);
    add(MY_STOP);
    add(MY_APPLY);

  }

  /**
   * Getter for folder directory.
   * 
   * @return folder path.
   */
  public static File folderDirGetter() {
    return myFolderDir;
  }

  /**
   * Getter for array of file path in folder.
   * 
   * @return array of file path.
   */
  public static FileArray getFileArray() {

    return myFileArray;

  }

  /**
   * Determine program behavior when a button is pressed.
   */
  @Override
  public void actionPerformed(final ActionEvent theE) {

    if (theE.getSource() == MY_DIR_BROWSE) {

      // File chooser to select folder directory.
      final JFileChooser myChooser = new JFileChooser();
      // Limit to choosing folder path only.
      myChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      // "this" indicate the SouthDirPanel panel is the parent window.
      final int chosenOption = myChooser.showOpenDialog(this);

      // Close file chooser window and return absolute folder path when "open" is
      // pressed.
      // Print folder path to message area.
      if (chosenOption == JFileChooser.APPROVE_OPTION) {

        // Get the absolute path.
        myFolderDir = myChooser.getSelectedFile().getAbsoluteFile();

        // Print message.
        MY_TEXT.setText("Picture(s) in directory: " + myFolderDir 
            + "\n If the path is changed, please press Apply button again. \n \n"
            + "Important: Please ensuring all files in the choosen folder are pictures. \n"
            + "In case a non-picture file like .txt is choosen, it cannot be set as the \n"
            + "desktop background. Therefore, the background will be set to black wallpaper!");

        // Update the requirement checker.
        NorthCheckListPanel.requirementSetter(11);

      }

    } else if (theE.getSource() == MY_APPLY) { 
      // Requirement must be all checked before apply (add later) (6/7/20).
      // Done (6/11/20).

      if (NorthCheckListPanel.is1Incomplete() || NorthCheckListPanel.is2Incomplete()) { 
        // Print message when either of the requirement is not satisfied.
        // (Either is true).

        RightTextPanel.textSetter(MY_LOG_OPTION, "Some requirements are not yet completed! \n"
            + "Please complete all the requirements first then try again.");

      } else {

        // Stop old instance of (GUI/revalidate/repaint update) timer before creating a
        // new one. Set to null to persuade GC.
        if (myUpdateTimer != null) {

          myUpdateTimer.stop();
          myUpdateTimer = null;

        }

        // Stop old instance of wallpaper and preview setter timer before creating a new
        // one. Set to null to persuade GC.
        if (myAutoRun != null) {

          myAutoRun.autoUpdate(false);
          myAutoRun = null;

        }
        
        // Garbage collection.
        System.gc();

        // Run the program automatically.
        myAutoRun = new AutoUpdateTime(myFolderDir);

        // Timer for automatic repaint/revalidate.
        myUpdateTimer = new Timer(250, this); // Action listener (this)
        myUpdateTimer.start();

        // Print message after apply button is pressed.
        RightTextPanel.textSetter(MY_LOG_OPTION, "Apply completed.");
        
        //Display running state.
        MiddleSettingPanel.setRunStatus(true);

      }

    } else if (theE.getSource() == MY_STOP) {
      //Stop the program.
      
      // When timer are null.
      if (myUpdateTimer == null || myAutoRun == null) {
        
        RightTextPanel.textSetter(MY_LOG_OPTION, "Program is currently not running!");
        
      } else {
        // When timer are set.       
        
        // Stop the threads.
        myUpdateTimer.stop();
        myAutoRun.autoUpdate(false);
        
        // Display log.
        RightTextPanel.textSetter(MY_LOG_OPTION, "Program has stopped. \n"
            + "Please press Apply button to restart!");
        
        // Display running status.
        MiddleSettingPanel.setRunStatus(false);
        
      }
      
    } else if (theE.getSource() == myUpdateTimer) { 
      // Event is invoked after a certain time.
      // This case is for automatic GUI refreshing.

      repaint();
      revalidate();

    }

  }

  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore potential violation of Law of Demeter (LoD).
  // Ignore redundant warning.
  // Ignore excessive method length (override action performed).
  // Ignore modified cyclomatic complexity.
  // Ignore null assignment.
  // Ignore do not call gc explicitly.

}
