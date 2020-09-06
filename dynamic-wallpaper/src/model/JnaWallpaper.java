/*
 * This program (Dynamic Wallpaper) changes desktop background based on provided timestamp.
 * Copyright (C) 2020  Hung Huu Vu <hunghvu2017@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package model;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Interface (native library mapping) to change wallpaper.
 * This is from JNA library.
 * 
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "PMD.CommentSize" })
//Ignore comment size (GPL copyright notice).
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

// Temporary, as of 09/05/20:
// Class: Done Recomment.
// Class: Done Checkstyle.
// Class: Done PMD.