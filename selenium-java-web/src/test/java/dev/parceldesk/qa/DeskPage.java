package dev.parceldesk.qa;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

public final class DeskPage {
    private final WebDriverWait wait;

    public DeskPage(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        wait.ignoring(StaleElementReferenceException.class);
    }

    private void click(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
    }

    public void expectResult(String expected) {
        wait.withMessage("Expected visible result: " + expected)
            .until(driver -> expected.equals(driver.findElement(By.id("result")).getText()));
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
