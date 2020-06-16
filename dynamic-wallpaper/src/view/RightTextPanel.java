package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.TimeList;

/**
 * This class creates right/text panel, <br> 
 * to display information (log and time list).
 * 
 * @author Hung Vu
 *
 */
@SuppressWarnings("serial")
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
   * Constructor.
   */
  public RightTextPanel() {
    //Call super.
    super();
    
    // Panel properties.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Component properties.
    MY_TEXT_LOG.setEditable(false);
    MY_TEXT_TIME_LIST.setEditable(false);
    MY_X_LOG_BUTTON.addActionListener(this);
    MY_X_TIME_BUTTON.addActionListener(this);
    MY_X_LOG_PANEL.add(MY_X_LOG_BUTTON);
    MY_X_TIME_PANEL.add(MY_X_TIME_BUTTON);
    // Center aligned labels.
    MY_LOG_LABEL.setAlignmentX(CENTER_ALIGNMENT);
    MY_TIME_LABEL.setAlignmentX(CENTER_ALIGNMENT);

    // Add components to panel.
    add(MY_LOG_LABEL);
    add(SCROLL_LOG);
    add(MY_X_LOG_PANEL);
    add(MY_TIME_LABEL);
    add(SCROLL_TIME_LIST);
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
    
    //No need in final release.
    //    else { // For internal use only, to check when wrong parameter is passed.
    //
    //      System.out.println("Wrong option, please check parameter again.");
    //
    //    }
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

      // Reset time list data.
      MiddleSettingPanel.resetTimeList();

      // Change requirement checklist.
      NorthCheckListPanel.requirementSetter(20);
      
      // Display log.
      MY_TEXT_LOG.append("Clear time list successfully! \n" 
          + "If the program is still running, \n" 
          + "please add new time to the list, \n"
          + "or stop the program. \n \n");

      // Reset time list display/message.
      MY_TEXT_TIME_LIST.setText("");

    }

  }

  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.

}
