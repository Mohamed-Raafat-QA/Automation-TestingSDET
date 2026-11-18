import type { Page } from 'playwright/test';

export class TrackingPage {
  constructor(private readonly page: Page) {}

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
}
