import pytest

pytestmark = [pytest.mark.regression, pytest.mark.checkout]


def test_block_empty_checkout(desk):
    desk.open_checkout()
    desk.checkout.place_order()
    desk.expect_result("Add an item before ordering")


def test_price_two_notebooks(desk):
    desk.open_checkout()
    desk.checkout.add_notebook()
    desk.checkout.add_notebook()
    desk.expect_result("Items 2 | Total AED 80")


def test_remove_last_item(desk):
    desk.open_checkout()
    desk.checkout.add_notebook()
    desk.checkout.remove_notebook()
    desk.expect_result("Items 0 | Total AED 0")
    desk.checkout.remove_notebook()
    desk.expect_result("Items 0 | Total AED 0")


def test_apply_voucher_once(desk):
    desk.open_checkout()
    desk.checkout.add_notebook()
    desk.checkout.apply_save10()
    desk.expect_result("Items 1 | Total AED 36")
    desk.checkout.apply_save10()
    desk.expect_result("Items 1 | Total AED 36")


def test_exclude_delivery_from_discount(desk):
    desk.open_checkout()
    desk.checkout.add_notebook()
    desk.checkout.select_express()
    desk.checkout.apply_save10()
    desk.expect_result("Items 1 | Total AED 48")


@pytest.mark.smoke
def test_confirm_order_and_clear_cart(desk):
    desk.open_checkout()
    desk.checkout.add_notebook()
    desk.checkout.place_order()
    desk.expect_result("Order confirmed | Paid AED 40")
    desk.open_checkout()
    desk.expect_result("Items 0 | Total AED 0")
    desk.checkout.place_order()
    desk.expect_result("Add an item before ordering")


def test_retain_basket_while_tracking(desk):
    desk.open_checkout()
    desk.checkout.add_notebook()
    desk.open_shipments()
    desk.tracking.select_dubai()
    desk.expect_result("SH-202 | Delivered")
    desk.open_checkout()
    desk.expect_result("Items 1 | Total AED 40")


def test_reset_voucher_and_delivery_after_payment(desk):
    desk.open_checkout()
    desk.checkout.add_notebook()
    desk.checkout.select_express()
    desk.checkout.apply_save10()
    desk.checkout.place_order()
    desk.expect_result("Order confirmed | Paid AED 48")
    desk.checkout.add_notebook()
    desk.expect_result("Items 1 | Total AED 40")
