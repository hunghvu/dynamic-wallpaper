package model;

import java.io.File;
import java.util.Arrays;

/**
 * This class return an array of file (path) in given directory.
 *
 * @author Hung Vu
 *
 */
@SuppressWarnings({ "PMD.AssignmentToNonFinalStatic", "PMD.MethodReturnsInternalArray" })
public class FileArray {
  /**
   * Array of file.
   */
  private static File[] myFileList;

  /**
   * Constructor.
   *
   * @param theDir given folder directory
   */
  public FileArray(final File theDir) {

    myFileList = theDir.listFiles();

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

  // Temporary, as of 09/04/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore MethodsReturnInternalArray.
  // Ignore Assignment to non final static. (Object is initialized only once)

}
