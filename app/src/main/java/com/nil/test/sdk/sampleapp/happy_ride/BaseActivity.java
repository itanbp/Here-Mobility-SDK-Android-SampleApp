package com.nil.test.sdk.sampleapp.happy_ride;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.nil.test.sdk.sampleapp.R;
import com.nil.test.sdk.sampleapp.geocoding.AutoCompleteActivity;

/**
 * Created by itanbp on 03/06/2018.
 */
public abstract class BaseActivity extends AppCompatActivity {


    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setToolbar();
        setStatusBarColor();
    }

    protected abstract int getLayoutId();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
        */
        return true;
    }


    private void setToolbar() {

        if (mToolbar == null) { // not all Activities has Toolbar
            return;
        }

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        //mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }


    protected void setStatusBarColor() {

        int color = ContextCompat.getColor(this, getStatusBarColor());

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(color);
    }


    abstract int getStatusBarColor();



}
