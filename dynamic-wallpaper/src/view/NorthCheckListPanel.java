package view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This panel contains guidance on how to use the program.
 * @author Hung Vu
 *
 */
@SuppressWarnings("serial")
public class NorthCheckListPanel extends JPanel{
	//Display step checklist to use program.
	/**
	 * Indicate the purpose of region.
	 */
	private static final JLabel MY_CHECK_LIST = new JLabel("Check List");
	/**
	 * First step in using a program.
	 */
	private static final JLabel MY_REQUIREMENT_1 = new JLabel("1) Choose folder directory. Not completed.");
	/**
	 * Second step in using a program
	 */
	private static final JLabel MY_REQUIREMENT_2 = new JLabel("2) Set the time you want to change the wall paper."
			+ "Not complete.");
	/**
	 * Constructor.
	 */
	public NorthCheckListPanel() {
		//Panel properties
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//Define size so the panel can appear properly.
		//set final for WxH later
		setPreferredSize(new Dimension(100,50));
		
		//Add component.
		add(MY_CHECK_LIST);
		add(MY_REQUIREMENT_1);
		add(MY_REQUIREMENT_2);
	}
	/**
	 * Change requirement (not completed -> completed).
	 * @param theRequirementNum indicates which label is going to be changed: <br>
	 * 							1 for requirement 1) <br>
	 * 							2 for requirement 2)
	 */
	public static void requirementSetter(int theRequirementNum) {
		//If something is achieved, update the check list.
		if(theRequirementNum == 1) {
			MY_REQUIREMENT_1.setText("1) Choose folder directory. Completed");
		} else if (theRequirementNum == 2) {
			MY_REQUIREMENT_2.setText("2) Set the time you want to change the wall paper. Completed");
		}
	}
}
