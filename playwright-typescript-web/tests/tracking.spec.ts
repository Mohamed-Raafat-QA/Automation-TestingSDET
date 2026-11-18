import { test } from './fixtures';

test('view booked shipment', { tag: ["@regression", "@tracking", "@smoke"] }, async ({ desk }) => {
  await desk.tracking.selectAbuDhabi();
  await desk.expectResult("SH-101 | Booked");
});

test('view delivered shipment', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.tracking.selectDubai();
  await desk.expectResult("SH-202 | Delivered");
});

test('advance booked shipment', { tag: ["@regression", "@tracking", "@smoke"] }, async ({ desk }) => {
  await desk.tracking.selectAbuDhabi();
  await desk.tracking.advanceShipment();
  await desk.expectResult("SH-101 | In transit");
});

test('record complete shipment history', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.tracking.selectAbuDhabi();
  await desk.tracking.advanceShipment();
  await desk.tracking.advanceShipment();
  await desk.tracking.showHistory();
  await desk.expectResult("SH-101 | Booked > In transit > Delivered");
});

test('prevent duplicate delivery event', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.tracking.selectDubai();
  await desk.tracking.advanceShipment();
  await desk.expectResult("SH-202 | Already delivered");
  await desk.tracking.showHistory();
  await desk.expectResult("SH-202 | Booked > In transit > Delivered");
});

test('keep shipments independent', { tag: ["@regression", "@tracking"] }, async ({ desk }) => {
  await desk.tracking.selectAbuDhabi();
  await desk.tracking.advanceShipment();
  await desk.tracking.selectDubai();
  await desk.expectResult("SH-202 | Delivered");
  await desk.tracking.selectAbuDhabi();
  await desk.expectResult("SH-101 | In transit");
});
