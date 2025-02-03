package StepDefinitions;

import BaseTest.*;

import Utilities.Log;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import static Utilities.ErrorHelpers.sendErrorMessageAndTakeScreenShot;

public class ClickSteps extends BaseTest{

    @Step("<xpath> xpath'li element görünene kadar bekleyip tıklanır")
    public void clicktoXpathVisibilityOfElementLocated(String xpath) throws IOException {
        try{

            Log.info(" XPath'li Element Gorunene Kadar Bekleyip Tiklama Adimi Basladi...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated (By.xpath(xpath)));
            element.click();
            Log.info(" " + xpath + " XPath'li Element Gorunene Kadar Bekleyip Tiklama Adimi Basarili...");
        }
        catch (Exception e){
            sendErrorMessageAndTakeScreenShot(" XPath'li Element Gorunene Kadar Bekleyip Tiklama Adiminda Hata Alindi !!! " + e.getMessage());
        }
    }

    @Step("<cssSelector> cssSelector ile elementin href değeri kullanılarak sayfaya yönlendirilir")
    public void navigateUsingHref(String cssSelector) throws IOException {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
            String hrefValue = element.getAttribute("href");

            Log.info("HREF değeri alındı: " + hrefValue);

            if (hrefValue != null && !hrefValue.isEmpty()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.location.href='" + hrefValue + "';");
                Log.info(" Sayfa '" + hrefValue + "' adresine yönlendirildi.");
            } else {
                Log.error(" HREF değeri bulunamadı! '" + cssSelector + "'");
                sendErrorMessageAndTakeScreenShot(" HREF değeri alınamadı: " + cssSelector);
            }

        } catch (Exception e) {
            sendErrorMessageAndTakeScreenShot(" HATA: '" + cssSelector + "' öğesinin href değeri ile yönlendirme yapılamadı! " + e.getMessage());
        }
    }
}
