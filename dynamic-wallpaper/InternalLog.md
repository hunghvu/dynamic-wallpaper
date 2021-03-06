# Internal development log

## [1.0.1.2] - 09-19-2020
-   Add import/export config functions.
-   Change look and feel based on native OS.
-   Fix all known bugs in **1.0.1.1**.

## [1.0.1.1] - 09-07-2020
- Know bug(s):
    - ~~The program must be in running state before being stopped to un-grey-out the check box.~~ (fixed - 09/19/20).
    - ~~The Apply button doesn't grey out the check box when the box option is chosen.~~ (fixed - 09/19/20).
        - ~~Step to reproduce: Choose the box -> set time -> apply -> stop -> apply again. This can cause problems since it affects the functionality of Stop button, which results in threads not being stopped properly.~~


## [1.0.0.2] - 06-17-2020
- Contains only an executable (.exe) file.
- Compared to **v1.0.0.1**, there is no change in the source code.
- Change JDK link to default folder: C:\Program Files\Java

## [1.0.0.1] - 06-17-2020
- Packaged version, JDK 14.0.1 is included to improve portability.
- Change JDK link to relative path.

## [1.0.0.0] - 2020-06-16
- The first (final) release.
___

## 2020/09/19
-   Finish implementing import/export function.
-   Refactor RightTextPanel to reduce code length and complexity.
-   Fix all known bugs in **1.0.1.1**.

## 2020/09/14
-   Change look and feel.
-   Add import/export config buttons.
-   Plan:
    -   Vi UI.
    -   Multi screen support.
___

## 2020/09/07
- Fix the issue where threads may not be stopped properly due to the state (isSelected) of the check box.
- Update comments, complete the update. Ready to be released.
- Next plan:
    - ~~Implement save/import/export config function.~~ (09/19/2020 - Done).
    - Implement auto generate timestamp.
    - Different API call option.
    - Auto start with OS (optional).
    - Change to specific background at a specific time.
    - Live background (maybe too overwhelming).

## 2020/09/05
- Large scale refactoring in Controller, RandomFromNet, RandomFromFolder and minor refactoring in other classes.
- Basically everything is completed. Still, one more additional day is needed to test user experience (real-use).
- This can be seen as RC1.
- Perhaps, version tag should be change from 1.0.0.x to 1.0.y.z (y is "large" version, z indicates whether JDK is included).

## 2020/09/04
- Multiple refactoring to view and model.
- Preliminary comments for controller, PMD + Checkstyle for TimeList, enum classes, FileArray, JnaWallpaper.
- Fix an unexpected behavior when user press Apply button multiple times, by introducing flag to "Apply" action.
- Fix a wrong path to create temporary lock file. It should be in system "temp" folder.
- Successfully cache the image on local system.
- Basically complete implementation of the new feature. Use image from the Internet to set as wallpaper. However, still need to test and refactoring.
- Add copyright notice to each file.

## 2020/09/03
- Display message panel when a user minimize the program.
- Fix time list not display minute properly (E.g: 17:2 to -> 17:02).
- Add button to get picture from Internet. Preliminary setup for this new feature.

## 2020/09/02
- Refactor methods in MainProgram, move all to WindowFrame.
- Refactor redundant code in WindowFrame.
- Fix JVM not fully terminated when the program is exited.
- There is a bug which preview wallpaper isn't working when changing folder path.


## 2020/09/01
- Introduce controller, observer & observable pattern.
___

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
