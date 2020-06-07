package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.FileArray;

/**
 * This class creates south region panel, which includes Aplly button
 * and directory browser.
 * @author Hung Vu
 *
 */
@SuppressWarnings("serial")
public class SouthDirPanel extends JPanel implements ActionListener{
	
	/**
	 * Button to open file chooser.
	 */
	private static final JButton MY_DIR_BROWSE = new JButton("Browse");
	
	/**
	 * Apply button.
	 */
	private static final JButton MY_APPLY = new JButton("Apply");
	
	/**
	 * Directory display. Size is managed by layout.
	 */
	private static final JTextArea MY_TEXT = new JTextArea(1, 1);
	
	/**
	 * Store picture folder path. It will be initialized after a user selects directory
	 */
	private static File myTempFile;
	
	/**
	 * Store an array of picture (file) of given folder. It will be initialized after a user selects directory
	 */
	private static FileArray myFileArray = null;
	
	/**
	 * Constructor.
	 */
	public SouthDirPanel() {
		//Panel properties
		//W = 1, H = 2
		setLayout(new GridLayout(3, 1));
		//Define size so South panel can appear properly
		setPreferredSize(new Dimension(100,200));		
		
		//Component properties
		MY_TEXT.setEditable(false);
		MY_DIR_BROWSE.addActionListener(this);
		MY_APPLY.addActionListener(this);
		
		//Add component to panel
		add(MY_TEXT);
		add(MY_DIR_BROWSE);
		add(MY_APPLY);
		
	}
	
	/**
	 * Getter for directory.
	 * @return file path.
	 */
	public static  File fileGetter() {
		return myTempFile;
	}
	
	public static FileArray getFileArray() {
		
		return myFileArray;
		
	}
	@Override
	public void actionPerformed(ActionEvent theE) {
		
		if(theE.getSource() == MY_DIR_BROWSE) {
			
			//File chooser to select folder directory.
			JFileChooser myChooser = new JFileChooser();
			myChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			//"this" indicate the SouthLink panel is parent window
			int returnVal = myChooser.showOpenDialog(this);
			
			//Close window and return absolute folder path when pressing open.
			//Print folder path to panel.
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				
				myTempFile = myChooser.getSelectedFile().getAbsoluteFile();
				MY_TEXT.setText("Directory: " + myTempFile);
				NorthCheckListPanel.requirementSetter(11);

			}
			
		} else if (theE.getSource() == MY_APPLY) {
			
			myFileArray = new FileArray(myTempFile);
			
		}
		
	}
}
