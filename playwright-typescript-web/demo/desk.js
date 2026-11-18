const byId = (id) => document.getElementById(id);
const stages = ["Booked", "In transit", "Delivered"];
const parcels = { "SH-101": 0, "SH-202": 2 };
let selected = "SH-101";
let quantity = 0;
let discounted = false;
let express = false;

function display(message) { byId("result").textContent = message; }
function shipment() { display(selected + " | " + stages[parcels[selected]]); }
function total() { return quantity * (discounted ? 36 : 40) + (express && quantity ? 12 : 0); }
function basket() { display("Items " + quantity + " | Total AED " + total()); }
function navigate(toBasket) {
  byId("basket").hidden = !toBasket;
  byId("shipments").hidden = toBasket;
  byId("tracking").setAttribute("aria-pressed", String(!toBasket));
  byId("checkout").setAttribute("aria-pressed", String(toBasket));
  if (toBasket) basket();
  else shipment();
}

byId("tracking").onclick = () => navigate(false);
byId("checkout").onclick = () => navigate(true);
for (const [id, ref] of [["parcel_a", "SH-101"], ["parcel_b", "SH-202"]]) {
  byId(id).onclick = () => { selected = ref; shipment(); };
}
byId("advance").onclick = () => {
  if (parcels[selected] === stages.length - 1) return display(selected + " | Already delivered");
  parcels[selected]++;
  shipment();
};
byId("history").onclick = () => display(selected + " | " + stages.slice(0, parcels[selected] + 1).join(" > "));
byId("add").onclick = () => { quantity++; basket(); };
byId("remove").onclick = () => { quantity = Math.max(0, quantity - 1); basket(); };
byId("voucher").onclick = () => { discounted = true; basket(); };
byId("express").onclick = () => { express = true; basket(); };
byId("order").onclick = () => {
  if (!quantity) return display("Add an item before ordering");
  display("Order confirmed | Paid AED " + total());
  quantity = 0;
  discounted = false;
  express = false;
};
navigate(false);
