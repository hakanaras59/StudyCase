package StepDefinitions;

import BaseTest.*;

import Utilities.Log;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static Utilities.ErrorHelpers.sendErrorMessageAndTakeScreenShot;
import static java.lang.Thread.sleep;



public class WaitSteps extends BaseTest {
    public static void waitForPageLoad(WebDriver driver) throws InterruptedException {
        sleep(50);
        try {
            Log.info(" Sayfa Yuklenene Kadar Bekleniyor...");
            (new WebDriverWait(driver, Duration.ofSeconds(10)))
                    .until((ExpectedCondition<Boolean>) driver1 -> {
                        assert driver1 != null;
                        return ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");
                    });
        } catch (TimeoutException ex) {
            Log.info(" Sayfa Yuklenene Kadar Bekle Fonksiyonda Hata Alindi !!!");
            ((JavascriptExecutor)driver).executeScript("window.stop();");
            sleep(500);
        }
    }
    @Step("<link> linkine gidilir ve sayfa yüklenene kadar beklenir")
    public void gotoLink(String link) throws IOException {
        try {
            Log.info(link + " Adresine Gidiliyor...");
            driver.get(link);
            waitForPageLoad(driver);
            Log.info(link + " Adresine Gidildi...");
        } catch (Exception e) {
            sendErrorMessageAndTakeScreenShot(link + " Adresine Gidilemedi !!! " + e.getMessage());
        }
    }
    @Step("<seconds> saniye beklenir")
    public void waitSeconds(int seconds) throws InterruptedException {
        Log.info(" " + seconds + " Saniye Bekleniyor...");
        sleep(1000L * seconds);
    }
}
