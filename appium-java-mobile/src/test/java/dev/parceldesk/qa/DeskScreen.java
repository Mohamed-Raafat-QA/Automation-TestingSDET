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
        var observed = new java.util.concurrent.atomic.AtomicReference<>("<element not found>");
        wait.withMessage(() -> "Expected '" + expected + "', last observed '" + observed.get() + "'")
            .until(driver -> {
                var results = driver.findElements(AppiumBy.accessibilityId("result"));
                observed.set(results.isEmpty() ? "<element not found>" : results.get(0).getText());
                return !results.isEmpty() && expected.equals(observed.get());
            });
    }

    public void openShipments() { click("tracking"); }

    public void openCheckout() { click("checkout"); }
}
