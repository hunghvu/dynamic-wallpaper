package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
 * @author Study
 *
 */
@SuppressWarnings("serial")
public class MiddleSettingPanel extends JPanel implements ActionListener{
//	//test accessibility to SouthDir panel.
//	private JButton myTester = new JButton("Test");
	/**
	 * Save the time to change wallpaper.
	 */
	private static final JButton MY_SAVE_TIME = new JButton("Save");
	/**
	 * Clear text field.
	 */
	private static final JButton MY_CLEAR_TIME = new JButton("Clear Fields");
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
		setBackground(Color.BLACK);
		
		//Properties of components.
//		myTester.addActionListener(this);
		MY_SAVE_TIME.addActionListener(this);
		MY_CLEAR_TIME.addActionListener(this);
		MY_HOUR.setPreferredSize(new Dimension (20,20));
		MY_MINUTE.setPreferredSize(new Dimension (20,20));
		
		//Create sub panel for South region. BorderLayout only allow 1 component per region.
		JPanel subSouth = new JPanel();
		subSouth.add(new JLabel("Hour (00-24):"));
		subSouth.add(MY_HOUR);
		subSouth.add(new JLabel("Minute (00-60):"));
		subSouth.add(MY_MINUTE);
		subSouth.add(MY_SAVE_TIME);
		subSouth.add(MY_CLEAR_TIME);
		
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
	@Override
	public void actionPerformed(ActionEvent theE) {
//		//Tester
//		if(theE.getSource() == myTester && SouthDirPanel.fileGetter() != null) {
//			//test static class
//			setBackground(Color.BLUE);
//		} else 
		if (theE.getSource() == MY_SAVE_TIME) {
			if(MY_HOUR.getText().length() == 2 && MY_MINUTE.getText().length() == 2)
				try {
					int hour = Integer.parseInt(MY_HOUR.getText());
					int minute = Integer.parseInt(MY_MINUTE.getText());
					if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
						RightTextPanel.textSetter("TIME_LIST");
						NorthCheckListPanel.requirementSetter(2);
					} else {
						RightTextPanel.textSetter("LOG");
					}
				} catch (Exception e){
					RightTextPanel.textSetter("LOG");
				}
			else {
				RightTextPanel.textSetter("LOG");
			}
		}
		
	}
}
