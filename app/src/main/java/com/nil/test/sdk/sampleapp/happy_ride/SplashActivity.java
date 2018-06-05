package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nil.test.sdk.sampleapp.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }, 1500);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    protected int getStatusBarColor() {
        return R.color.status_bar;
    }

}
