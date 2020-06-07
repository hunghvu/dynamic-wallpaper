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
	 * 							10 for requirement 1) Not completed <br>
	 * 							11 for requirement 1) Completed <br>
	 * 							20 for requirement 2) Not completed <br>
	 * 							21 for requirement 2) Completed 
	 */
	public static void requirementSetter(int theRequirementNum) {
		
		//If something is achieved, update the check list.
		if (theRequirementNum == 10) {
			
			MY_REQUIREMENT_1.setText("1) Choose folder directory. Not completed.");
			
		} else if(theRequirementNum == 11) {
			
			MY_REQUIREMENT_1.setText("1) Choose folder directory. Completed");
			
		} else if (theRequirementNum == 20) {
			
			MY_REQUIREMENT_2.setText("2) Set the time you want to change the wall paper. Not completed");
			
		} else if (theRequirementNum == 21) {
			
			MY_REQUIREMENT_2.setText("2) Set the time you want to change the wall paper. Completed");
			
		} else { //for internal use only
			
			System.out.println("Wrong option, please check parameter again.");
			
		}
		
	}
}
