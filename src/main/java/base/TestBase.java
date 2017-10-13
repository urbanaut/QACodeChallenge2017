package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected static WebDriver driver;
    private static String chromeDriverPath = "src\\main\\resources\\drivers\\chromedriver.exe";
    public boolean mobileTest = false;
    private String language = "English";

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
//        options.addArguments("--start-maximized");
//        options.addArguments("--window-size=800,600");

        options.addArguments("chrome.switches","--disable-extensions"); //Removes popup reminder for disabling extensions
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        String deviceName = "Samsung Galaxy S4";
//        String deviceName = "Apple iPad";
        Map<String, String> devices =  new HashMap<>();
        devices.put("deviceName", deviceName);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("mobileEmulation", devices);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, mobileEmulation);
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        if (mobileTest)
            driver = new ChromeDriver(capabilities);
        else
            driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        switch (language) {
            case "English":
                driver.navigate().to("https://www.nuskin.com/content/nuskin/en_US/ageloc-me-assessment.html#/you-start");
                break;
            case "French":
                driver.navigate().to("https://www.nuskin.com/content/nuskin/fr_BE/ageloc-me-assessment.html#/you-start");
                break;
            case "Dutch":
                driver.navigate().to("https://www.nuskin.com/content/nuskin/nl_BE/ageloc-me-assessment.html#/you-start");
                break;
        }
    }
}
