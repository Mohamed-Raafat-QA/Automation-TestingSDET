package dev.parceldesk.qa;

import org.testng.annotations.Test;

public final class CheckoutTest extends DeskFixture {

    @Test(groups = {"regression", "checkout"})
    public void blockEmptyCheckout() {
        desk.openCheckout();
        desk.placeOrder();
        desk.expectResult("Add an item before ordering");
    }

    @Test(groups = {"regression", "checkout"})
    public void priceTwoNotebooks() {
        desk.openCheckout();
        desk.addNotebook();
        desk.addNotebook();
        desk.expectResult("Items 2 | Total AED 80");
    }

    @Test(groups = {"regression", "checkout"})
    public void removeLastItem() {
        desk.openCheckout();
        desk.addNotebook();
        desk.removeNotebook();
        desk.expectResult("Items 0 | Total AED 0");
        desk.removeNotebook();
        desk.expectResult("Items 0 | Total AED 0");
    }

    @Test(groups = {"regression", "checkout"})
    public void applyVoucherOnce() {
        desk.openCheckout();
        desk.addNotebook();
        desk.applySave10();
        desk.expectResult("Items 1 | Total AED 36");
        desk.applySave10();
        desk.expectResult("Items 1 | Total AED 36");
    }

    @Test(groups = {"regression", "checkout"})
    public void excludeDeliveryFromDiscount() {
        desk.openCheckout();
        desk.addNotebook();
        desk.selectExpress();
        desk.applySave10();
        desk.expectResult("Items 1 | Total AED 48");
    }

    @Test(groups = {"regression", "checkout", "smoke"})
    public void confirmOrderAndClearCart() {
        desk.openCheckout();
        desk.addNotebook();
        desk.placeOrder();
        desk.expectResult("Order confirmed | Paid AED 40");
        desk.openCheckout();
        desk.expectResult("Items 0 | Total AED 0");
        desk.placeOrder();
        desk.expectResult("Add an item before ordering");
    }

    @Test(groups = {"regression", "checkout"})
    public void retainBasketWhileTracking() {
        desk.openCheckout();
        desk.addNotebook();
        desk.openShipments();
        desk.selectDubai();
        desk.expectResult("SH-202 | Delivered");
        desk.openCheckout();
        desk.expectResult("Items 1 | Total AED 40");
    }

    @Test(groups = {"regression", "checkout"})
    public void resetVoucherAndDeliveryAfterPayment() {
        desk.openCheckout();
        desk.addNotebook();
        desk.selectExpress();
        desk.applySave10();
        desk.placeOrder();
        desk.expectResult("Order confirmed | Paid AED 48");
        desk.addNotebook();
        desk.expectResult("Items 1 | Total AED 40");
    }
}
