package dev.parceldesk.qa;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;

public final class DeskScreen {
    private final WebDriverWait wait;

    public DeskScreen(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        wait.ignoring(StaleElementReferenceException.class);
    }

    private void click(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(id))).click();
    }

    public void expectResult(String expected) {
        wait.withMessage("Expected visible result: " + expected)
            .until(driver -> expected.equals(driver.findElement(AppiumBy.accessibilityId("result")).getText()));
    }

    public void openShipments() { click("tracking"); }

    public void openCheckout() { click("checkout"); }

    public void selectAbuDhabi() { click("parcel_a"); }

    public void selectDubai() { click("parcel_b"); }

    public void advanceShipment() { click("advance"); }

    public void showHistory() { click("history"); }

    public void addNotebook() { click("add"); }

    public void removeNotebook() { click("remove"); }

    public void applySave10() { click("voucher"); }

    public void selectExpress() { click("express"); }

    public void placeOrder() { click("order"); }
}
