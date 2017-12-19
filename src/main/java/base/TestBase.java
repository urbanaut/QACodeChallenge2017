package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected static WebDriver driver;
    protected boolean mobileTest = true;

    public static String DATA_FILE="";
    private static String DRIVER_PATH="";
    public static String RESULTS_FILE="";
    public static String SUMMARY_FILE="";
    public static String SHEET_NAME="";
    private static String DEVICE_NAME="";

    private static final String PROPERTIES_FILE = "src\\main\\resources\\test.properties";

    @BeforeSuite
    public void setConstants() throws Exception {
        InputStream input = new FileInputStream(PROPERTIES_FILE);
        Properties props = new Properties();
        props.load(input);

        DATA_FILE = props.getProperty("DATA_FILE");
        DRIVER_PATH = props.getProperty("DRIVER_PATH");
        RESULTS_FILE = props.getProperty("RESULTS_FILE");
        SUMMARY_FILE = props.getProperty("SUMMARY_FILE");
        SHEET_NAME = props.getProperty("SHEET_NAME");
        DEVICE_NAME = props.getProperty("DEVICE_NAME");
    }

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);

        if (mobileTest) {
            Map<String, String> devices =  new HashMap<>();
            devices.put("deviceName", DEVICE_NAME);
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("mobileEmulation", devices);

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, mobileEmulation);
            driver = new ChromeDriver(capabilities);
        }
        else {
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--no-sandbox");
            options.addArguments("chrome.switches","--disable-extensions"); //Removes popup reminder for disabling extensions
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}