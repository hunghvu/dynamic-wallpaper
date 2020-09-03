package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates a middle panel to preview image, receives users input to
 * create time list, and create proper message to display.
 *
 * @author Hung Vu
 *
 */
@SuppressWarnings({"serial", "PMD.LawOfDemeter", "PMD.NullAssignment",
    "PMD.DataflowAnomalyAnalysis", "PMD.DoNotCallGarbageCollectionExplicitly",
    "PMD.UnusedAssignment"})
public class MiddleSettingPanel extends JPanel implements ActionListener {

  //Remove time list (02/09)

  /**
   * Save the time to change wallpaper.
   */
  private static final JButton MY_SAVE_TIME = new JButton("Save Fields");

  /**
   * Clear input field.
   */
  private static final JButton MY_CLEAR_TIME = new JButton("Clear Fields");

  /**
   * Delete given time from time list.
   */
  private static final JButton MY_DELETE_TIME = new JButton("Delete Provided Time");

  /**
   * Hour (Format: HH from 00-23).
   */
  private static final JTextField MY_HOUR = new JTextField();

  /**
   * Minute (Format: MM from 00-59).
   */
  private static final JTextField MY_MINUTE = new JTextField();

  /**
   * Label to indicate purpose of middle region.
   */
  private static final JLabel MY_PREVIEW_LABEL = new JLabel(

      "Next Wallpaper Preview", JLabel.CENTER

      );

  /**
   * Label to indicate status of the program.
   */
  private static final JLabel MY_STATUS_LABEL = new JLabel("Status: Not Running");

  /**
   * Picture preview sub-panel.
   */
  private static final JPanel MY_PREVIEW_PANEL = new JPanel();

  /**
   * Width of previewed image.
   */
  private static final int MY_RESIZE_W = 770;

  /**
   * Preview height.
   */
  private static final int MY_RESIZE_H = 435;

  /**
   * Image for the preview panel. This will contain image after resized.
   */
  private static ImageIcon myWallpaperImage;

  /**
   * Label to encapsulate Image. This will contain resized image to add to panel.
   */
  private static JLabel myImageLabel;

  /**
   * Constructor.
   */
  public MiddleSettingPanel() {
    // Call super.
    super();

    // Properties of panel.
    setLayout(new BorderLayout());

    // Properties of components.
    MY_SAVE_TIME.addActionListener(this);
    MY_CLEAR_TIME.addActionListener(this);
    MY_DELETE_TIME.addActionListener(this);
    MY_HOUR.setPreferredSize(new Dimension(20, 20));
    MY_MINUTE.setPreferredSize(new Dimension(20, 20));
    MY_PREVIEW_LABEL.setPreferredSize(new Dimension(120, 120));
    MY_STATUS_LABEL.setPreferredSize(new Dimension(120, 20)); //Actual min height: 9, width 111

    // Create sub-panel for South region of this panel. BorderLayout only allow 1
    // component per region.
    // Therefore, using sub-panel as 1 component that contains other components.
    final JPanel subSouth = new JPanel();
    subSouth.add(new JLabel("Hour (00-23):"));
    subSouth.add(MY_HOUR);
    subSouth.add(new JLabel("Minute (00-59):"));
    subSouth.add(MY_MINUTE);
    subSouth.add(MY_SAVE_TIME);
    subSouth.add(MY_CLEAR_TIME);
    subSouth.add(MY_DELETE_TIME);
    subSouth.add(MY_STATUS_LABEL);

    // Add components to panel.
    add(MY_PREVIEW_LABEL, BorderLayout.NORTH);
    add(MY_PREVIEW_PANEL, BorderLayout.CENTER);
    add(subSouth, BorderLayout.SOUTH);
  }

  /**
   * This method provide the input in hour text field.
   *
   * @return hour in text field
   */
  public static String getMyHourText() {

    return MY_HOUR.getText();

  }

  /**
   * This method provide the input in minute text field.
   *
   * @return minute in text field
   */
  public static String getMyMinuteText() {

    return MY_MINUTE.getText();

  }

  /**
   * Display the status of program.
   * @param theStatus Indicates whether the program is running. <br>
   True is running <br>
   false otherwise.
   */
  public static void setRunStatus(final boolean theStatus) {
    if (theStatus) {

      MY_STATUS_LABEL.setText("Status: Running");

    } else {

      MY_STATUS_LABEL.setText("Status: Not Running");

    }
  }

  /**
   * Display image on preview panel.
   */
  public static void displayPreview(final File thePath) {

    // Remove old image to add the new one.
    MY_PREVIEW_PANEL.removeAll();

    // Get original image icon.
    ImageIcon originalIcon = new ImageIcon(thePath.toString());

    //Get original image.
    Image originalImage = originalIcon.getImage();

    //Image to resize
    Image resized = originalImage.getScaledInstance(

        MY_RESIZE_W, //776 fit left margin 774 fit right margin
        MY_RESIZE_H,
        Image.SCALE_DEFAULT

        );

    // Previewed image after resized.
    myWallpaperImage = new ImageIcon(resized);

    // Image inside label to add into preview panel.
    myImageLabel = new JLabel("", myWallpaperImage, JLabel.CENTER);

    // Add label (image) to the panel.
    MY_PREVIEW_PANEL.add(myImageLabel);

    //Destroy image in memory. To prevent memory leak from them.
    originalImage.flush();
    resized.flush();

    //Set to null to persuade garbage collection.
    //Not reliable, only to limit the possibility of memory leak.
    originalIcon = null;
    originalImage = null;
    resized = null;
    myWallpaperImage = null;
    myImageLabel = null;

    //Collect heap garbage.
    System.gc();

  }

  /**
   * Program behavior when a button is pressed.
   */
  @Override
  public void actionPerformed(final ActionEvent theE) {

    if (theE.getSource() == MY_SAVE_TIME) {

      controller.Controller.setTime(getMyHourText(), getMyMinuteText());

      // Reset input fields after button is pressed.
      MY_HOUR.setText("");
      MY_MINUTE.setText("");



      //////////////////////////////////


    } else if (theE.getSource() == MY_CLEAR_TIME) { // Clear input fields.

      MY_HOUR.setText("");
      MY_MINUTE.setText("");

    } else if (theE.getSource() == MY_DELETE_TIME) {
      // Delete time (from string time list)
      // and update message.
      controller.Controller.deleteTime(getMyHourText(), getMyMinuteText());
      controller.Controller.emptyTime();


      // Reset text field after button is pressed
      MY_HOUR.setText("");
      MY_MINUTE.setText("");
    }

  }

  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore Cyclomatic-complexity-type error.
  // Ignore Law of Demeter (LoD) potential violation.
  // Ignore Excessive Method length (action performed).
  // Ignore Avoid duplicate literal ("Please try again." 4 times).
  // Ignore Data flow anomaly (set variables to null).
  // Ignore null assignment.
  // Ignore do not call gc explicitly.

}
