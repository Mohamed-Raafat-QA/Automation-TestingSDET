import json
import subprocess

listing = subprocess.check_output(["xcrun", "simctl", "list", "devices", "available", "-j"])
devices = json.loads(listing)["devices"]
runtimes = sorted(
    (key for key in devices if ".iOS-" in key),
    key=lambda key: tuple(int(part) for part in key.split(".iOS-")[1].split("-")),
    reverse=True,
)
for runtime in runtimes:
    for device in devices[runtime]:
        if device["name"].startswith("iPhone"):
            print(device["udid"])
            raise SystemExit(0)
raise SystemExit("Install an iPhone simulator runtime in Xcode first")
