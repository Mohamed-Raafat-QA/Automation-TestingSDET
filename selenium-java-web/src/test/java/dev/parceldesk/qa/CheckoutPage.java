package dev.parceldesk.qa;

import org.openqa.selenium.WebDriver;

public final class CheckoutPage extends DeskControls {
    CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void addNotebook() { click("add"); }

    public void removeNotebook() { click("remove"); }

    public void applySave10() { click("voucher"); }

    public void selectExpress() { click("express"); }

    public void placeOrder() { click("order"); }
}
