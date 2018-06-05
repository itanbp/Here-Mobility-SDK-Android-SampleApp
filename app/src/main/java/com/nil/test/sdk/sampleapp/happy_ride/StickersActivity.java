package com.nil.test.sdk.sampleapp.happy_ride;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nil.test.sdk.sampleapp.R;

public class StickersActivity extends BaseActivity {


    public static final String BITMAP_KEY = "BITMAP_KEY";


    private Bitmap screenshotBitmap;
    private ImageView screenshotBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        if (getIntent().hasExtra(BITMAP_KEY)) {
            screenshotBitmap = getIntent().getParcelableExtra(BITMAP_KEY);
        }
        */

        screenshotBackground = findViewById(R.id.sticker_screenshot_background);
        screenshotBitmap = HappyRideData.getInstance().getBitmap();
        screenshotBackground.setImageBitmap(screenshotBitmap);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_stickers;
    }


    @Override
    protected int getStatusBarColor() {
        return R.color.material_grey_400;
    }


    @Override
    protected boolean showToolbar() {
        return true;
    }


    @Override
    protected String getToolbarTitle() {
        return "";
    }



}
