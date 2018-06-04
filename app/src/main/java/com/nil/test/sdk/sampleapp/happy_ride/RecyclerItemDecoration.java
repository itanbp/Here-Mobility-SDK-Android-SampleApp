package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nil.test.sdk.sampleapp.R;

/**
 * Created by itanbp on 13/03/2018.
 */

public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    private int mMargin;
    private int mOrientation;


    public RecyclerItemDecoration(int margin) {
        this(margin, RecyclerView.HORIZONTAL);
    }


    public RecyclerItemDecoration(int margin, int orientation) {
        mMargin = margin;
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent != null) {
            int itemPosition = parent.getChildAdapterPosition(view);
            int totalCount = parent.getAdapter().getItemCount();

            if (itemPosition >= 0 && itemPosition < totalCount - 1) {
                if (mOrientation == RecyclerView.HORIZONTAL) {
                    outRect.right = mMargin;
                } else {
                    outRect.bottom = mMargin;
                }
            }
        }
    }
}
