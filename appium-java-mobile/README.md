# Parcel Desk · Appium Java mobile

Appium Java Client, Maven and TestNG automate shipment tracking and e-commerce checkout. Runs against the included Android Activity and iOS UIKit application. All records and purchases are synthetic.

## Run locally

From the repository root, run `cd appium-java-mobile` before the commands below. Install JDK 17 and Maven 3.9+. Dependencies resolve on the first Maven run.

### Prepare a mobile target

Use Node.js 22.12+ and npm 10+ for Appium. Run these commands in Bash. Select one target per run.

**Android emulator.** Install JDK 17, Gradle 8.9, Android SDK Platform 35, Build Tools 35.0.0, platform-tools and an emulator. Set `ANDROID_HOME` and put `adb` on PATH. Boot an API 35 emulator in Android Studio, then copy its serial from `adb devices`.

```bash
npm install --global appium@3.1.2
appium driver install uiautomator2@5.0.0
export PLATFORM=Android
export DEVICE_UDID=emulator-5554  # replace with the serial reported by adb
export APP_PATH="$(bash scripts/build-app.sh android)"
```

**iOS simulator.** Use macOS with Xcode 16 or a compatible newer Xcode and an installed iPhone simulator runtime. The build script produces a simulator app.

```bash
brew install xcodegen
npm install --global appium@3.1.2
appium driver install xcuitest@10.7.0
export PLATFORM=iOS
export DEVICE_UDID="$(python3 scripts/iphone.py)"
xcrun simctl boot "$DEVICE_UDID" || xcrun simctl list devices booted | grep -F "$DEVICE_UDID"
xcrun simctl bootstatus "$DEVICE_UDID" -b
export APP_PATH="$(bash scripts/build-app.sh ios)"
```

Start `appium --address 127.0.0.1 --port 4723` in a second terminal on the build host. Run the suite in the first terminal, where the target variables are set. Use `APPIUM_URL` for a different server URL. The app path must be readable by the server.

### Execute the suite

```bash
mvn -B test  # all 14 scenarios
mvn -B -Dgroups=smoke test  # three critical paths
```

## Structure and evidence

- `demo/` contains the application under test. It seeds SH-101 as booked and SH-202 as delivered. A notebook costs AED 40, SAVE10 discounts merchandise by 10%, and express delivery adds AED 12 once.
- `src/test/java/dev/parceldesk/qa` contains the reusable UI interactions. Assertions check the exact visible shipment state or basket amount.
- Every test gets a new Appium session. The app keeps the basket and shipment progress in process memory and starts with the same seed on launch.
- Evidence is written to target/surefire-reports/ (XML, HTML and test output) and reports/screenshots/ on failure.
- On push, pull request or manual dispatch, GitHub Actions runs separate Android and iOS jobs and retains reports for seven days.

## Scenario coverage

| Area | Scenario | Tags |
| --- | --- | --- |
| Tracking | View booked shipment | regression, smoke |
| Tracking | View delivered shipment | regression |
| Tracking | Advance booked shipment | regression, smoke |
| Tracking | Record complete shipment history | regression |
| Tracking | Prevent duplicate delivery event | regression |
| Tracking | Keep shipments independent | regression |
| Checkout | Block empty checkout | regression |
| Checkout | Price two notebooks | regression |
| Checkout | Remove last item | regression |
| Checkout | Apply voucher once | regression |
| Checkout | Exclude delivery from discount | regression |
| Checkout | Confirm order and clear cart | regression, smoke |
| Checkout | Retain basket while tracking | regression |
| Checkout | Reset voucher and delivery after payment | regression |
