package com.nil.test.sdk.sampleapp.happy_ride;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.nil.test.sdk.sampleapp.happy_ride.StickersAdapter.StickerElement;
import com.nil.test.sdk.sampleapp.R;

import java.util.ArrayList;

public class StickersActivity extends BaseActivity implements StickersAdapter.ItemClickListener {


    public static final String BITMAP_KEY = "BITMAP_KEY";


    private Bitmap screenshotBitmap;
    private ImageView screenshotBackground;
    private RecyclerView stickersRecycler;
    private StickersAdapter stickersAdapter;
    private ImageView stickerFrame;
    private ImageView stickerDrag;
    private CoordinatorLayout stickersContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        screenshotBitmap = HappyRideData.getInstance().getBitmap();
        screenshotBackground.setImageBitmap(screenshotBitmap);


        stickersRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        stickersAdapter = new StickersAdapter(this, getStickersMock());
        stickersAdapter.setClickListener(this);
        stickersRecycler.setAdapter(stickersAdapter);


        stickerFrame.setOnClickListener(v -> {
            Log.v("MOTEK", "stickerFrame");
        });


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

}
