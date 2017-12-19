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
    protected boolean mobileTest = false;
    private static final String DEVICE_NAME = "Galaxy S5";

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--no-sandbox");
        options.addArguments("chrome.switches","--disable-extensions"); //Removes popup reminder for disabling extensions

        Map<String, String> devices =  new HashMap<>();
        devices.put("deviceName", DEVICE_NAME);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("mobileEmulation", devices);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, mobileEmulation);

        if (mobileTest)
            driver = new ChromeDriver(capabilities);
        else
            driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}