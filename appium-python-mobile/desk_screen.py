from selenium.common.exceptions import StaleElementReferenceException, TimeoutException
from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support import expected_conditions as conditions
from selenium.webdriver.support.ui import WebDriverWait


class Controls:
    def __init__(self, driver):
        self.wait = WebDriverWait(driver, 12, ignored_exceptions=(StaleElementReferenceException,))

    def _click(self, element_id):
        self.wait.until(conditions.element_to_be_clickable((AppiumBy.ACCESSIBILITY_ID, element_id))).click()


class TrackingScreen(Controls):
    def select_abu_dhabi(self):
        self._click("parcel_a")

    def select_dubai(self):
        self._click("parcel_b")

    def advance_shipment(self):
        self._click("advance")

    def show_history(self):
        self._click("history")


class CheckoutScreen(Controls):
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


class DeskScreen(Controls):
    def __init__(self, driver):
        super().__init__(driver)
        self.tracking = TrackingScreen(driver)
        self.checkout = CheckoutScreen(driver)

    def expect_result(self, expected):
        observed = "<element not found>"

        def matches(driver):
            nonlocal observed
            elements = driver.find_elements(AppiumBy.ACCESSIBILITY_ID, "result")
            observed = elements[0].text if elements else "<element not found>"
            return bool(elements) and observed == expected

        try:
            self.wait.until(matches)
        except TimeoutException as error:
            raise AssertionError(f"Expected {expected!r}, last observed {observed!r}") from error

    def open_shipments(self):
        self._click("tracking")

    def open_checkout(self):
        self._click("checkout")
