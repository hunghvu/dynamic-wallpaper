package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * This class creates a window frame to contains panel. <br>
 * Basically, this is GUI of the program.
 * 
 * @author Hung Vu
 *
 */
@SuppressWarnings({"serial", "PMD.DoNotCallSystemExit"})
public class WindowFrame extends JFrame {
  
  /**
   * Temporary frame size.
   */
  public static final int SIZE = 960;
  
  /**
   * Constructor.
   */
  public WindowFrame() {
    
    //Call super.
    super();
    
    // Add components to frame.
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new MiddleSettingPanel(), BorderLayout.CENTER);
    getContentPane().add(new RightTextPanel(), BorderLayout.EAST);
    getContentPane().add(new SouthDirPanel(), BorderLayout.SOUTH);
    getContentPane().add(new NorthCheckListPanel(), BorderLayout.NORTH);
    
    // Frame properties.
    setTitle("Dynamic Wallpaper");
    setSize(SIZE, SIZE);
    setVisible(true);
    setResizable(false);


  }
  
  //Ignore Do not call sys exit.
}
