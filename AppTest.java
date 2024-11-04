package com.prz;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

public class AppTest {

    private AndroidDriver driver;

    // Method to return the URL for Appium server
    @SuppressWarnings("deprecation")
    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Before
    public void setUp() {
        // Using BaseOptions as per the updated Appium Inspector code
        @SuppressWarnings("rawtypes")
        var options = new BaseOptions()
            .amend("appium:automationName", "UiAutomator2")
            .amend("platformName", "Android")
            .amend("appium:platformVersion", "14")
            .amend("appium:deviceName", "R92X71K77PY")
            .amend("appium:app", "E:\\projects\\APK\\app-debug.apk")
            .amend("appium:ensureWebviewsHavePages", true)
            .amend("appium:nativeWebScreenshot", true)
            .amend("appium:newCommandTimeout", 3600)
            .amend("appium:connectHardwareKeyboard", true);

        // Initialize AndroidDriver with options
        driver = new AndroidDriver(this.getUrl(), options);
    }

    @Test
    public void addTwoPositiveNumbersTest() {
        handlePopupIfPresent();

        performAddition("5", "3", "8");
    }

    @Test
    public void addPositiveAndNegativeNumberTest() {
        handlePopupIfPresent();

        performAddition("5", "-3", "2");
    }

    @Test
    public void addTwoNegativeNumbersTest() {
        handlePopupIfPresent();

        performAddition("-5", "-3", "-8");
    }

    @Test
    public void addZerosTest() {
        handlePopupIfPresent();

        performAddition("0", "0", "0");
    }

    @Test
    public void emptyInputFieldTest() {
        handlePopupIfPresent();

        performAddition("", "5", "5");
    }

    @Test
    public void nonNumericInputTest() {
        handlePopupIfPresent();

        performAddition("abc", "5", "Invalid Input");  // Assumes app handles invalid input gracefully
    }

    private void handlePopupIfPresent() {
        try {
            // Wait for the popup to appear and switch to it
            WebElement popup = driver.findElement(By.id("android:id/parentPanel"));
            WebElement okButton = popup.findElement(By.id("android:id/button1"));

            // Tap the button on the popup
            okButton.click();
        } catch (Exception e) {
            // If the popup is not found, we can log or ignore the error
            System.out.println("No popup found or could not click the button: " + e.getMessage());
        }
    }

    private void performAddition(String num1, String num2, String expectedResult) {
        WebElement firstNumberField = driver.findElement(By.id("com.browserstack.addnumber:id/num1"));
        WebElement secondNumberField = driver.findElement(By.id("com.browserstack.addnumber:id/num2"));
        WebElement addButton = driver.findElement(By.id("com.browserstack.addnumber:id/addBtn"));
        WebElement resultLabel = driver.findElement(By.id("com.browserstack.addnumber:id/sampleLabel"));

        // Input numbers
        firstNumberField.clear();
        secondNumberField.clear();
        firstNumberField.sendKeys(num1);
        secondNumberField.sendKeys(num2);

        // Click the Add button
        addButton.click();

        // Extract the result text and verify the expected result
        String resultText = resultLabel.getText(); // Example: "Answer: 8"
        String actualResult = extractNumber(resultText);

        // Verify the result (for invalid cases, modify accordingly)
        assertEquals(expectedResult, actualResult);
    }

    private String extractNumber(String text) {
        Pattern pattern = Pattern.compile("-?\\d+"); // Regex to match one or more digits, including negative numbers
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(); // Return the first found number
        }
        return "Invalid Input"; // Return this for invalid input cases
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}