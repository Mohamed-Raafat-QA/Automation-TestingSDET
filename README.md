# Parcel Desk | Web and mobile test automation

Parcel Desk is a QA portfolio built around two questions. Can a customer trust the shipment history, and does checkout charge the right amount? Seven automation projects exercise the same 15 business scenarios against a demo web app and native Android and iOS apps. The shared scenarios make it easy to compare test design across languages and frameworks.

**Start with one checkout rule.** A notebook costs AED 40. A 10% voucher reduces the merchandise price to AED 36. Express delivery adds AED 12. Selecting express twice must still leave the total at **AED 48**. The test checks the displayed total after each selection, so a failure identifies which action changed the price. [Read the nine-line test](https://github.com/Mohamed-Raafat-QA/Automation-TestingSDET/blob/7c6d7bdd6093875866f9f465345c0f25a43e8b58/playwright-typescript-web/tests/checkout.spec.ts#L42-L50) · [View the browser run](https://github.com/Mohamed-Raafat-QA/Automation-TestingSDET/actions/runs/44345248782)

## Walk through the implementation

1. **Follow the UI action.** The Playwright test calls `selectExpress()`. The checkout page object owns the button locator, while the test owns the expected price. Compare that action with its native Appium equivalent. [Web checkout page](playwright-typescript-web/pages/checkout-page.ts) · [Mobile checkout screen](appium-java-mobile/src/test/java/dev/parceldesk/qa/CheckoutScreen.java)
2. **Check test isolation.** Each Playwright test gets a fresh browser context. Its fixture opens the demo before handing the page object to the test. On mobile, each Java test gets a new Appium session. [Playwright fixture](playwright-typescript-web/tests/fixtures.ts) · [Appium fixture](appium-java-mobile/src/test/java/dev/parceldesk/qa/DeskFixture.java)
3. **Read a failure alongside its retry.** On November 19, 2026, Robot Framework on iOS passed 14 of 15 scenarios on its first execution that day. The simulator failed to launch the app during setup for one test. A fresh execution of the same commit passed all 15. The retry confirmed the scenarios could pass. First-attempt launch reliability remained an open issue. [Initial job log](https://github.com/Mohamed-Raafat-QA/Automation-TestingSDET/actions/runs/44345248445/job/135317640156) · [Retry log](https://github.com/Mohamed-Raafat-QA/Automation-TestingSDET/actions/runs/44345248445/job/135324487739)

## Choose a project

| Project and setup guide | Test stack | Execution target |
| --- | --- | --- |
| [Playwright TypeScript](playwright-typescript-web/README.md) | Playwright Test | Chromium, Firefox, WebKit |
| [Selenium Java](selenium-java-web/README.md) | Selenium WebDriver, TestNG, Maven | Chrome |
| [Selenium Python](selenium-python-web/README.md) | Selenium WebDriver, pytest | Chrome |
| [Robot Framework Web](robot-framework-web/README.md) | SeleniumLibrary | Chrome |
| [Appium Java](appium-java-mobile/README.md) | Appium, TestNG, Maven | Android and iOS |
| [Appium Python](appium-python-mobile/README.md) | Appium, pytest | Android and iOS |
| [Robot Framework Mobile](robot-framework-mobile/README.md) | AppiumLibrary | Android and iOS |

Each guide includes setup instructions, smoke and regression commands, and the scenario list. GitHub Actions runs the affected project when its files change. Each workflow also supports manual execution.

## Verified coverage

On November 19, 2026, the final results for commit `7c6d7bd` totaled **180 passing test executions**. The 15 distinct scenarios ran on six mobile framework/platform combinations (90 executions), three Chrome suites (45), and three Playwright browser engines (45). Nine of the ten jobs passed on their first execution that day. Robot iOS passed on retry. [Tested commit](https://github.com/Mohamed-Raafat-QA/Automation-TestingSDET/commit/7c6d7bdd6093875866f9f465345c0f25a43e8b58) · [Workflow history](https://github.com/Mohamed-Raafat-QA/Automation-TestingSDET/actions)
