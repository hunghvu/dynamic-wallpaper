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

import java.io.File;
import java.util.Arrays;

/**
 * This class return an array of file (path) in given directory.
 *
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "PMD.AssignmentToNonFinalStatic", "PMD.MethodReturnsInternalArray",
    "PMD.CommentSize" })
//Ignore comment size (GPL copyright notice).
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

  // Temporary, as of 09/05/20:
  // Class: Done Recomment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  // Ignore MethodsReturnInternalArray.
  // Ignore Assignment to non final static.

}
