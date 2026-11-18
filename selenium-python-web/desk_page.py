from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as conditions
from selenium.webdriver.support.ui import WebDriverWait


class Controls:
    def __init__(self, driver):
        self.wait = WebDriverWait(driver, 12)

    def _click(self, element_id):
        self.wait.until(conditions.element_to_be_clickable((By.ID, element_id))).click()


class TrackingPage(Controls):
    def select_abu_dhabi(self):
        self._click("parcel_a")

    def select_dubai(self):
        self._click("parcel_b")

    def advance_shipment(self):
        self._click("advance")

    def show_history(self):
        self._click("history")


class CheckoutPage(Controls):
    def add_notebook(self):
        self._click("add")

    def remove_notebook(self):
        self._click("remove")

    def apply_save10(self):
        self._click("voucher")

    def select_express(self):
        self._click("express")

    def place_order(self):
        self._click("order")


class DeskPage(Controls):
    def __init__(self, driver):
        super().__init__(driver)
        self.tracking = TrackingPage(driver)
        self.checkout = CheckoutPage(driver)

    def expect_result(self, expected):
        def matches(driver):
            elements = driver.find_elements(By.ID, "result")
            return bool(elements) and elements[0].text == expected

        self.wait.until(matches, f"Expected visible result: {expected}")

    def open_shipments(self):
        self._click("tracking")

    def open_checkout(self):
        self._click("checkout")
