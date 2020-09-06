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

/**
 * Return error code in validating given timestamp.
 * This is used to trigger proper listener action.
 * @author Hung Huu Vu
 *
 */
@SuppressWarnings({ "PMD.CommentSize" })
//Ignore comment size (GPL copyright notice).
public enum CodeError {
  
  // Error in TimeList.
  TIME_OUT_OF_RANGE, TIME_INVALID_CHAR, TIME_INVALID_FORMAT, 
  
  // Error in RandomFromNet.
  NET_NO_CONNECTION;

}

// Temporary, as of 09/05/20:
// Class: Done Recomment.
// Class: Done Checkstyle.
// Class: Done PMD.