import { test } from './fixtures';

test('block empty checkout', { tag: ["@regression", "@checkout"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.placeOrder();
  await desk.expectResult("Add an item before ordering");
});

test('price two notebooks', { tag: ["@regression", "@checkout"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.addNotebook();
  await desk.addNotebook();
  await desk.expectResult("Items 2 | Total AED 80");
});

test('remove last item', { tag: ["@regression", "@checkout"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.addNotebook();
  await desk.removeNotebook();
  await desk.expectResult("Items 0 | Total AED 0");
  await desk.removeNotebook();
  await desk.expectResult("Items 0 | Total AED 0");
});

test('apply voucher once', { tag: ["@regression", "@checkout"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.addNotebook();
  await desk.applySave10();
  await desk.expectResult("Items 1 | Total AED 36");
  await desk.applySave10();
  await desk.expectResult("Items 1 | Total AED 36");
});

test('exclude delivery from discount', { tag: ["@regression", "@checkout"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.addNotebook();
  await desk.selectExpress();
  await desk.applySave10();
  await desk.expectResult("Items 1 | Total AED 48");
});

test('confirm order and clear cart', { tag: ["@regression", "@checkout", "@smoke"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.addNotebook();
  await desk.placeOrder();
  await desk.expectResult("Order confirmed | Paid AED 40");
  await desk.openCheckout();
  await desk.expectResult("Items 0 | Total AED 0");
  await desk.placeOrder();
  await desk.expectResult("Add an item before ordering");
});

test('retain basket while tracking', { tag: ["@regression", "@checkout"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.addNotebook();
  await desk.openShipments();
  await desk.selectDubai();
  await desk.expectResult("SH-202 | Delivered");
  await desk.openCheckout();
  await desk.expectResult("Items 1 | Total AED 40");
});

test('reset voucher and delivery after payment', { tag: ["@regression", "@checkout"] }, async ({ desk }) => {
  await desk.openCheckout();
  await desk.addNotebook();
  await desk.selectExpress();
  await desk.applySave10();
  await desk.placeOrder();
  await desk.expectResult("Order confirmed | Paid AED 48");
  await desk.addNotebook();
  await desk.expectResult("Items 1 | Total AED 40");
});
