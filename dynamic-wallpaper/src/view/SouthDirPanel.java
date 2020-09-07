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

package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * This class creates south region panel, which includes Apply button and
 * directory browser.
 *
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "serial", "PMD.LawOfDemeter",  "PMD.NullAssignment",
    "PMD.AssignmentToNonFinalStatic", "PMD.CommentSize" })
//Ignore comment size (GPL copyright notice).
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

  // Remove FileArray (unused code - 09/02)

  // Remove AutoRun (09/02)

  /**
   * Timer to automatically update preview panel (refresh GUI). <br>
   * This will be initialized after Apply is successfully pressed.
   */
  private Timer myUpdateTimer;
  
  /**
   * This is running flag to prevent user pressing apply multiple times.
   * True means a user can apply, false otherwise.
   */
  private static boolean myApplyFlag;

  /**
   * Constructor.
   */
  public SouthDirPanel() {
    
    // Call super.
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
    
    // Initialize apply flag.
    myApplyFlag = true;

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
   * Return the browse button for necessary manipulation.
   * 
   * @return the browse button
   */
  public static JButton getBrowseButton() {
    
    return MY_DIR_BROWSE;
    
  }

  /**
   * Return the text are for necessary manipulation.
   * 
   * @return the text area
   */
  public static JTextArea getTextArea() {
    
    return MY_TEXT;
    
  }

  /**
   * Determine program behavior when a button is pressed.
   */
  @Override
  public void actionPerformed(final ActionEvent theE) {

    if (theE.getSource() == MY_DIR_BROWSE) {
      
      dirButtonAction();

    } else if (theE.getSource() == MY_APPLY) {
      applyButtonAction();

    } else if (theE.getSource() == MY_STOP) {
      // Stop the program.

      stopButtonAction();

    } else if (theE.getSource() == myUpdateTimer) {
      // Event is invoked after a certain time.
      // This case is for automatic GUI refreshing.

      repaint();
      revalidate();

    }

  }

  /**
   * This is the action perfomed when Stop button is chosen.
   */
  private void stopButtonAction() {
    
    // When timer are null.
    if (
        myUpdateTimer == null 
        || !controller.Controller.isFolderRunning() 
        && !controller.Controller.isNetRunning() 
        // False means not running, but need "true" for the comparison.
        // Default order: Evaluate && first then ||
        // so no need for parentheses.  
        ) {
      
      RightTextPanel.textSetter(MY_LOG_OPTION, "Program is currently not running!");

    } else {
      // When timer is set.

      // Stop the threads.
      myUpdateTimer.stop();

      if (NorthCheckListPanel.isInternetChosen()) {
        
        controller.Controller.stopBackgroundNet();

        
      } else {
        
        controller.Controller.stopBackgroundFolder();
        
      }
      
      // Lock the check box so a thread can be stopped properly. (09/07)
      NorthCheckListPanel.setCheckboxUsability(true);
      
      // Display log.
      RightTextPanel.textSetter(
          
          MY_LOG_OPTION, 
          "Program has stopped. \n" + "Please press Apply button to restart!"
          
      );

      // Display running status.
      MiddleSettingPanel.setRunStatus(false);
      
      // Allow user to apply after the program is stopped.
      // Reset apply flag.
      myApplyFlag = true;

    }
    
  }

  /**
   * This is the action performed when Apply button is chosen.
   */
  private void applyButtonAction() {

    if (NorthCheckListPanel.is1Incomplete() || NorthCheckListPanel.is2Incomplete()) {
      // Print message when either of the requirement is not satisfied.
      // (Either is true).

      RightTextPanel.textSetter(MY_LOG_OPTION, "Some requirements are not yet completed! \n"
          + "Please complete all the requirements first then try again.");

    } else if (myApplyFlag) {

      // Stop old instance of (GUI/revalidate/repaint update) timer before creating a
      // new one. Set to null to persuade GC.
      if (myUpdateTimer != null) {

        myUpdateTimer.stop();
        myUpdateTimer = null;

      }

      if (NorthCheckListPanel.isInternetChosen()) {
        
        controller.Controller.startBackgroundNet();
        
      } else {
        
        controller.Controller.createFileArray(myFolderDir);
        controller.Controller.startBackgroundFolder();
        
        // Lock the check box so a thread can be stopped properly. (09/07)
        NorthCheckListPanel.setCheckboxUsability(false);
        
      }

      // Timer for automatic repaint/revalidate.
      myUpdateTimer = new Timer(250, this); // Action listener (this)
      myUpdateTimer.start();

      // Print message after apply button is pressed.
      RightTextPanel.textSetter(MY_LOG_OPTION, "Apply completed. \n"
          + "Press Stop button to un-grey-out the checkbox.");

      // Display running state.
      MiddleSettingPanel.setRunStatus(true);
      
      // Not allow user to continuously press apply button.
      myApplyFlag = false;

    } else {
      // Show a dialog to let user know 
      // not to press "Apply" button multiple times in a row.
      
      final JFrame msgFrame = new JFrame();
      JOptionPane.showMessageDialog(msgFrame, "The process is running right now. "
          + "Please either changing the picture folder or press stop button first.");
      
    }
  }
  
  /**
   * This is the action performed when Browse button is chosen.
   */
  private void dirButtonAction() {
    
    // Allow user to apply again after changing folder directory.
    // Reset apply flag.
    myApplyFlag = true;
    
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
    
  }
  
  // Done, as of 09/07/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore Potential violation of Law of Demeter (LoD).
  // Ignore Null assignment.
  // Ignore Assignment to non final static.
  
  // Fix Do not call GC explicitly.
  // Fix Excessive method length (by refactoring).
  // Fix Cyclomatic complexity (all types).
  
}
