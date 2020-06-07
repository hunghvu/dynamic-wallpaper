package view;

import model.TimeList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates a middle panel to preview image 
 * and apply background setting.
 * @author Hung Vu
 *
 */
@SuppressWarnings("serial")
public class MiddleSettingPanel extends JPanel implements ActionListener{
	
	/**
	 * Store list of given time.
	 */
	private static final TimeList MY_TIME_LIST = new TimeList();
	
	/**
	 * Save the time to change wallpaper.
	 */
	private static final JButton MY_SAVE_TIME = new JButton("Save Fields");
	
	/**
	 * Clear text field.
	 */
	private static final JButton MY_CLEAR_TIME = new JButton("Clear Fields");
	
	/**
	 * Delete given time from time list.
	 */
	private static final JButton MY_DELETE_GIVEN_TIME = new JButton("Delete Given Time");
	
	/**
	 * Hour (Format: HH from 00-23).
	 */
	private static final JTextField MY_HOUR = new JTextField();
	
	/**
	 * Minute (Format: MM from 00-59).
	 */
	private static final JTextField MY_MINUTE = new JTextField();
	
	/**
	 * Constructor
	 */
	public MiddleSettingPanel() {
		//Properties of panel.
		setLayout(new BorderLayout());
//		setBackground(Color.BLACK);
		
		//Properties of components.
		MY_SAVE_TIME.addActionListener(this);
		MY_CLEAR_TIME.addActionListener(this);
		MY_DELETE_GIVEN_TIME.addActionListener(this);
		MY_HOUR.setPreferredSize(new Dimension (20,20));
		MY_MINUTE.setPreferredSize(new Dimension (20,20));
		
		//Create sub panel for South region. BorderLayout only allow 1 component per region.
		JPanel subSouth = new JPanel();
		subSouth.add(new JLabel("Hour (00-23):"));
		subSouth.add(MY_HOUR);
		subSouth.add(new JLabel("Minute (00-59):"));
		subSouth.add(MY_MINUTE);
		subSouth.add(MY_SAVE_TIME);
		subSouth.add(MY_CLEAR_TIME);
		subSouth.add(MY_DELETE_GIVEN_TIME);
		
		//Add buttons to panel.
//		add(myTester, BorderLayout.NORTH);
		add(subSouth, BorderLayout.SOUTH);
	}
	
	/**
	 *
	 * @return hour in text field
	 */
	public static String getMyHourText() {
		
		return MY_HOUR.getText();
		
	}
	
	/**
	 *
	 * @return minute in text field
	 */
	public static String getMyMinuteText() {
		
		return MY_MINUTE.getText();
		
	}
	
	/**
	 * 
	 * @return ArrayList of time.
	 */
	public static TimeList getTimeList(){
		
		return MY_TIME_LIST;
		
	}
	
	/**
	 * Reset time list.
	 */
	public static void resetTimeList() {
		
		MY_TIME_LIST.clearTimeList();
		
	}

	@Override
	public void actionPerformed(ActionEvent theE) {
		
		if (theE.getSource() == MY_SAVE_TIME) {
			
			//See if string is in ## format (length == 2)
			if(getMyHourText().length() == 2 && getMyMinuteText().length() == 2) {
				
				try {
					
					//Turn hour and minute from string to int. If fail then throws exception
					int hour = Integer.parseInt(getMyHourText());
					int minute = Integer.parseInt(getMyMinuteText());
					
					//Hour (00-23), minute (00-59)
					if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {						
						
						//Build list of time
						boolean addResult = MY_TIME_LIST.addTime(getMyHourText() + ":" + getMyMinuteText());
						
						//Display state
						if (addResult == true) {
							
							RightTextPanel.textSetter("TIME_LIST", "Time list is succesfully updated. \n");
							NorthCheckListPanel.requirementSetter(21);
							
						} else {
							
							RightTextPanel.textSetter("TIME_LIST", "Cannot add duplicate time! Please try again. \n");
							
						}
						
					} else {
						
						RightTextPanel.textSetter("LOG", "Invalid time! Please try again.\n");
						
					}
					
				} catch (Exception e){ //throw exception when string can't be parsed.
					
					RightTextPanel.textSetter("LOG", "Invalid time! Please try again.\n");
					
				}
				
			}
			else {
				
				RightTextPanel.textSetter("LOG", "Invalid time! Please try again.\n");
				
			}
			//Reset text field.
			MY_HOUR.setText("");
			MY_MINUTE.setText("");
			
		} else if (theE.getSource() == MY_CLEAR_TIME) { //Clear text fields 
			
			MY_HOUR.setText("");
			MY_MINUTE.setText("");
			
		} else if (theE.getSource() == MY_DELETE_GIVEN_TIME) { //Delete given time (from string time list) and
																//update display

			if (MY_TIME_LIST.deleteTime(getMyHourText() + ":" + getMyMinuteText()) == true) {
				
				RightTextPanel.textSetter("TIME_LIST", "Delete time successfully. \n");
				
			} else {
				
				RightTextPanel.textSetter("LOG", "Given time is not in the list. \n");
				
			}
			
			//Check if list is empty to update requirement checker (north region)
			if(MY_TIME_LIST.isEmpty()) {
				
				NorthCheckListPanel.requirementSetter(20);
				
			}

			//Reset text field
			MY_HOUR.setText("");
			MY_MINUTE.setText("");
		}
		
	}
	//Save to to list for model
}
