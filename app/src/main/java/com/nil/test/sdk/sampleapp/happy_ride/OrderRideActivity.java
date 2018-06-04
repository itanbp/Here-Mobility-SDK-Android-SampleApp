package com.nil.test.sdk.sampleapp.happy_ride;

import android.os.Bundle;

import com.nil.test.sdk.sampleapp.R;

public class OrderRideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_ride;
    }


    @Override
    protected int getStatusBarColor() {
        return R.color.status_bar;
    }


}
