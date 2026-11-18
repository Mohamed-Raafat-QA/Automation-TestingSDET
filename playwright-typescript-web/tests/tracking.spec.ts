import { test } from './fixtures';

test('view booked shipment', { tag: ["@regression", "@tracking", "@smoke"] }, async ({ desk }) => {
  await desk.selectAbuDhabi();
  await desk.expectResult("SH-101 | Booked");
});

test('view delivered shipment', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.selectDubai();
  await desk.expectResult("SH-202 | Delivered");
});

test('advance booked shipment', { tag: ["@regression", "@tracking", "@smoke"] }, async ({ desk }) => {
  await desk.selectAbuDhabi();
  await desk.advanceShipment();
  await desk.expectResult("SH-101 | In transit");
});

test('record complete shipment history', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.selectAbuDhabi();
  await desk.advanceShipment();
  await desk.advanceShipment();
  await desk.showHistory();
  await desk.expectResult("SH-101 | Booked > In transit > Delivered");
});

test('prevent duplicate delivery event', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.selectDubai();
  await desk.advanceShipment();
  await desk.expectResult("SH-202 | Already delivered");
  await desk.showHistory();
  await desk.expectResult("SH-202 | Booked > In transit > Delivered");
});

test('keep shipments independent', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.selectAbuDhabi();
  await desk.advanceShipment();
  await desk.selectDubai();
  await desk.expectResult("SH-202 | Delivered");
  await desk.selectAbuDhabi();
  await desk.expectResult("SH-101 | In transit");
});
