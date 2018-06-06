package com.nil.test.sdk.sampleapp.happy_ride;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by itanbp on 06/06/2018.
 */
public class OnDragTouchListener implements View.OnTouchListener {

    /**
     * Callback used to indicate when the drag is finished
     */
    public interface OnDragActionListener {
        /**
         * Called when drag event is started
         *
         * @param view The view dragged
         */
        void onDragStart(View view);

        /**
         * Called when drag event is completed
         *
         * @param view The view dragged
         */
        void onDragEnd(View view);

        void onClick();
    }

    private View mView;
    private View mParent;
    private boolean isDragging;
    private boolean isInitialized = false;

    private int width;
    private float xWhenAttached;
    private float maxLeft;
    private float maxRight;
    private float dX;

    private int height;
    private float yWhenAttached;
    private float maxTop;
    private float maxBottom;
    private float dY;

    private GestureDetector gestureDetector;


    private OnDragActionListener mOnDragActionListener;

    public OnDragTouchListener(View view) {
        this(view, (View) view.getParent(), null);
    }

    public OnDragTouchListener(View view, View parent) {
        this(view, parent, null);
    }

    public OnDragTouchListener(View view, OnDragActionListener onDragActionListener) {
        this(view, (View) view.getParent(), onDragActionListener);
    }

    public OnDragTouchListener(View view, View parent, OnDragActionListener onDragActionListener) {
        initListener(view, parent);
        setOnDragActionListener(onDragActionListener);

        gestureDetector = new GestureDetector(view.getContext(), new SingleTapConfirm());

    }

    public void setOnDragActionListener(OnDragActionListener onDragActionListener) {
        mOnDragActionListener = onDragActionListener;
    }

    public void initListener(View view, View parent) {
        mView = view;
        mParent = parent;
        isDragging = false;
        isInitialized = false;
    }

    public void updateBounds() {
        updateViewBounds();
        updateParentBounds();
        isInitialized = true;
    }

    public void updateViewBounds() {
        width = mView.getWidth();
        xWhenAttached = mView.getX();
        dX = 0;

        height = mView.getHeight();
        yWhenAttached = mView.getY();
        dY = 0;
    }

    public void updateParentBounds() {
        maxLeft = 0;
        maxRight = maxLeft + mParent.getWidth();

        maxTop = 0;
        maxBottom = maxTop + mParent.getHeight();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (gestureDetector.onTouchEvent(event)) { // single tap

            if (mOnDragActionListener != null) {
                mOnDragActionListener.onClick();
            }
            return true;

        } else {


            if (isDragging) {
                float[] bounds = new float[4];
                // LEFT
                bounds[0] = event.getRawX() + dX;
                if (bounds[0] < maxLeft) {
                    bounds[0] = maxLeft;
                }
                // RIGHT
                bounds[2] = bounds[0] + width;
                if (bounds[2] > maxRight) {
                    bounds[2] = maxRight;
                    bounds[0] = bounds[2] - width;
                }
                // TOP
                bounds[1] = event.getRawY() + dY;
                if (bounds[1] < maxTop) {
                    bounds[1] = maxTop;
                }
                // BOTTOM
                bounds[3] = bounds[1] + height;
                if (bounds[3] > maxBottom) {
                    bounds[3] = maxBottom;
                    bounds[1] = bounds[3] - height;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        onDragFinish();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mView.animate().x(bounds[0]).y(bounds[1]).setDuration(0).start();
                        break;
                }
                return true;
            } else {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        /*
                        if (v.getX() - event.getRawX() < 10 && v.getY() - event.getRawY() < 10) {
                            return true;
                        }
                        */

                        isDragging = true;
                        if (!isInitialized) {
                            updateBounds();
                        }

                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();

                        if (mOnDragActionListener != null) {
                            mOnDragActionListener.onDragStart(mView);
                        }
                        return true;

                    /*
                case MotionEvent.ACTION_UP:
                    if (mOnDragActionListener != null) {
                        mOnDragActionListener.onClick();
                    }
                    break;
                    */
                }
            }
            return false;

        }
    }

    private void onDragFinish() {
        if (mOnDragActionListener != null) {
            mOnDragActionListener.onDragEnd(mView);
        }

        /*
        if (mOnDragActionListener != null) {
            mOnDragActionListener.onClick();
        }
        */

        dX = 0;
        dY = 0;
        isDragging = false;
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

}
