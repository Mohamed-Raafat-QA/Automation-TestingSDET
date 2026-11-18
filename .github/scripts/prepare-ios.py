"""Warm the iOS simulator and WebDriverAgent before framework client timeouts apply."""
import json
import os
from urllib.error import HTTPError
from urllib.request import Request, urlopen


def appium(method, path, payload=None):
    data = None if payload is None else json.dumps(payload).encode()
    request = Request(
        "http://127.0.0.1:4723" + path,
        method=method,
        data=data,
        headers={"Content-Type": "application/json"},
    )
    try:
        with urlopen(request, timeout=720) as response:
            return json.load(response)["value"]
    except HTTPError as exc:
        raise RuntimeError(exc.read().decode(errors="replace")) from exc


def main():
    capabilities = {
        "platformName": "iOS",
        "appium:automationName": "XCUITest",
        "appium:udid": os.environ["DEVICE_UDID"],
        "appium:deviceName": "Parcel Desk device",
        "appium:app": os.environ["APP_PATH"],
        "appium:simulatorStartupTimeout": 420000,
        "appium:wdaLaunchTimeout": 180000,
    }
    print("Starting the simulator, WebDriverAgent, and Parcel Desk", flush=True)
    session = appium("POST", "/session", {"capabilities": {"alwaysMatch": capabilities}})
    appium("DELETE", "/session/" + session["sessionId"])
    print("The first native session completed. Starting the isolated scenarios.", flush=True)


if __name__ == "__main__":
    main()
