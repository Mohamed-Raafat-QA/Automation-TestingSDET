package dev.parceldesk.qa;

import org.testng.annotations.Test;

public final class TrackingTest extends DeskFixture {

    @Test(groups = {"regression", "tracking", "smoke"})
    public void viewBookedShipment() {
        desk.selectAbuDhabi();
        desk.expectResult("SH-101 | Booked");
    }

    @Test(groups = {"regression", "tracking"})
    public void viewDeliveredShipment() {
        desk.selectDubai();
        desk.expectResult("SH-202 | Delivered");
    }

    @Test(groups = {"regression", "tracking", "smoke"})
    public void advanceBookedShipment() {
        desk.selectAbuDhabi();
        desk.advanceShipment();
        desk.expectResult("SH-101 | In transit");
    }

    @Test(groups = {"regression", "tracking"})
    public void recordCompleteShipmentHistory() {
        desk.selectAbuDhabi();
        desk.advanceShipment();
        desk.advanceShipment();
        desk.showHistory();
        desk.expectResult("SH-101 | Booked > In transit > Delivered");
    }

    @Test(groups = {"regression", "tracking"})
    public void preventDuplicateDeliveryEvent() {
        desk.selectDubai();
        desk.advanceShipment();
        desk.expectResult("SH-202 | Already delivered");
        desk.showHistory();
        desk.expectResult("SH-202 | Booked > In transit > Delivered");
    }

    @Test(groups = {"regression", "tracking"})
    public void keepShipmentsIndependent() {
        desk.selectAbuDhabi();
        desk.advanceShipment();
        desk.selectDubai();
        desk.expectResult("SH-202 | Delivered");
        desk.selectAbuDhabi();
        desk.expectResult("SH-101 | In transit");
    }
}
