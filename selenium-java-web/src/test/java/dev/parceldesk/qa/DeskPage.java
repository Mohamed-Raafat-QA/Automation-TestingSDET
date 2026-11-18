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
        var observed = new java.util.concurrent.atomic.AtomicReference<>("<element not found>");
        wait.withMessage(() -> "Expected '" + expected + "', last observed '" + observed.get() + "'")
            .until(driver -> {
                var results = driver.findElements(By.id("result"));
                observed.set(results.isEmpty() ? "<element not found>" : results.get(0).getText());
                return !results.isEmpty() && expected.equals(observed.get());
            });
    }

    public void openShipments() { click("tracking"); }

    public void openCheckout() { click("checkout"); }
}
