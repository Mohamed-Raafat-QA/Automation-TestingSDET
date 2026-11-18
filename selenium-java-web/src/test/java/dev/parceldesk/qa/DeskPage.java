package dev.parceldesk.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class DeskPage extends DeskControls {
    public final TrackingPage tracking;
    public final CheckoutPage checkout;

    public DeskPage(WebDriver driver) {
        super(driver);
        tracking = new TrackingPage(driver);
        checkout = new CheckoutPage(driver);
    }

    public void expectResult(String expected) {
        wait.withMessage("Expected visible result: " + expected)
            .until(driver -> expected.equals(driver.findElement(By.id("result")).getText()));
    }

    public void openShipments() { click("tracking"); }

    public void openCheckout() { click("checkout"); }
}
