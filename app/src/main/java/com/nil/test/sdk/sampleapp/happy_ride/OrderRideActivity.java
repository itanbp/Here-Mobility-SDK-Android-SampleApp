package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.here.mobility.sdk.common.util.Cancelable;
import com.here.mobility.sdk.core.geo.LatLng;
import com.here.mobility.sdk.core.net.ResponseException;
import com.here.mobility.sdk.core.net.ResponseFuture;
import com.here.mobility.sdk.core.net.ResponseListener;
import com.here.mobility.sdk.map.geocoding.GeocodingClient;
import com.here.mobility.sdk.map.geocoding.GeocodingRequest;
import com.here.mobility.sdk.map.geocoding.GeocodingResponse;
import com.here.mobility.sdk.map.geocoding.GeocodingResult;
import com.nil.test.sdk.sampleapp.R;
import com.nil.test.sdk.sampleapp.geocoding.AutoCompleteActivity;
import com.nil.test.sdk.sampleapp.geocoding.AutocompleteAdapter;
import com.nil.test.sdk.sampleapp.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;

import static com.nil.test.sdk.sampleapp.happy_ride.HomeActivity.CONCERT_KEY;

public class OrderRideActivity extends BaseActivity implements IStepperAdapter {


    private VerticalStepperView stepperView;
    private Concert concert;
    private Button orderRideButton;
    private String concertSelectedDate;

    /**
     * Geocoding client is the channel for forward and reverse geocoding request.
     * note that this client must be shutdown by calling {@link GeocodingClient#shutdown()} after the client is no longer needed.
     */
    private GeocodingClient autocompleteClient;

    /**
     * Current geocoding request. Store it so we can cancel it when new request is sent.
     */
    @Nullable
    private Cancelable autocompleteResponseFuture = null;

    /**
     * Autocomplete adapter.
     */
    private AutocompleteAdapter adapter;


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

        autocompleteClient = new GeocodingClient(this);

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
                inflateView = LayoutInflater.from(context).inflate(R.layout.order_ride_item_3, parent, false);
                setStep3(inflateView);
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

    @Override
    protected void onDestroy() {

        super.onDestroy();

        //It's important to call shutdown function when the client is no longer needed.
        if (autocompleteClient != null) {
            autocompleteClient.shutdownNow();
        }
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


    private void setStep3(View view) {

        EditText searchAddressEdit = view.findViewById(R.id.search_address_edit_text);
        searchAddressEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                onSearchAddressTextChanged(s.toString());
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RecyclerView autoCompleteRecyclerView = view.findViewById(R.id.auto_complete_recycler_view);
        adapter = new AutocompleteAdapter(adapterListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        autoCompleteRecyclerView.setLayoutManager(layoutManager);
        autoCompleteRecyclerView.setItemAnimator(new DefaultItemAnimator());
        autoCompleteRecyclerView.setAdapter(adapter);

    }


    /**
     * Callback method called when an address item did click.
     */
    @NonNull
    private AutocompleteAdapter.AutoCompleteItemClicked adapterListener = (int position, GeocodingResult selected) -> {
        //resultIntent.putExtra(GEOCODING_RESULT, selected);
    };


    /**
     * Called when search address text field changed.
     * Send geocoding request just when text length more than one char.
     *
     * @param text the query for forward geocoding
     */
    private void onSearchAddressTextChanged(@NonNull String text) {

        //Cancel recent geocoding request.
        if (autocompleteResponseFuture != null) {
            autocompleteResponseFuture.cancel();
            autocompleteResponseFuture = null;
        }

        int GEOCODING_REQUEST_MIN_CHAR = 2;
        if (text.length() >= GEOCODING_REQUEST_MIN_CHAR) {
            //updateLastKnownLocation();
            geocodingRequest(text);
        } else {
            //clean autocomplete list.
            if (adapter != null) {
                adapter.setDataSource(Collections.emptyList());
                adapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * Forward geocoding by given a textual query.
     *
     * @param query the query for forward geocoding.
     */
    private void geocodingRequest(@NonNull String query) {

        //User current location.
        LatLng location = Constant.CENTER_OF_LONDON;
        /*
        if (lastKnownLocation != null) {
            location = LatLng.fromLocation(lastKnownLocation);
        }else{
            location = Constant.CENTER_OF_LONDON;
        }
        */

        String languageCode = Locale.getDefault().getISO3Language();

        //The result type can be - ADDRESS:     and or PLACE:
        Set<GeocodingResult.Type> resultTypes = EnumSet.of(GeocodingResult.Type.PLACE);

        //Create forward geocoding request.
        GeocodingRequest geocodingRequest = GeocodingRequest.newForwardRequest(
                query,
                location, //The location around which to search for results.
                //put here countryCode if you want suggestions only from a specific country.
                null, //ISO 3166 alpha 3 country to code used filter results.
                languageCode, //ISO 639-1 language code for the preferred language of the results
                resultTypes); // The result types to obtain.

        //send the request.
        ResponseFuture<GeocodingResponse> autocompleteResponse = autocompleteClient.geocode(geocodingRequest);

        //register listener for updates.
        autocompleteResponse.registerListener(geocodingResponseResponseListener);

        //Store it so we can cancel it when new request is sent.
        this.autocompleteResponseFuture = autocompleteResponse;
    }


    /**
     * Geocoding response listener.
     */
    @NonNull
    private ResponseListener<GeocodingResponse> geocodingResponseResponseListener = new ResponseListener<GeocodingResponse>() {
        @Override
        public void onResponse(@NonNull GeocodingResponse geocodingResponse) {

            List<GeocodingResult> results = geocodingResponse.getResults();
            adapter.setDataSource(results);
            adapter.notifyDataSetChanged();
        }


        @Override
        public void onError(@NonNull ResponseException exception) {

        }
    };

}
