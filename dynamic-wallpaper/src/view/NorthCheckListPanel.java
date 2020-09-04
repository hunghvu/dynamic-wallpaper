package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel contains guidance on how to use the program. They are also
 * requirement to run the program properly.
 * 
 * @author Hung Vu
 *
 */
@SuppressWarnings({"serial", "PMD.CommentSize", "PMD.RedundantFieldInitializer"})
public class NorthCheckListPanel extends JPanel {
  
  /**
   * Indicate owner.
   */
  private static final JLabel MY_OWNER = new JLabel("© Hung Huu Vu 2020.");

  /**
   * Indicate the purpose of region.
   */
  private static final JLabel MY_CHECK_LIST = new JLabel("Check List");

  /**
   * First step in using a program.
   */
  private static final JLabel MY_REQ_1 = new JLabel(
      
      "1) Choose folder directory. Not completed."
      
      );

  /**
   * Second step in using a program.
   */
  private static final JLabel MY_REQ_2 = new JLabel(
      "2) Set the time you want to change the wallpaper. " + "Not completed.");
  
  /**
   * Check the box if a user want to get random image from Internet instead.
   */
  private static final JCheckBox MY_REQ_1_NET = new JCheckBox(
      "Get random wallpaper from Internet. "
      + "This feature requires an Internet connection. "
      + "Requirement 1 will be ignored.");
  
  /**
   * Represent incomplete state of requirement 1.
   */
  private static final int MY_REQ1_NOT_DONE = 10;
  
  /**
   * Represent completed state of requirement 1.
   */
  private static final int MY_REQ1_DONE = 11;
  
  /**
   * Represent incomplete state of requirement 2.
   */
  private static final int MY_REQ2_NOT_DONE = 20;
  
  /**
   * Represent completed state of requirement 2.
   */
  private static final int MY_REQ2_DONE = 21;

  /**
   * State of requirement 1. <br>
   * false means incomplete, true otherwise.
   */
  private static boolean myReq1State = false;

  /**
   * State of requirement 2. <br>
   * false means incomplete, true otherwise.
   */
  private static boolean myReq2State = false;

  /**
   * Constructor.
   */
  public NorthCheckListPanel() {
    //Call super.
    super();
    
    // Panel properties.
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    // Add component.
    add(MY_OWNER);
    add(MY_CHECK_LIST);
    add(MY_REQ_1);
    add(MY_REQ_1_NET);
    add(MY_REQ_2);
    
    MY_REQ_1_NET.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        
        if (MY_REQ_1_NET.isSelected()) {
          
          MY_REQ_1.setText("1) Choose folder directory. Ignored.");
          myReq1State = true;
          SouthDirPanel.getBrowseButton().setEnabled(false);
          SouthDirPanel.getTextArea().setText(
              "You are using image from the Internet. There is no need to select local picture folder.");
          
        } else {
          
          requirementSetter(10);
          SouthDirPanel.getBrowseButton().setEnabled(true);
          SouthDirPanel.getTextArea().setText("");
          
        }
        
      }
      
    });

  }

  /**
   * Check if requirement 1 is completed.
   * 
   * @return state of requirement 1 <br>
   *         true is incomplete <br>
   *         false otherwise
   */
  public static boolean is1Incomplete() {
    return !myReq1State;
  }

  /**
   * Check if requirement 2 is completed.
   * 
   * @return state of requirement 2 <br>
   *         true is incomplete <br>
   *         false otherwise
   */
  public static boolean is2Incomplete() {
    return !myReq2State;
  }

  /**
   * Change requirement (not completed -> completed).
   * 
   * @param theRequirementNum indicates which label is going to be changed: <br>
   *                          10 for requirement 1) Not completed <br>
   *                          11 for requirement 1) Completed <br>
   *                          20 for requirement 2) Not completed <br>
   *                          21 for requirement 2) Completed
   */
  public static void requirementSetter(final int theRequirementNum) {

    // If something is achieved, update the check list.
    if (theRequirementNum == MY_REQ1_NOT_DONE) {

      MY_REQ_1.setText("1) Choose folder directory. Not completed.");
      myReq1State = false;

    } else if (theRequirementNum == MY_REQ1_DONE) {

      MY_REQ_1.setText("1) Choose folder directory. Completed.");
      myReq1State = true;

    } else if (theRequirementNum == MY_REQ2_NOT_DONE) {

      MY_REQ_2.setText("2) Set the time you want to change the wallpaper. Not completed.");
      myReq2State = false;

    } else if (theRequirementNum == MY_REQ2_DONE) {

      MY_REQ_2.setText("2) Set the time you want to change the wallpaper. Completed.");
      myReq2State = true;

    } 
    
    //No need in final release.
    //    else { // For internal use only, to check when wrong parameter is passed.
    //
    //      System.out.println("Wrong option, please check parameter again.");
    //
    //    }

  }

  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore redundant warning.
  // Ignore comment size warning.
}
