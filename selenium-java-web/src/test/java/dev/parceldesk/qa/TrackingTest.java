package dev.parceldesk.qa;

import org.testng.annotations.Test;

public final class TrackingTest extends DeskFixture {

    @Test(groups = {"regression", "tracking", "smoke"})
    public void viewBookedShipment() {
        desk.tracking.selectAbuDhabi();
        desk.expectResult("SH-101 | Booked");
    }

    @Test(groups = {"regression", "tracking"})
    public void viewDeliveredShipment() {
        desk.tracking.selectDubai();
        desk.expectResult("SH-202 | Delivered");
    }

    @Test(groups = {"regression", "tracking", "smoke"})
    public void advanceBookedShipment() {
        desk.tracking.selectAbuDhabi();
        desk.tracking.advanceShipment();
        desk.expectResult("SH-101 | In transit");
    }

    @Test(groups = {"regression", "tracking"})
    public void recordCompleteShipmentHistory() {
        desk.tracking.selectAbuDhabi();
        desk.tracking.advanceShipment();
        desk.tracking.advanceShipment();
        desk.tracking.showHistory();
        desk.expectResult("SH-101 | Booked > In transit > Delivered");
    }

    @Test(groups = {"regression", "tracking"})
    public void preventDuplicateDeliveryEvent() {
        desk.tracking.selectDubai();
        desk.tracking.advanceShipment();
        desk.expectResult("SH-202 | Already delivered");
        desk.tracking.showHistory();
        desk.expectResult("SH-202 | Booked > In transit > Delivered");
    }

    @Test(groups = {"regression", "tracking"})
    public void keepShipmentsIndependent() {
        desk.tracking.selectAbuDhabi();
        desk.tracking.advanceShipment();
        desk.tracking.selectDubai();
        desk.expectResult("SH-202 | Delivered");
        desk.tracking.selectAbuDhabi();
        desk.expectResult("SH-101 | In transit");
    }
}
