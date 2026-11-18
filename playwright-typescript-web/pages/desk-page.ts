import { expect, type Page } from 'playwright/test';
import { resolve } from 'node:path';
import { pathToFileURL } from 'node:url';

export class DeskPage {
  constructor(private readonly page: Page) {}

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

  async selectAbuDhabi() {
    await this.page.getByRole('button', { name: 'SH-101 · Abu Dhabi', exact: true }).click();
  }

  async selectDubai() {
    await this.page.getByRole('button', { name: 'SH-202 · Dubai', exact: true }).click();
  }

  async advanceShipment() {
    await this.page.getByRole('button', { name: 'Advance status', exact: true }).click();
  }

  async showHistory() {
    await this.page.getByRole('button', { name: 'Show history', exact: true }).click();
  }

  async addNotebook() {
    await this.page.getByRole('button', { name: 'Add notebook', exact: true }).click();
  }

  async removeNotebook() {
    await this.page.getByRole('button', { name: 'Remove notebook', exact: true }).click();
  }

  async applySave10() {
    await this.page.getByRole('button', { name: 'Apply SAVE10', exact: true }).click();
  }

  async selectExpress() {
    await this.page.getByRole('button', { name: 'Express delivery', exact: true }).click();
  }

  async placeOrder() {
    await this.page.getByRole('button', { name: 'Place order', exact: true }).click();
  }
}
