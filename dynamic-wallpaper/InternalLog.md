# Internal development log
### [1.0.0.2] - 06-17-2020 
- Contains only an executable (.exe) file.
- Compared to **v1.0.0.1**, there is no change in the source code.
- Change JDK link to default folder: C:\Program Files\Java

### [1.0.0.1] - 06-17-2020 
- Packaged version, JDK 14.0.1 is included to improve portability.
- Change JDK link to relative path.

## [1.0.0.0] - 2020-06-16
- The first (final) release. 

## 2020/06/16
- Minor changes to printed messages.
- Change preview current image to next image.
- Update comment, PMD, and checkstyle.

## 2020/06/15
- Fix memory leak issue in displaying previewed image after each wallpaper change. (Auto update and middle panel preview).
- Update comment, PMD, and checkstyle.

## 2020/06/14
- First attempt to create .exe file using Launch4j by Grzegorz Kowal.
- Add file lock to limit instance of the program to 1.
- Add a note - folder should contains only pictures.
- Move window listener from frame back to main.
- Update some message.
- Move all new line character to right panel.
- Reorganize the code.
- Change preview image size.
- Update comment, PMD, and checkstyle.

# 2020/06/13
- Change some text messages, and add space between them.
- Add run-status label.
- Add stop button.
- Delete Start and Pause button
- Minor changes to structure of frame and main (move some frame properties and window listener to frame).
- Add set extended state (bring frame to the front after restoring).
- Add double click to tray icon.
- Add copyright label.
- Update comment, PMD, and checkstyle.

# 2020/06/12
- Initial completion of re-commenting, checkstyle, and PMD.
- Add minimizing to system tray function.
- First attempt to create .jar and runnable .jar file

# 2020/06/11
- Add condition checking to Apply button.
- First attempt to use external [JNA](https://github.com/java-native-access/jna) library in order to change desktop wallpaper. 

# 2020/06/07
- First attempt to implement auto update for the program. (automatically choose wallpaper).
- First attempt to display resized preview image.
- Note: Using Apply button to call set preview image for testing purpose.
- Create some (early-stage) basics for the program (sort time list, print file list, open random file, display picture, cases for the program to run, etc)

# 2020/06/04
- Create some general on-paper design for the program.
