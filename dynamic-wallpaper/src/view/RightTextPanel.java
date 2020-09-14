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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.TimeList;

/**
 * This class creates right/text panel, <br>
 * to display information (log and time list).
 *
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "serial", "PMD.LawOfDemeter", "PMD.CommentSize"  })
//Ignore comment size (GPL copyright notice).
public class RightTextPanel extends JPanel implements ActionListener {

  /**
   * Text height/rows.
   */
  private static final int MY_HEIGHT = 20;

  /**
   * Text width/columns.
   */
  private static final int MY_WIDTH = 15;

  /**
   * Display log.
   */
  private static final JTextArea MY_TEXT_LOG = new JTextArea(MY_HEIGHT, MY_WIDTH);

  /**
   * Display saved time.
   */
  private static final JTextArea MY_TEXT_TIME_LIST = new JTextArea(MY_HEIGHT, MY_WIDTH);

  /**
   * Make log scrollable.
   */
  private static final JScrollPane SCROLL_LOG = new JScrollPane(MY_TEXT_LOG);

  /**
   * Make time list scrollable.
   */
  private static final JScrollPane SCROLL_TIME_LIST = new JScrollPane(MY_TEXT_TIME_LIST);

  /**
   * Button to clear LOG area.
   */
  private static final JButton MY_X_LOG_BUTTON = new JButton("Clear LOG");

  /**
   * Button to clear TIME LIST.
   */
  private static final JButton MY_X_TIME_BUTTON = new JButton("Clear TIME LIST");

  /**
   * Button to export TIME LIST.
   */
  private static final JButton MY_EXPORT_BUTTON = new JButton("Export TIME LIST"); // 09/14

  /**
   * Button to export TIME LIST.
   */
  private static final JButton MY_IMPORT_BUTTON = new JButton("Import TIME LIST"); // 09/14

  /**
   * Sub panel for clear log button. Aesthetic purpose (avoiding layout component
   * resize).
   */
  private static final JPanel MY_X_LOG_PANEL = new JPanel();

  /**
   * Sub panel for clear time list button. Aesthetic purpose (avoiding layout
   * component resize).
   */
  private static final JPanel MY_X_TIME_PANEL = new JPanel();

  /**
   * Sub panel for export button. Aesthetic purpose (avoiding layout
   * component resize).
   */
  private static final JPanel MY_EXPORT_PANEL = new JPanel(); // 09/14

  /**
   * Sub panel for import button. Aesthetic purpose (avoiding layout
   * component resize).
   */
  private static final JPanel MY_IMPORT_PANEL = new JPanel(); // 09/14

  /**
   * LOG area label.
   */
  private static final JLabel MY_LOG_LABEL = new JLabel("LOG:");

  /**
   * TIME LIST area label.
   */
  private static final JLabel MY_TIME_LABEL = new JLabel("TIME LIST:");

  /**
   * Parameter to print message to text log.
   */
  private static final String MY_LOG_TEXT = "LOG";

  /**
   * Parameter to print message to Time List.
   */
  private static final String MY_TIME_LIST_TEXT = "TIME_LIST";

  /**
   * Cache the previous path for file chooser, both export and import buttons.
   */
  private static File myCachedDir = null;

  /**
   * Constructor.
   */
  public RightTextPanel() {

    // Call super.
    super();

    // Panel properties.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Component properties.
    MY_TEXT_LOG.setEditable(false);
    MY_TEXT_TIME_LIST.setEditable(false);
    MY_X_LOG_BUTTON.addActionListener(this);
    MY_X_TIME_BUTTON.addActionListener(this);
    MY_EXPORT_BUTTON.addActionListener(this); // 09/14
    MY_IMPORT_BUTTON.addActionListener(this); // 09/14
    MY_X_LOG_PANEL.add(MY_X_LOG_BUTTON);
    MY_X_TIME_PANEL.add(MY_X_TIME_BUTTON);
    MY_EXPORT_PANEL.add(MY_EXPORT_BUTTON); // 09/14
    MY_IMPORT_PANEL.add(MY_IMPORT_BUTTON); // 09/14
    // Center aligned labels.
    MY_LOG_LABEL.setAlignmentX(CENTER_ALIGNMENT);
    MY_TIME_LABEL.setAlignmentX(CENTER_ALIGNMENT);

    // Add components to panel.
    add(MY_LOG_LABEL);
    add(SCROLL_LOG);
    add(MY_X_LOG_PANEL);
    add(MY_TIME_LABEL);
    add(SCROLL_TIME_LIST);
    add(MY_EXPORT_PANEL);// 09/14
    add(MY_IMPORT_PANEL);// 09/14
    add(MY_X_TIME_PANEL);

  }

  /**
   * Text setter for text (message) area.
   *
   * @param theIndicator Indicate which text area is going to be changed: <br>
   *                     LOG for MY_TEXT_LOG (LOG message)<br>
   *                     TIME_LIST for MY_TEXT_TIME_LIST (TIME LIST message)
   */
  public static void textSetter(final String theIndicator, final String theMsg) {

    if (theIndicator.equals(MY_LOG_TEXT)) {

      MY_TEXT_LOG.append(theMsg + "\n \n");

    } else if (theIndicator.equals(MY_TIME_LIST_TEXT)) {

      MY_TEXT_LOG.append(theMsg + "\n \n");
      MY_TEXT_TIME_LIST.setText(String.join("\n \n", TimeList.getTimeList()));

    }

    // No need in final release.
    // else { // For internal use only, to check when wrong parameter is passed.
    //
    // System.out.println("Wrong option, please check parameter again.");
    //
    // }
  }

  /**
   * Indicate program behavior when a button is pressed.
   */
  @Override
  public void actionPerformed(final ActionEvent theE) {

    // Clear log when the button is pressed.
    if (theE.getSource() == MY_X_LOG_BUTTON) {

      MY_TEXT_LOG.setText("");

    } else if (theE.getSource() == MY_X_TIME_BUTTON) {
      // Clear time list (display message + data storage) when
      // button is pressed.

      // Reset time list data. (02/09)
      controller.Controller.resetTimeList();

      // Change requirement checklist.
      NorthCheckListPanel.requirementSetter(20);

      // Display log.
      MY_TEXT_LOG.append("Clear time list successfully! \n" + "If the program is still running, \n"
          + "please add new time to the list, \n" + "or stop the program. \n \n");

      // Reset time list display/message.
      MY_TEXT_TIME_LIST.setText("");

    } else if (theE.getSource() == MY_EXPORT_BUTTON) {

      exportButtonAction();

    }

  }

  /**
   * Action for export button. 09/14
   */
  private void exportButtonAction() {

    final JFileChooser chooser = new JFileChooser();

    // Only display txt files.
    final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
    chooser.setFileFilter(filter);

    if (myCachedDir != null) {
      chooser.setCurrentDirectory(myCachedDir);
    }

    final int chosenOption = chooser.showOpenDialog(this);

    // Holder of file path.
    File savedFile = null;

    if (chosenOption == JFileChooser.APPROVE_OPTION) {

      // Open previous choosen path.
      savedFile = chooser.getSelectedFile().getAbsoluteFile();

      // Cache path.
      myCachedDir = savedFile.getParentFile();

      try {

        // Save config to a file
        PrintWriter writer = new PrintWriter(savedFile);
        writer.println(MY_TEXT_TIME_LIST.getText());
        writer.close();

      } catch (FileNotFoundException e) {

        System.out.println("Exception in RightTextPanel export writer.");

      }

    }

  }

  // Done, as of 09/07/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.

  // Ignore LoD

}
