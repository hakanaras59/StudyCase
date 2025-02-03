package BaseTest;

import Utilities.Log;
import Utilities.RunnerConfig;
import Utilities.ScreenshotHelper;
import com.thoughtworks.gauge.*;

import java.io.File;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.*;

import static junit.framework.TestCase.fail;

public class BaseTest {
    public static WebDriver driver;
    protected static int LogCounter;
    static boolean isErrorDetect;
    static int ScreenshotCounter;
    protected static String StringLogError;

    public static void LogCountManager() {

        Log.info(" -------------------------- ");
        Log.info(" Log Counter Cagirildi !!! ");

        Log.info(" Log Counter Onceki Deger ->>> " + LogCounter);
        LogCounter++;

        Log.info(" Log Counter Guncel Deger ->>> " + LogCounter);
        Log.info(" -------------------------- ");
    }
    public static void getScreenShot(File GetScreenshot) {
        if (ScreenshotCounter == 0) {
            Log.info(" Screenshot ->>> " + GetScreenshot.getName());
            Log.info(" Screenshot Counter Onceki Deger ->>> " + ScreenshotCounter);
            ScreenshotCounter++;
            Log.info(" Screenshot Counter Onceki Deger ->>> " + ScreenshotCounter);
        } else {
            Log.info(" -------------------------- ");
            Log.info(" Screenshot Counter Onceden Cagirildi !!! ->>> " + ScreenshotCounter);
            Log.info(" -------------------------- ");
        }
    }
    public static void resetAll() {

        try {
            Log.info(" Sıfırlama Islemleri Basladi");

            ScreenshotCounter = 0;
            Log.info(" Sıfırlama Islemleri Tamamlandi");

        } catch (Exception e) {
            Log.error(" Sifirlama Adiminda Hata Alindi !!! " + e.getMessage());
        }
    }

    @BeforeSuite
    public void cleanPhase() {
        ScreenshotHelper.clearScreenShots();
        ScreenshotHelper.clearJavaExeOutLogs();
    }
    @BeforeScenario
    public static void setUp() {
        isErrorDetect = false;
        String config = RunnerConfig.driverType;
        try {
            if (Objects.equals(config, "chrome")) {
                ChromeOptions options = getChromeOptions();
                ChromeDriverService driverService = ChromeDriverService.createDefaultService();
                driver = new ChromeDriver(driverService, options);
                driver.manage().window().maximize();
                Log.info(" Çözünürlük ->>> " + driver.manage().window().getSize());

            } else if(Objects.equals(config, "firefox")){
                System.setProperty("webdriver.firefox.logfile", "/dev/null");
                FirefoxOptions options = getFirefoxOptions();
                GeckoDriverService driverService = GeckoDriverService.createDefaultService();
                driver = new FirefoxDriver(driverService, options);
                driver.manage().window().maximize();
                Log.info(" Çözünürlük ->>> " + driver.manage().window().getSize());
            }
        } catch (Exception e) {
            Log.error(" Driver Ayakta Değil !!! " + e.getMessage());
        }
    }
    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        options.addArguments("--disable-notifications");
        return options;
    }
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        return options;
    }
    @AfterStep
    public final void checkStepHasError(){

        if ((LogCounter > 0)) {
            isErrorDetect = true;
            fail("Senaryoda Hata Alındı");
            Log.info("isError: " + isErrorDetect);
            driver.quit();
        }
    }
    @AfterScenario
    public final void tearDown() {
        try {
            Thread.sleep(1000);
            LogCounter = 0;
            resetAll();
            Log.info(" Driver'dan Cikiliyor...");
            driver.quit();
            Log.info(" Driver'dan Kapatildi...");
        } catch (Exception e) {
            Log.error(" Driver Sonlandirma Adiminda Hata Alindi !!! " + e.getMessage());
        }
    }
}
