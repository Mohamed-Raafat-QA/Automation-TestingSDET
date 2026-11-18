import type { Page } from 'playwright/test';

export class CheckoutPage {
  constructor(private readonly page: Page) {}

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
