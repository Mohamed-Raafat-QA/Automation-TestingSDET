import { TrackingPage } from './tracking-page';
import { CheckoutPage } from './checkout-page';
import { expect, type Page } from 'playwright/test';
import { resolve } from 'node:path';
import { pathToFileURL } from 'node:url';

export class DeskPage {
  readonly tracking: TrackingPage;
  readonly checkout: CheckoutPage;

  constructor(private readonly page: Page) {
    this.tracking = new TrackingPage(page);
    this.checkout = new CheckoutPage(page);
  }

  async open() {
    await this.page.goto(pathToFileURL(resolve('demo/index.html')).href);
  }

  async expectResult(expected: string) {
    await expect(this.page.locator('#result')).toHaveText(expected);
  }

  async openShipments() {
    await this.page.getByRole('button', { name: 'Shipments', exact: true }).click();
  }

  async openCheckout() {
    await this.page.getByRole('button', { name: 'Checkout', exact: true }).click();
  }
}
