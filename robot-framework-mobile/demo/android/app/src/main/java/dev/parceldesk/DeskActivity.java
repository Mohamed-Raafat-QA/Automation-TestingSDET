package dev.parceldesk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import java.util.Arrays;

public final class DeskActivity extends Activity {
    private final String[] stages = {"Booked", "In transit", "Delivered"};
    private final String[] refs = {"SH-101", "SH-202"};
    private final int[] progress = {0, 2};
    private int selected, quantity;
    private boolean discounted, express;
    private LinearLayout panel;
    private TextView result;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        render(false);
    }

    private void button(String id, String text, Runnable action) {
        Button button = new Button(this);
        button.setText(text);
        button.setContentDescription(id);
        button.setOnClickListener(view -> action.run());
        panel.addView(button);
    }

    private void render(boolean checkout) {
        ScrollView scroll = new ScrollView(this);
        panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(20, 12, 20, 12);
        scroll.addView(panel);
        setContentView(scroll);
        TextView title = new TextView(this);
        title.setText(checkout ? "Notebook checkout" : "Track a shipment");
        title.setTextSize(23);
        panel.addView(title);
        button("tracking", "Shipments", () -> render(false));
        button("checkout", "Checkout", () -> render(true));
        result = new TextView(this);
        result.setContentDescription("result");
        result.setTextSize(17);
        panel.addView(result);
        if (checkout) {
            button("add", "Add notebook", () -> { quantity++; basket(); });
            button("remove", "Remove notebook", () -> { quantity = Math.max(0, quantity - 1); basket(); });
            button("voucher", "Apply SAVE10", () -> { discounted = true; basket(); });
            button("express", "Express delivery", () -> { express = true; basket(); });
            button("order", "Place order", this::placeOrder);
            basket();
        } else {
            button("parcel_a", "SH-101 · Abu Dhabi", () -> select(0));
            button("parcel_b", "SH-202 · Dubai", () -> select(1));
            button("advance", "Advance status", this::advance);
            button("history", "Show history", () -> showShipment(String.join(" > ", Arrays.copyOf(stages, progress[selected] + 1))));
            select(selected);
        }
    }

    private void select(int index) {
        selected = index;
        showShipment(stages[progress[index]]);
    }

    private void showShipment(String status) { result.setText(refs[selected] + " | " + status); }

    private void advance() {
        if (progress[selected] == stages.length - 1) {
            showShipment("Already delivered");
            return;
        }
        progress[selected]++;
        select(selected);
    }

    private int total() { return quantity * (discounted ? 36 : 40) + (quantity > 0 && express ? 12 : 0); }
    private void basket() { result.setText("Items " + quantity + " | Total AED " + total()); }

    private void placeOrder() {
        if (quantity == 0) {
            result.setText("Add an item before ordering");
            return;
        }
        result.setText("Order confirmed | Paid AED " + total());
        quantity = 0;
        discounted = false;
        express = false;
    }
}
