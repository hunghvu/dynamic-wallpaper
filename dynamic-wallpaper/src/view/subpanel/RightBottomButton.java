package view.subpanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.NorthCheckListPanel;
import view.RightTextPanel;

/**
 * This class groups up the buttons at the bottom of RightTextPanel.
 *
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({
    "serial", "PMD.CloseResource", "PMD.LawOfDemeter", "PMD.SystemPrintln",
    "PMD.OnlyOneReturn"
})
public class RightBottomButton extends JPanel implements ActionListener {

  /**
   * Empty line length.
   */
  private static final int EMPTY_LENGTH = 1;

  /**
   * Option to print the log to right panel.
   */
  private static final String MY_TEXT_LOG = "LOG";

  /**
   * Button to clear TIME LIST.
   */
  private static final JButton MY_X_TIME_BUTTON = new JButton("Clear TIME LIST");

  /**
   * Button to export TIME LIST.
   */
  private static final JButton MY_EXPORT_BUTTON = new JButton("Export TIME LIST");

  /**
   * Button to export TIME LIST.
   */
  private static final JButton MY_IMPORT_BUTTON = new JButton("Import TIME LIST");

  /**
   * Cache the previous path for file chooser, both export and import buttons.
   */
  private static File myCachedDir;

  /**
   * Constructor.
   */
  public RightBottomButton() {

    // Call super.
    super();

    // Panel properties.
    setLayout(new GridLayout(0, 1));

    // Add properties.
    MY_X_TIME_BUTTON.addActionListener(this);
    MY_EXPORT_BUTTON.addActionListener(this);
    MY_IMPORT_BUTTON.addActionListener(this);

    // Add components.
    add(MY_X_TIME_BUTTON);
    add(MY_EXPORT_BUTTON);
    add(MY_IMPORT_BUTTON);

  }

  @Override
  public void actionPerformed(final ActionEvent theE) {

    if (theE.getSource() == MY_X_TIME_BUTTON) {
      // Clear time list (display message + data storage) when
      // button is pressed.

      clearTimeList();

    } else if (theE.getSource() == MY_EXPORT_BUTTON) {

      exportTimeList();

    } else if (theE.getSource() == MY_IMPORT_BUTTON) {

      importTimeList();

    }

  }

  /**
   * Action for clear button.
   */
  private void clearTimeList() {

    // Reset time list data. (02/09)
    controller.Controller.resetTimeList();

    // Change requirement checklist.
    NorthCheckListPanel.requirementSetter(20);

    // Display log.
    RightTextPanel.textSetter(MY_TEXT_LOG, "Clear time list successfully! \n"
        + "If the program is still running, \n"
        + "please add new time to the list, \n" + "or stop the program. ");

    // Reset time list display/message.
    RightTextPanel.textSetter("RESET_TIME", "");

  }

  /**
   * Helper method to export time list and save to local storage.
   */
  private void exportTimeList() {

    // Initialize file chooser window.
    final JFileChooser chooser = this.initFileChooser();

    // Display file chooser window.
    final int chosenOption = chooser.showOpenDialog(this);

    File savedFile;
    if (chosenOption == JFileChooser.APPROVE_OPTION) {

      if ("".equals(RightTextPanel.getTimeText())) {

        // Avoid export when TIME_LIST is empty.
        RightTextPanel.textSetter("LOG", "Nothing to export. \n"
            + "Please fill in TIME LIST first. \n"
            + "Export - cancelled.");
        return;

      }

      // Open previous choosen path/file.
      savedFile = chooser.getSelectedFile().getAbsoluteFile();

      // Cache path.
      myCachedDir = savedFile.getParentFile();

      // String from of the path.
      final String savedPathString = savedFile.toString();

      // Length of the path.
      final int pathLength = savedPathString.length();

      // Min file name is "A.txt", length = 5.
      // Last 4 character is ".txt", used to compare the extension.
      // Prevent the full file name to be ".txt", which is not visible
      //  in JFileChooser by comparing the character before ".txt".

      // Note: "\\".equals(savedPathString.charAt(pathLength - 5)) is false
      //  because we cannot compare string with char.

      if (pathLength < 5

          || !".txt".equals(
              savedFile.toString().substring(pathLength - 4)
              )

          || '\\' == savedPathString.charAt(pathLength - 5)

          ) {

        final JFrame msgFrame = new JFrame();
        JOptionPane.showMessageDialog(msgFrame,
            "File extension must be txt, and the whole name cannot be '.txt'. \n"
            + "Please try again.");
        return;

      }

      try {

        // Save time list to a file.
        final PrintWriter writer = new PrintWriter(savedFile);
        writer.println(RightTextPanel.getTimeText());
        writer.close();

        RightTextPanel.textSetter(MY_TEXT_LOG, "Export TIME LIST successfully! \n"
            + "Please don't manually modify the exported file. \n"
            + "Use export to override the old file. \n"
            + "Or create a new one instead.");

      } catch (FileNotFoundException e) {

        System.out.println("Exception in RightTextPanel export writer.");

      }

    } else if (chosenOption == JFileChooser.CANCEL_OPTION) {

      RightTextPanel.textSetter(MY_TEXT_LOG, "Export - cancelled.");

    }

  }

  /**
   * Helper method to import time list from local storage.
   */
  private void importTimeList() {

    // Initialize file chooser windows.
    final JFileChooser chooser = this.initFileChooser();

    // Accept option.
    final int chosenOption = chooser.showOpenDialog(this);

    // Read from saved configuration.
    if (chosenOption == JFileChooser.APPROVE_OPTION) {

      final File config = chooser.getSelectedFile();

      try {

        @SuppressWarnings("resource")
        final Scanner input = new Scanner(config);
        while (input.hasNextLine()) {

          final String lineBuffer = input.nextLine();

          if (lineBuffer.isBlank()) {
            
            // Prevent importing empty file or file with only whitespace.
            RightTextPanel.textSetter(MY_TEXT_LOG, "Empty file. \n"
                + "Import - cancelled.");
            return;

          }

          // Avoid empty line.
          if (lineBuffer.length() != EMPTY_LENGTH) {

            // Get hour, minute and add them to time list.
            final String hour = lineBuffer.substring(0, 2);
            final String minute = lineBuffer.substring(3);
            controller.Controller.setTime(hour, minute);

          }

        }

      } catch (FileNotFoundException e) {

        System.out.println("Problems with import scanner in Right panel.");

      }

      RightTextPanel.textSetter(MY_TEXT_LOG, "Import - done.");

    } else if (chosenOption == JFileChooser.CANCEL_OPTION) {

      RightTextPanel.textSetter(MY_TEXT_LOG, "Import - cancelled.");

    }


  }

  /**
   * Helper method to initialize file chooser window.
   * @return a file chooser window.
   */
  private JFileChooser initFileChooser() {

    final JFileChooser chooser = new JFileChooser();

    // Only display txt files.
    final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
    chooser.setFileFilter(filter);

    if (myCachedDir != null) {
      chooser.setCurrentDirectory(myCachedDir);
    }

    return chooser;

  }

  // Done, as of 09/07/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.

  // Ignore close resource.
  // Ignore LoD.
  // Ignore System println.
  // Ignore only one return.
}
