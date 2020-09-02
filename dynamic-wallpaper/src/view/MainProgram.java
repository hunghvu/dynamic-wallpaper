package view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The main class to run a program.
 * This program will changes desktop wallpaper
 * based on the time set.
 * @author Hung Vu
 *
 */
@SuppressWarnings({"PMD.DoNotCallSystemExit", "PMD.ExcessiveMethodLength",
    "PMD.LawOfDemeter", "PMD.ModifiedCyclomaticComplexity",
    "PMD.SystemPrintln", "PMD.UseUtilityClass", "PMD.DataflowAnomalyAnalysis",
    "PMD.CloseResource"})
public class MainProgram {
  
  /**
   * Method to run dynamic wallpaper program.
   * @param theArgs argument to run the program.
   */
  public static void main(final String[] theArgs) {
    
    
    
    //Initialize frame for program.
    final WindowFrame frame = new WindowFrame();

    


  }

  
  //Update 6/14 Attempt to create file lock. Done.

}
