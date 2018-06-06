package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.ImageView;

import com.nil.test.sdk.sampleapp.happy_ride.StickersAdapter.StickerElement;
import com.nil.test.sdk.sampleapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StickersActivity extends BaseActivity implements StickersAdapter.ItemClickListener {


    public static final String BITMAP_PATH_KEY = "BITMAP_PATH_KEY";


    private Bitmap screenshotBitmap;
    private ImageView screenshotBackground;
    private RecyclerView stickersRecycler;
    private StickersAdapter stickersAdapter;
    private ImageView stickerFrame;
    private ImageView stickerDrag;
    private CoordinatorLayout stickersContainer;


    private String bitmapPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(BITMAP_PATH_KEY)) {
            bitmapPath = getIntent().getStringExtra(BITMAP_PATH_KEY);
        }

        stickersRecycler = findViewById(R.id.stickers_gallery);
        stickerFrame = findViewById(R.id.sticker_frame);
        stickerDrag = findViewById(R.id.sticker_drag);
        stickersContainer = findViewById(R.id.stickers_container);

        stickerDrag.setOnTouchListener(new OnDragTouchListener(stickerDrag, new OnDragTouchListener.OnDragActionListener() {
            @Override
            public void onDragStart(View view) {

            }

            @Override
            public void onDragEnd(View view) {

            }

            @Override
            public void onClick() {
                Log.v("MOTEK", "stickerDrag");
            }
        }));

        screenshotBackground = findViewById(R.id.sticker_screenshot_background);


        stickersRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        stickersAdapter = new StickersAdapter(this, getStickersMock());
        stickersAdapter.setClickListener(this);
        stickersRecycler.setAdapter(stickersAdapter);


        stickerFrame.setOnClickListener(v -> {
            Log.v("MOTEK", "stickerFrame");
        });

        setBackgroundImage();

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
    public void onItemClick(View view, StickerElement stickerElement) {

        if (stickerElement == null) {
            return;
        }

        int index = stickerElement.index;
        int drawableId = stickerElement.drawableIds.get(index);

        if (stickerElement.dragable) {
            stickerDrag.setImageResource(drawableId);
        } else {
            stickerFrame.setImageResource(drawableId);
        }
    }


    private ArrayList<StickerElement> getStickersMock() {

        ArrayList<StickerElement> stickers = new ArrayList<>();
        ArrayList<Integer> drawableIds = new ArrayList<>();

        drawableIds.add(R.drawable.logo);
        stickers.add(new StickerElement(drawableIds, false));

        drawableIds = new ArrayList<>();
        drawableIds.add(R.drawable.pic_ed);
        stickers.add(new StickerElement(drawableIds, false));

        drawableIds = new ArrayList<>();
        drawableIds.add(R.drawable.logo);
        stickers.add(new StickerElement(drawableIds, true));

        drawableIds = new ArrayList<>();
        drawableIds.add(R.drawable.ic_launcher);
        stickers.add(new StickerElement(drawableIds, true));

        drawableIds = new ArrayList<>();
        drawableIds.add(R.drawable.ic_tickets);
        stickers.add(new StickerElement(drawableIds, true));

        return stickers;
    }


    private void setBackgroundImage() {

        if (TextUtils.isEmpty(bitmapPath)) {
            return;
        }

        File imgFile = new File(bitmapPath);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            screenshotBackground.setImageBitmap(myBitmap);

        }
    }

    private void shareBitmap(String bitmapPath) {

        Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");

        if (intent != null) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage("com.instagram.android");


            try {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmapPath, "GO Mobility", "MoBiLiTy")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            shareIntent.setType("image/jpeg");
            startActivity(shareIntent);


        }
    }


}
