# Add Number App - Automated Testing

This project automates testing for a simple Android application that adds two numbers.

## Prerequisites

Ensure the following tools and dependencies are installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Apache Maven](https://maven.apache.org/install.html)
- [Appium Server](http://appium.io/docs/en/about-appium/intro/)
- Android Device or Emulator with USB Debugging Enabled
- The APK file for the Add Number App (app-debug.apk)

### Test Cases

The script includes the following test cases:

    Handle Launch Popup:
        Automatically closes any initial pop-up with id = android:id/parentPanel.

    Addition Functionality Test:
        Inputs two numbers, taps the "Add" button, and verifies the result.

    Verification of Result:
        Extracts the numeric result from the label text to verify accuracy.

    Other Tests (Add any additional functionality tests as required).

    