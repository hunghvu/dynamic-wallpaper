package view;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * This class creates a left/start-pause panel.
 * @author Hung Vu
 *
 */
@SuppressWarnings("serial")
public class LeftStartPausePanel extends JPanel{

	private JButton myStart = new JButton("Run");
	private JButton myPause = new JButton("Pause");
	public LeftStartPausePanel() {
		setLayout(new GridLayout(1, 2));
		add(myStart);
		add(myPause);
	}
}
