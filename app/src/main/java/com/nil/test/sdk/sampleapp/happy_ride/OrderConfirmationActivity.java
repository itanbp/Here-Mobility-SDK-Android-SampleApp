package com.nil.test.sdk.sampleapp.happy_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nil.test.sdk.sampleapp.R;

public class OrderConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
