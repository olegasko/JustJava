package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        displayMessage(createOrderSummary(quantity));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JastJava order for " + getName());
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(quantity));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity) {
        return quantity * (5 + addChocolate() + addWhippedCream());
    }

    private String createOrderSummary(int quantity) {
        return getString(R.string.name) + ": " + getName() + "\n"
                + getString(R.string.whipped_cream) + ": $" + addWhippedCream() +"\n"
                + getString(R.string.chocolate) + ": $" + addChocolate() +"\n"
                + getString(R.string.quantity) + ": " + quantity + "\n"
                + getString(R.string.total) + ": $" + calculatePrice(quantity) + "\n"
                + getString(R.string.thank_you);
    }

    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this, "You can't order more than 100 cups of coffee", Toast.LENGTH_SHORT)
            .show();
            return;
        }
        quantity++;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "You can't order less than 1 cup of coffee", Toast.LENGTH_SHORT)
            .show();
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int addWhippedCream() {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return whippedCream.isChecked() ? 1 : 0;
    }

    private int addChocolate() {
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return chocolate.isChecked() ? 2 : 0;
    }

    private String getName() {
        EditText name = (EditText) findViewById(R.id.name_input);
        return name.getText().toString();
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
