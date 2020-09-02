package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Arrays;
import java.util.Observable;

/**
 * This class return an array of file (path) in given directory.
 *
 * @author Hung Vu
 *
 */
@SuppressWarnings({"PMD.AssignmentToNonFinalStatic", "PMD.MethodReturnsInternalArray"})
public class FileArray {
  /**
   * Array of file.
   */
  private static File[] myFileList;

  private PropertyChangeSupport myFileNotifier;

  /**
   * Constructor.
   *
   * @param theDir given folder directory
   */
  public FileArray(final File theDir) {
    
    //Initialize notifier (01/09)
    myFileNotifier = new PropertyChangeSupport(this);
    myFileNotifier.firePropertyChange("myFileList", FileArray.myFileList, theDir.listFiles());
    
    myFileList = theDir.listFiles();
    

  }
  
  //add listener(01/09)
  public void addListener (PropertyChangeListener theObserver) {

    myFileNotifier.addPropertyChangeListener(theObserver);

  }

  /**
   * Return array of file path.
   *
   * @return array of file path.
   */
  public File[] getFileArray() {

    return myFileList;

  }

  /**
   * String representation of FileArray. <br>
   * Only use for internal checking purpose.
   */
  @Override
  public String toString() {

    return Arrays.toString(myFileList);

  }

  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore MethodsReturnInternalArray.
  // Ignore Assignment to non final static. (Object is initialized only once)

}
