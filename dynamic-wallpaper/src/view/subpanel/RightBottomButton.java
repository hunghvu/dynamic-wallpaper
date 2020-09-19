package view.subpanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.NorthCheckListPanel;
import view.RightTextPanel;

/**
 * This class groups up the buttons at the bottom
 * of RightTextPanel.
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings("serial")
public class RightBottomButton extends JPanel implements ActionListener{
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
   * Cache the previous path for file chooser, both export and import buttons.
   */
  private static File myCachedDir = null;
  
  /**
   * Constructor.
   */
  public RightBottomButton() {
    
    // Panel properties.
    setLayout(new GridLayout(0, 1));
    
    // Add properties.
    MY_X_TIME_BUTTON.addActionListener(this);
    MY_EXPORT_BUTTON.addActionListener(this); // 09/14
    MY_IMPORT_BUTTON.addActionListener(this); // 09/14
    
    // Add components.
    add(MY_X_TIME_BUTTON);
    add(MY_EXPORT_BUTTON);
    add(MY_IMPORT_BUTTON);
    
  }

  @Override
  public void actionPerformed(ActionEvent theE) {

    if (theE.getSource() == MY_X_TIME_BUTTON) {
      // Clear time list (display message + data storage) when
      // button is pressed.

      clearButtonAction();

    } else if (theE.getSource() == MY_EXPORT_BUTTON) {

      exportButtonAction();

    }
    
  }
  
  /**
   * Action for clear button.
   */
  private void clearButtonAction() {
    
    // Reset time list data. (02/09)
    controller.Controller.resetTimeList();

    // Change requirement checklist.
    NorthCheckListPanel.requirementSetter(20);

    // Display log.
    RightTextPanel.textSetter(
        "LOG", 
        "Clear time list successfully! \n" 
        + "If the program is still running, \n"
        + "please add new time to the list, \n" 
        + "or stop the program. ");

    // Reset time list display/message.
    RightTextPanel.textSetter("RESET_TIME", "");
    
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
        writer.println(RightTextPanel.getTimeText());
        writer.close();

      } catch (FileNotFoundException e) {

        System.out.println("Exception in RightTextPanel export writer.");

      }

    }

  }
}
