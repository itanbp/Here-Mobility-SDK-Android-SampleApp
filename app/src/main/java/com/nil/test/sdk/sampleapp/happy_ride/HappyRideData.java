package com.nil.test.sdk.sampleapp.happy_ride;

import android.graphics.Bitmap;

/**
 * Created by itanbp on 05/06/2018.
 */
public class HappyRideData {

    private static HappyRideData sInstance;


    private Bitmap screenshotBitmap;


    public static HappyRideData getInstance() {
        if (sInstance == null) {
            sInstance = new HappyRideData();
        }
        return sInstance;
    }


    private HappyRideData() {

    }


    public void setBitmap(Bitmap bitmap) {
        screenshotBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return screenshotBitmap;
    }
}
