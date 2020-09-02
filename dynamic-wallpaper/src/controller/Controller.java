package controller;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import model.AutoUpdateTime;
import model.FileArray;
import model.TimeList;
import view.WindowFrame;

public class Controller implements PropertyChangeListener{
  
  // 02/09
  private static final TimeList MY_TIME = new TimeList();
  
  private static AutoUpdateTime myAutoRun;
  private static FileArray myFile;
  
  private WindowFrame myFrame;

  public Controller() { 
    
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {

        myFrame = new WindowFrame();

      }

    });
  }
  
  public static void createFileArray(File theDir) {
    
    myFile = new FileArray(theDir);
//    myFile.addListener(this);
    
  }
  
  public static void startBackgroundChanger() {
    // Stop old instance of wallpaper and preview setter timer before creating a new
    // one. Set to null to persuade GC.
    if (myAutoRun != null) {

      myAutoRun.autoUpdate(false);
      myAutoRun = null;

    }
    
    // Garbage collection.
    System.gc();
    
    // Run the program automatically.
    myAutoRun = new AutoUpdateTime(myFile);
  }
  
  public static void stopBackgroundTask() {
    myAutoRun.autoUpdate(false);
  }
  
  public static AutoUpdateTime getAuto() {
    return myAutoRun;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // TODO Auto-generated method stub

  }
}
