package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nil.test.sdk.sampleapp.R;

import java.util.ArrayList;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;

import static com.nil.test.sdk.sampleapp.happy_ride.HomeActivity.CONCERT_KEY;

public class OrderRideActivity extends BaseActivity implements IStepperAdapter {


    private VerticalStepperView stepperView;
    private Concert concert;
    private Button orderRideButton;
    private String concertSelectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().hasExtra(CONCERT_KEY)) {
            concert = getIntent().getParcelableExtra(CONCERT_KEY);
        }

        stepperView = findViewById(R.id.order_ride_stepper);
        stepperView.setStepperAdapter(this);

        stepperView.nextStep(); // first step already filled

        orderRideButton = (Button) findViewById(R.id.order_ride_button);

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

        String title;

        switch (index) {
            case 0:
                title = "For which show do you order HappyRide?";
                break;

            case 1:
                title = "On what date you want to go?";
                break;

            case 2:
                title = "From where to pick you up?";
                break;

            case 3:
                title = "What time should the taxi wait for you?";
                break;

            case 4:
                title = "Who is riding along with you?";
                break;

            default:
                title = "";
                break;
        }

        return title;
    }

    @Override
    public @Nullable
    CharSequence getSummary(int index) {

        String summary;

        switch (index) {
            case 0:
                if (concert != null) {
                    summary = concert.title;
                } else {
                    summary = null;
                }
                break;

            case 1:
                summary = concertSelectedDate;
                break;

            case 2:
                summary = null;
                break;

            case 3:
                summary = null;
                break;

            case 4:
                summary = null;
                break;

            default:
                summary = null;
                break;
        }

        return summary;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public View onCreateCustomView(final int index, Context context, VerticalStepperItemView parent) {

        View inflateView;

        switch (index) {

            case 0:
            case 1:
                inflateView = LayoutInflater.from(context).inflate(R.layout.order_ride_item_2, parent, false);
                setStep2(inflateView);
                break;

            case 2:
                inflateView = LayoutInflater.from(context).inflate(R.layout.order_ride_item_2, parent, false);
                break;

            case 3:
                inflateView = LayoutInflater.from(context).inflate(R.layout.order_ride_item_2, parent, false);
                break;

            case 4:
                inflateView = LayoutInflater.from(context).inflate(R.layout.order_ride_item_2, parent, false);
                break;

            default:
                inflateView = LayoutInflater.from(context).inflate(R.layout.order_ride_item_2, parent, false);
                break;

        }

        Button okButton = inflateView.findViewById(R.id.button_next);
        Button cancelButton = inflateView.findViewById(R.id.button_prev);

        okButton.setOnClickListener(v -> {
            if (stepperView.canNext()) {
                stepperView.nextStep();
            } else if (stepperView.getCurrentStep() == 4) { // last step
                orderRideButton.setTextColor(ContextCompat.getColor(this, R.color.white));
                orderRideButton.setEnabled(true);
            }
        });

        cancelButton.setOnClickListener(v -> {
            if (stepperView.canPrev()) {
                stepperView.prevStep();
            }
        });

        return inflateView;
    }

    @Override
    public void onShow(int index) {

    }

    @Override
    public void onHide(int index) {

    }


    @Override
    protected boolean showToolbar() {
        return true;
    }


    @Override
    protected String getToolbarTitle() {
        return getString(R.string.order_title);
    }


    private void setStep2(View view) {
        if (concert != null) {

            ArrayList<String> dates = concert.getDates();
            RadioGroup radioGroup = view.findViewById(R.id.order_ride_radio_group);

            ((RadioButton) radioGroup.getChildAt(0)).setText(dates.get(0));
            ((RadioButton) radioGroup.getChildAt(1)).setText(dates.get(1));

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                switch (checkedId) {
                    case R.id.order_ride_radio_button_1:
                        concertSelectedDate = dates.get(0);
                        break;

                    case R.id.order_ride_radio_button_2:
                        concertSelectedDate = dates.get(1);
                        break;
                }

            });
        }
    }


}
