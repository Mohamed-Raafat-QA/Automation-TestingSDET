import logging
from pathlib import Path
import pytest
from desk_screen import DeskScreen
import os
from appium import webdriver
from appium.options.android import UiAutomator2Options
from appium.options.ios import XCUITestOptions

def required(name):
    value = os.getenv(name, "").strip()
    if not value:
        raise ValueError(f"Set {name} before starting Appium")
    return value


def open_driver():
    platform = required("PLATFORM").lower()
    option_types = {"android": UiAutomator2Options, "ios": XCUITestOptions}
    if platform not in option_types:
        raise ValueError(f"PLATFORM must be Android or iOS, got {platform!r}")
    options = option_types[platform]()
    options.app = str(Path(required("APP_PATH")).resolve(strict=True))
    options.udid = required("DEVICE_UDID")
    options.device_name = os.environ.get("DEVICE_NAME", "Parcel Desk device")
    options.no_reset = False
    return webdriver.Remote(os.getenv("APPIUM_URL", "http://127.0.0.1:4723"), options=options)


@pytest.fixture
def desk(request):
    driver = open_driver()
    request.node.desk_driver = driver
    try:
        yield DeskScreen(driver)
    finally:
        driver.quit()


@pytest.hookimpl(hookwrapper=True)
def pytest_runtest_makereport(item, call):
    outcome = yield
    report = outcome.get_result()
    driver = getattr(item, "desk_driver", None)
    if report.failed and driver and report.when in ("setup", "call"):
        try:
            directory = Path("reports/screenshots")
            directory.mkdir(parents=True, exist_ok=True)
            if not driver.save_screenshot(str(directory / f"{item.name}.png")):
                logging.getLogger(__name__).warning("Screenshot was not saved for %s", item.nodeid)
        except Exception:
            logging.getLogger(__name__).warning("Screenshot capture failed for %s", item.nodeid, exc_info=True)
