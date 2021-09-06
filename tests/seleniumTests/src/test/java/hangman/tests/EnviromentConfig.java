package hangman.tests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;

public class EnviromentConfig {
    private static String index = "http://localhost:8080/TechnicalJavaTest/";

    private static List<String> urls = Arrays.asList(index);

    /**
     * Return index url
     */
    public static String getIndex() {
        return index;
    }

    public static List<String> getUrls() {
        return urls;
    }

    /**
     * Set the WebDriver to use.
     * The default web driver is Chrome. You can choose
     * other driver by using {@link #getDriver(int)}.
     */
    public static WebDriver getDriver() {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        java.net.URL path = loader.getResource("chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", path.getPath());
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        return driver;
    }

    /**
     * As default {@link #getDriver()} use Chrome as driver. If you want use other supported
     * driver, you can use {@link #getDriver(int)} and choose the driver to use.
     * Supported drivers: Chrome; Opera.
     * 
     * @param browser 1: Opera.
     */
    public static WebDriver getDriver(int browser) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        java.net.URL path;
        WebDriver driver;

        switch (browser) {
            case 1:
                path = loader.getResource("operadriver.exe");
                System.setProperty("webdriver.opera.driver", path.getPath());
                driver = new OperaDriver();
                break;
            default:
                path = loader.getResource("chromedriver.exe");
                System.setProperty("webdriver.chrome.driver", path.getPath());
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        return driver;
    }
}
