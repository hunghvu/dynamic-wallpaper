# Dynamic Wallpaper
- A small program, written in Java, that allows users to change desktop wallpaper at specific time in the day.
- Users will first choose their picture folder, then the program will **randomly** choose one image and set it as wallpaper.
- Initially, I'm learning on how Java interacts and modifies the OS, and coincidentally, my sister asked whether I know any kind of program that randomly changes wallpaper. Well, I don't know, so I decided to create one myself and here it is.

## Latest version
**1.0.0.0 (06/16/2020)**

## The GUI
![](https://github.com/hunghvu/dynamic-wallpaper/blob/master/dynamic-wallpaper/ImageForREADME/Guide.png)

## Note
- This program has been tested only on Window 10.
- This program is my out-of-class personal project, feel free to take a look over the code. I hope it can be helpful for you!
- The project and executable files uses/packages [JNA](https://github.com/java-native-access/jna) open library. I don't own nor be a contributor to the library. All rights reserved to their respective owners/contributors/creators.

## What I have struggle and learned about via this project
Major. These are new to me, so it's really nice to learn about them.
- The way to package .jar and runnable .jar, and how it performs based on my code (E.g: images must be inside a package to be exported to runnable jar, etc).
- How to convert .jar to .exe using Launch4j by Grzegorz Kowal.
- How to install and use external library ([JNA](https://github.com/java-native-access/jna)).
- How Java interact and modifies the OS.
- How to use GitHub (push, pull, backup, comment, etc).
- How to use [VisualVM](https://visualvm.github.io/features.html) by Jiri Sedlacek, and Tomas Hurka to observe heap usage of VM.

Minor. I already learned these concepts before, and this project help me improve my knowledge on them. Still, there is a long way to go.
- Garbage collection in Java (to fix memory leak issue).
- Static concept in Java.
- GUI design (properties of components, layouts, etc).
- Concurrency, thread design.
- Way to use checkstyle, PMD, surpress warning.

## License
This project is protected under MIT license. <br>
Copyright (c) 2020 Hung Huu Vu
