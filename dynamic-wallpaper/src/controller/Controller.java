package controller;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.FileArray;
import model.TimeList;
import view.WindowFrame;

public class Controller implements PropertyChangeListener{
  private static final TimeList MY_TIME = new TimeList();

  private static FileArray myFile;
  private WindowFrame myFrame;

  public Controller() { 
    myFile.addListener(this);
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {

        myFrame = new WindowFrame();

      }

    });
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // TODO Auto-generated method stub

  }
}
