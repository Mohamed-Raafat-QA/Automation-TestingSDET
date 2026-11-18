from pathlib import Path
import pytest
from desk_page import DeskPage
import os
from selenium import webdriver

def open_driver():
    options = webdriver.ChromeOptions()
    options.add_argument("--headless=new")
    options.add_argument("--window-size=1280,900")
    if binary := os.getenv("CHROME_BIN"):
        options.binary_location = binary
    return webdriver.Chrome(options=options)


@pytest.fixture
def desk(request):
    driver = open_driver()
    request.node.desk_driver = driver
    try:
        driver.get((Path(__file__).resolve().parent / "demo/index.html").as_uri())
        yield DeskPage(driver)
    finally:
        driver.quit()


@pytest.hookimpl(hookwrapper=True)
def pytest_runtest_makereport(item, call):
    outcome = yield
    report = outcome.get_result()
    driver = getattr(item, "desk_driver", None)
    if report.failed and driver and report.when in ("setup", "call"):
        directory = Path("reports/screenshots")
        directory.mkdir(parents=True, exist_ok=True)
        driver.save_screenshot(str(directory / f"{item.name}.png"))
