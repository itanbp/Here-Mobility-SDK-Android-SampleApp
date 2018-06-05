package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nil.test.sdk.sampleapp.R;

import static com.nil.test.sdk.sampleapp.happy_ride.HomeActivity.CONCERT_KEY;

public class OrderConfirmationActivity extends BaseActivity {

    public static final String CONFIRMATION_KEY = "CONFIRMATION_KEY";


    private String confirmationNumber;
    private TextView orderNumber;
    private ImageView cameraImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        orderNumber = (TextView) findViewById(R.id.confirmation_order_number);
        cameraImage = (ImageView) findViewById(R.id.confirmation_camera_image);

        if (getIntent().hasExtra(CONFIRMATION_KEY)) {
            confirmationNumber = getIntent().getStringExtra(CONFIRMATION_KEY);
            String text = String.format(getString(R.string.confirmation_order_number), confirmationNumber);
            orderNumber.setText(text);
        }

        cameraImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        });

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
