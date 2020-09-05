package model;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Interface (native library mapping) to change wallpaper.
 * 
 * @author Hung Vu
 *
 */
public interface JnaWallpaper extends StdCallLibrary {
  /**
   * Field to load USER32 library. loadLibrary deprecated, use load instead
   */
  JnaWallpaper INSTANCE = (JnaWallpaper) Native.load(
      
      "user32", 
      JnaWallpaper.class
      
      );

  /**
   * Map function (Based on JNA/Microsoft document).
   * @param theUiAction Action to perform on UI
   * @param theUiParam Not used
   * @param thePath Path of a picture for desktop wallpaper
   * @param theFWinIni Not used
   * @return a boolean, not used
   */
  //This is function mapping, ignore warning.
  @SuppressWarnings("PMD.MethodNamingConventions")
  boolean SystemParametersInfoA(
      
      int theUiAction, 
      int theUiParam, 
      String thePath, 
      int theFWinIni

      );
}

// Temporary, as of 09/04/20:
// Class: Done Recomment.
// Class: Done Checkstyle.
// Class: Done PMD.