package com.nil.test.sdk.sampleapp.happy_ride;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nil.test.sdk.sampleapp.R;

import java.util.ArrayList;

public class StickersActivity extends BaseActivity implements StickersAdapter.ItemClickListener {


    public static final String BITMAP_KEY = "BITMAP_KEY";


    private Bitmap screenshotBitmap;
    private ImageView screenshotBackground;
    private RecyclerView stickersRecycler;
    private StickersAdapter stickersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stickersRecycler = findViewById(R.id.stickers_gallery);

        screenshotBackground = findViewById(R.id.sticker_screenshot_background);
        screenshotBitmap = HappyRideData.getInstance().getBitmap();
        screenshotBackground.setImageBitmap(screenshotBitmap);


        stickersRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        stickersAdapter = new StickersAdapter(this, getStickersMock());
        stickersAdapter.setClickListener(this);
        stickersRecycler.setAdapter(stickersAdapter);


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


    @Override
    public void onItemClick(View view, int position) {
    }


    private ArrayList<Integer> getStickersMock() {

        ArrayList<Integer> stickers = new ArrayList<>();

        stickers.add(R.drawable.logo);
        stickers.add(R.drawable.pic_ed);
        stickers.add(R.drawable.logo);
        stickers.add(R.drawable.logo);
        stickers.add(R.drawable.logo);

        return stickers;
    }

}
