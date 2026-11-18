package dev.parceldesk.qa;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebDriver;

public final class DeskScreen extends DeskControls {
    public final TrackingScreen tracking;
    public final CheckoutScreen checkout;

    public DeskScreen(WebDriver driver) {
        super(driver);
        tracking = new TrackingScreen(driver);
        checkout = new CheckoutScreen(driver);
    }

    public void expectResult(String expected) {
        wait.withMessage("Expected visible result: " + expected)
            .until(driver -> expected.equals(driver.findElement(AppiumBy.accessibilityId("result")).getText()));
    }

    public void openShipments() { click("tracking"); }

    public void openCheckout() { click("checkout"); }
}
