import pytest

pytestmark = [pytest.mark.regression, pytest.mark.tracking]


@pytest.mark.smoke
def test_view_booked_shipment(desk):
    desk.tracking.select_abu_dhabi()
    desk.expect_result("SH-101 | Booked")


def test_view_delivered_shipment(desk):
    desk.tracking.select_dubai()
    desk.expect_result("SH-202 | Delivered")


@pytest.mark.smoke
def test_advance_booked_shipment(desk):
    desk.tracking.select_abu_dhabi()
    desk.tracking.advance_shipment()
    desk.expect_result("SH-101 | In transit")


def test_record_complete_shipment_history(desk):
    desk.tracking.select_abu_dhabi()
    desk.tracking.advance_shipment()
    desk.tracking.advance_shipment()
    desk.tracking.show_history()
    desk.expect_result("SH-101 | Booked > In transit > Delivered")


def test_prevent_duplicate_delivery_event(desk):
    desk.tracking.select_dubai()
    desk.tracking.advance_shipment()
    desk.expect_result("SH-202 | Already delivered")
    desk.tracking.show_history()
    desk.expect_result("SH-202 | Booked > In transit > Delivered")


def test_keep_shipments_independent(desk):
    desk.tracking.select_abu_dhabi()
    desk.tracking.advance_shipment()
    desk.tracking.select_dubai()
    desk.expect_result("SH-202 | Delivered")
    desk.tracking.select_abu_dhabi()
    desk.expect_result("SH-101 | In transit")
