package dev.parceldesk.qa;

import org.openqa.selenium.WebDriver;

public final class TrackingPage extends DeskControls {
    TrackingPage(WebDriver driver) {
        super(driver);
    }

    public void selectAbuDhabi() { click("parcel_a"); }

    public void selectDubai() { click("parcel_b"); }

    public void advanceShipment() { click("advance"); }

    public void showHistory() { click("history"); }
}
