/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TA.steps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SetupSteps {
    
    // Gunakan 'public static' agar driver ini dibagikan ke semua file Steps
    public static AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        // Mencegah Appium membuka double session
        if (driver == null) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName("Android");
            options.setDeviceName("Android Emulator");
            options.setAutomationName("UiAutomator2");
            options.withBrowserName("Chrome");
            options.setCapability("appium:chromedriverExecutable", "C:\\Drivers\\chromedriver.exe");
            
            options.setNoReset(true);
            options.setAutoGrantPermissions(true);

            // --- JURUS ANTI CRASH CHROME DI EMULATOR ---
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--no-first-run");
            chromeOptions.addArguments("--disable-fre");
            chromeOptions.addArguments("--no-sandbox");             // Mengatasi masalah limitasi sandbox emulator
            chromeOptions.addArguments("--disable-dev-shm-usage");  // Mencegah Chrome kehabisan shared memory
            
            options.setCapability("goog:chromeOptions", chromeOptions);

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // Reset driver ke null setelah ditutup
        }
    }
}
/**
 *
 * @author KnightlyTech
 */

