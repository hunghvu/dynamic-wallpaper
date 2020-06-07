package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
/**
 * This class creates a window frame to contains panel.
 * @author Hung Vu
 *
 */
@SuppressWarnings("serial")
public class WindowFrame extends JFrame{
	/**
	 * Temporary frame size
	 */
	public static final int SIZE = 960;
	
	public WindowFrame() {
		
		//Frame properties.
		setTitle("Dynamic Wallpaper");
		setSize(SIZE, SIZE);
		setVisible(true);
		setResizable(false);
		
		//Add components to frame.
		getContentPane().setLayout(new BorderLayout());	
		getContentPane().add(new LeftStartPausePanel(), BorderLayout.WEST);
		getContentPane().add(new MiddleSettingPanel(), BorderLayout.CENTER);
		getContentPane().add(new RightTextPanel(), BorderLayout.EAST);
		getContentPane().add(new SouthDirPanel(), BorderLayout.SOUTH);
		getContentPane().add(new NorthCheckListPanel(), BorderLayout.NORTH);

		
	}
}
