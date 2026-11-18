# Parcel Desk · Selenium Java web

Selenium WebDriver, Maven and TestNG automate shipment tracking and e-commerce checkout. Runs against the included web application in headless Chrome. All records and purchases are synthetic.

## Run locally

From the repository root, run `cd selenium-java-web` before the commands below. Install JDK 17 and Maven 3.9+. Dependencies resolve on the first Maven run.

Install Chrome. Selenium Manager can resolve the matching driver on the first run. In offline environments, place a compatible ChromeDriver on PATH beforehand.

### Execute the suite

```bash
mvn -B test  # all 15 scenarios
mvn -B -Dgroups=smoke test  # three critical paths
```

## Structure and evidence

- `demo/` contains the application under test. It seeds SH-101 as booked and SH-202 as delivered. A notebook costs AED 40, SAVE10 discounts merchandise by 10%, and express delivery adds AED 12 once.
- `src/test/java/dev/parceldesk/qa` contains the reusable UI interactions. Assertions check the exact visible shipment state or basket amount.
- Every test starts with a fresh browser session or context and reloads the local fixture. The app keeps its records in page memory.
- Evidence is written to target/surefire-reports/ (XML, HTML and test output) and reports/screenshots/ on failure.
- On push, pull request or manual dispatch, GitHub Actions runs the full web suite and retains reports for seven days.

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
| Checkout | Charge express delivery once after voucher | regression |
| Checkout | Confirm order and clear cart | regression, smoke |
| Checkout | Retain basket while tracking | regression |
| Checkout | Reset voucher and delivery after payment | regression |
