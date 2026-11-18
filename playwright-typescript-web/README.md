# Parcel Desk · Playwright TypeScript web

Playwright Test and TypeScript automate shipment tracking and e-commerce checkout. Runs against the included web application in Chromium, Firefox and WebKit. All records and purchases are synthetic.

## Run locally

From the repository root, run `cd playwright-typescript-web` before the commands below. Install Node.js 22 and npm, then install the browsers. On Linux, use the following commands. On macOS and Windows, omit `--with-deps`.

```bash
npm install
npx playwright install --with-deps chromium firefox webkit
npm run typecheck
```

### Execute the suite

```bash
npm test  # all 15 scenarios
npm run smoke  # three critical paths
```

For a single engine, run `npm test -- --project=chromium`.

## Structure and evidence

- `demo/` contains the application under test. It seeds SH-101 as booked and SH-202 as delivered. A notebook costs AED 40, SAVE10 discounts merchandise by 10%, and express delivery adds AED 12 once.
- `pages/desk-page.ts` contains the reusable UI interactions. Assertions check the exact visible shipment state or basket amount.
- Every test starts with a fresh browser session or context and reloads the local fixture. The app keeps its records in page memory.
- Evidence is written to reports/html/index.html, reports/junit.xml and per-failure traces/screenshots under reports/results/.
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
