package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class creates right/text panel, to display information (log and time list).
 * @author Hung Vu
 *
 */
@SuppressWarnings("serial")
public class RightTextPanel extends JPanel implements ActionListener{
	
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
	private static final JScrollPane SCROLL_TIME_LIST = new JScrollPane (MY_TEXT_TIME_LIST);
	
	/**
	 * Button to clear LOG area.
	 */
	private static final JButton MY_CLEAR_LOG_BUTTON = new JButton("Clear LOG");
	
	/**
	 * Button to clear TIME LIST.
	 */
	private static final JButton MY_CLEAR_TIME_LIST_BUTTON = new JButton("Clear TIME LIST");
	
	/**
	 * Sub panel for clear log button. Aesthetic purpose (avoiding layout component resize).
	 */
	private static final JPanel MY_CLEAR_LOG_PANEL = new JPanel();
	
	/**
	 * Sub panel for clear time list button. Aesthetic purpose (avoiding layout component resize).
	 */
	private static final JPanel MY_CLEAR_TIME_LIST_PANEL = new JPanel();
	
	/**
	 * LOG area label.
	 */
	private static final JLabel MY_LOG_LABEL = new JLabel("LOG:");
	
	/**
	 * TIME LIST area label. 
	 */
	private static final JLabel MY_TIME_LIST_LABEL = new JLabel("TIME LIST:");
	
	/**
	 * Constructor.
	 */
	public RightTextPanel() {
		
		//Panel properties.
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Component properties.
		MY_TEXT_LOG.setEditable(false);
		MY_TEXT_TIME_LIST.setEditable(false);
		MY_CLEAR_LOG_BUTTON.addActionListener(this);
		MY_CLEAR_TIME_LIST_BUTTON.addActionListener(this);
		MY_CLEAR_LOG_PANEL.add(MY_CLEAR_LOG_BUTTON);
		MY_CLEAR_TIME_LIST_PANEL.add(MY_CLEAR_TIME_LIST_BUTTON);
		//Center aligned labels.
		MY_LOG_LABEL.setAlignmentX(CENTER_ALIGNMENT);
		MY_TIME_LIST_LABEL.setAlignmentX(CENTER_ALIGNMENT);
		
		
		//Add components to panel.
		add(MY_LOG_LABEL);
		add(SCROLL_LOG);
		add(MY_CLEAR_LOG_PANEL);
		add(MY_TIME_LIST_LABEL);
		add(SCROLL_TIME_LIST);
		add(MY_CLEAR_TIME_LIST_PANEL);
		
	}
	
	/**
	 * Text setter for text area.
	 * @param theIndicator Indicate which text area is going to be changed: <br>
	 * 						LOG for MY_TEXT_LOG <br>
	 * 						TIME_LIST for MY_TEXT_TIME_LIST
	 */
	public static void textSetter(String theIndicator, String theMsg) {
		
		if(theIndicator == "LOG") {
			
			MY_TEXT_LOG.append(theMsg);
			
		} else if (theIndicator == "TIME_LIST") {
			
			MY_TEXT_LOG.append(theMsg);
			MY_TEXT_TIME_LIST.setText(MiddleSettingPanel.getTimeList().toString());
			
		} else { //for internal use.
			
			System.out.println("Wrong parameters. Check available option list again.");
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent theE) {
		//Clear log when button is pressed
		if(theE.getSource() == MY_CLEAR_LOG_BUTTON) {
			
			MY_TEXT_LOG.setText("");
			
		} else if (theE.getSource() == MY_CLEAR_TIME_LIST_BUTTON) { //Clear time list (display + storage) when pressed
			
			MiddleSettingPanel.resetTimeList();
			NorthCheckListPanel.requirementSetter(20);
			MY_TEXT_TIME_LIST.setText("");
			
		} 
		
	}

}
