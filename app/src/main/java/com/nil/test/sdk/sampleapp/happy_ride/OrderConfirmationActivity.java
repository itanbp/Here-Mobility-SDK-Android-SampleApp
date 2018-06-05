package com.nil.test.sdk.sampleapp.happy_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nil.test.sdk.sampleapp.R;

import static com.nil.test.sdk.sampleapp.happy_ride.HomeActivity.CONCERT_KEY;

public class OrderConfirmationActivity extends BaseActivity {

    public static final String CONFIRMATION_KEY = "CONFIRMATION_KEY";


    private String confirmationNumber;
    private TextView orderNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        orderNumber = (TextView) findViewById(R.id.confirmation_order_number);

        if (getIntent().hasExtra(CONFIRMATION_KEY)) {
            confirmationNumber = getIntent().getStringExtra(CONFIRMATION_KEY);
            String text = String.format(getString(R.string.confirmation_order_number), confirmationNumber);
            orderNumber.setText(text);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_confirmation;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.status_bar;
    }


}
