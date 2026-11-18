import { test as base } from 'playwright/test';
import { DeskPage } from '../pages/desk-page';

export const test = base.extend<{ desk: DeskPage }>({
  desk: async ({ page }, use) => {
    const desk = new DeskPage(page);
    await desk.open();
    await use(desk);
  },
});
