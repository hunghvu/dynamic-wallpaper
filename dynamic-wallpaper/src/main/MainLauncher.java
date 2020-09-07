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

package main;

import controller.Controller;

/**
 * The main class to run a program.
 * This program will changes desktop wallpaper
 * based given timestamp.
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.UseUtilityClass",
    "PMD.DataflowAnomalyAnalysis" })
//Ignore comment size (GPL copyright notice).
public class MainLauncher {
  
  /**
   * Method to run dynamic wallpaper program.
   * @param theArgs argument to run the program.
   */
  public static void main(final String[] theArgs) {
    
    // Ignore Checkstyle warnings.
    final Controller appController = new Controller();

  }

  
  // Done, as of 09/07/20:
  // Class: Done Comment.
  // Class: Done Checkstyle.
  // Class: Done PMD.
  
  // Ignore Use utility class.
  // Ignore Data flow anomaly analysis.
  
  // Ignore Checkstyle warnings.

}
