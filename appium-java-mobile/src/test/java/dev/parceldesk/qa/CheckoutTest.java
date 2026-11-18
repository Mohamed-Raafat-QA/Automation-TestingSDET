package dev.parceldesk.qa;

import org.testng.annotations.Test;

public final class CheckoutTest extends DeskFixture {

    @Test(groups = {"regression", "checkout"})
    public void blockEmptyCheckout() {
        desk.openCheckout();
        desk.checkout.placeOrder();
        desk.expectResult("Add an item before ordering");
    }

    @Test(groups = {"regression", "checkout"})
    public void priceTwoNotebooks() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.checkout.addNotebook();
        desk.expectResult("Items 2 | Total AED 80");
    }

    @Test(groups = {"regression", "checkout"})
    public void removeLastItem() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.checkout.removeNotebook();
        desk.expectResult("Items 0 | Total AED 0");
        desk.checkout.removeNotebook();
        desk.expectResult("Items 0 | Total AED 0");
    }

    @Test(groups = {"regression", "checkout"})
    public void applyVoucherOnce() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.checkout.applySave10();
        desk.expectResult("Items 1 | Total AED 36");
        desk.checkout.applySave10();
        desk.expectResult("Items 1 | Total AED 36");
    }

    @Test(groups = {"regression", "checkout"})
    public void excludeDeliveryFromDiscount() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.checkout.selectExpress();
        desk.checkout.applySave10();
        desk.expectResult("Items 1 | Total AED 48");
    }

    @Test(groups = {"regression", "checkout"})
    public void chargeExpressOnceAfterVoucher() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.checkout.applySave10();
        desk.checkout.selectExpress();
        desk.expectResult("Items 1 | Total AED 48");
        desk.checkout.selectExpress();
        desk.expectResult("Items 1 | Total AED 48");
    }

    @Test(groups = {"regression", "checkout", "smoke"})
    public void confirmOrderAndClearCart() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.checkout.placeOrder();
        desk.expectResult("Order confirmed | Paid AED 40");
        desk.openCheckout();
        desk.expectResult("Items 0 | Total AED 0");
        desk.checkout.placeOrder();
        desk.expectResult("Add an item before ordering");
    }

    @Test(groups = {"regression", "checkout"})
    public void retainBasketWhileTracking() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.openShipments();
        desk.tracking.selectDubai();
        desk.expectResult("SH-202 | Delivered");
        desk.openCheckout();
        desk.expectResult("Items 1 | Total AED 40");
    }

    @Test(groups = {"regression", "checkout"})
    public void resetVoucherAndDeliveryAfterPayment() {
        desk.openCheckout();
        desk.checkout.addNotebook();
        desk.checkout.selectExpress();
        desk.checkout.applySave10();
        desk.checkout.placeOrder();
        desk.expectResult("Order confirmed | Paid AED 48");
        desk.checkout.addNotebook();
        desk.expectResult("Items 1 | Total AED 40");
    }
}
