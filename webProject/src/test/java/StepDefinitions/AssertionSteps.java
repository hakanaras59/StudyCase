package StepDefinitions;

import BaseTest.BaseTest;
import Utilities.Log;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static Utilities.ErrorHelpers.sendErrorMessageAndTakeScreenShot;


public class AssertionSteps extends BaseTest {

    @Step("<xpath> xpath'li elementin var olduğu kontrol edilir")
    public void ifElementExistXPath(String xpath) throws IOException {
        try {
            Log.info(" XPATH'li Elementin Var Oldugunun Kontrolu Basladi...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            Log.info(" " + xpath + " XPATH'li Elementin Var Oldugunun Kontrolu Basarili...");
        } catch (Exception e) {
            sendErrorMessageAndTakeScreenShot(" " + xpath + " XPATH'li Elementin Var Oldugunun Kontrolu Basarisiz !!! " + e.getMessage());
        }
    }
    @Step("<xpath> xpath'li elementin olmadığı kontrol edilir")
    public void ifElementDoesntExistXPath(String xpath) throws IOException {
        try {
            Log.info(" XPATH'li Elementin Olmadiginin Kontrolu Islemi Basladi...");
            Log.info(" Boyut ->>> " + driver.findElements(By.xpath(xpath)).size());
            if (driver.findElements(By.xpath(xpath)).isEmpty()) {
                Log.info(" " + xpath + " XPath'li Element Bulunamadi -> Kontrol Basarili...");
            } else {
                sendErrorMessageAndTakeScreenShot(" " + xpath + " XPATH'li Element Bulundu -> Kontrol Basarisiz...");
            }
        } catch (Exception e) {
            sendErrorMessageAndTakeScreenShot(" " + xpath + "XPATH'li Elementin Var Olmadıginin Kontrolu Basarisiz !!! " + e.getMessage());
        }
    }

    @Step("<xpath> XPath'li fiyat listesinin <order> sıralamada olup olmadığı kontrol edilir")
    public void verifyPricesAreSorted(String xpath, String order) throws IOException {
        try {
            Log.info("Fiyatların '" + order + "' sıralamada olup olmadığı kontrol ediliyor...");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));

            List<Float> prices = priceElements.stream()
                    .map(e -> e.getText().replaceAll("[^0-9,]", "").replace(",", ".").trim())
                    .map(Float::parseFloat)
                    .toList();

            Log.info("Alınan fiyatlar: " + prices);

            List<Float> ascendingSortedPrices = prices.stream().sorted().toList();
            List<Float> descendingSortedPrices = prices.stream().sorted((a, b) -> Float.compare(b, a)).toList();

            boolean isSortedCorrectly = false;

            if (order.equalsIgnoreCase("artan")) {
                isSortedCorrectly = prices.equals(ascendingSortedPrices);
            } else if (order.equalsIgnoreCase("azalan")) {
                isSortedCorrectly = prices.equals(descendingSortedPrices);
            } else {
                throw new IllegalArgumentException("Geçersiz sıralama türü: '" + order + "'. 'artan' veya 'azalan' kullanmalısınız.");
            }

            if (isSortedCorrectly) {
                Log.info("Fiyatlar '" + order + "' sıralamada doğru!");
            } else {
                String artan = " HATA: Beklenen: " + (order.equalsIgnoreCase("artan") ? ascendingSortedPrices : descendingSortedPrices) +
                        ", Mevcut: " + prices;
                sendErrorMessageAndTakeScreenShot(artan);
            }

        } catch (Exception e) {
            sendErrorMessageAndTakeScreenShot("Fiyat sıralaması kontrolü başarısız! " + e.getMessage());
        }
    }
}