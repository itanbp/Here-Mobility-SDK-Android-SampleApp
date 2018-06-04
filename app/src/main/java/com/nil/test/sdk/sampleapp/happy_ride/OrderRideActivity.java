package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nil.test.sdk.sampleapp.R;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;

public class OrderRideActivity extends BaseActivity implements IStepperAdapter {


    private VerticalStepperView stepperView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stepperView = findViewById(R.id.order_ride_stepper);
        stepperView.setStepperAdapter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_ride;
    }


    @Override
    protected int getStatusBarColor() {
        return R.color.material_grey_400;
    }


    @Override
    public @NonNull
    CharSequence getTitle(int index) {
        return "Step " + index;
    }

    @Override
    public @Nullable
    CharSequence getSummary(int index) {
        switch (index) {
            default:
                return null;
        }
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public View onCreateCustomView(final int index, Context context, VerticalStepperItemView parent) {

        View inflateView = LayoutInflater.from(context).inflate(R.layout.order_ride_item, parent, false);


        return inflateView;
    }

    @Override
    public void onShow(int index) {

    }

    @Override
    public void onHide(int index) {

    }


}
