package com.nil.test.sdk.sampleapp.happy_ride;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.common.collect.Lists;
import com.here.mobility.sdk.demand.RideOffer;
import com.nil.test.sdk.sampleapp.R;

import java.util.ArrayList;

import static com.nil.test.sdk.sampleapp.ride_offers.RideOffersActivity.EXTRA_PT_RIDE_OFFER_LIST;
import static com.nil.test.sdk.sampleapp.ride_offers.RideOffersActivity.EXTRA_TAXI_RIDE_OFFER_LIST;

public class MarketplaceActivity extends BaseActivity {


    private RecyclerView.Adapter offersAdapter;
    private RecyclerView offersRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        offersRecycler = (RecyclerView) findViewById(R.id.marketplace_offers_recycler);

        initViews();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_marketplace;
    }


    @Override
    protected int getStatusBarColor() {
        return R.color.status_bar;
    }


    private void initViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        offersRecycler.setHasFixedSize(true);
        offersRecycler.addItemDecoration(new RecyclerItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider), RecyclerView.VERTICAL));
        offersRecycler.setLayoutManager(layoutManager);

        //Received ride offers list from Intent.extra and update the list.
        ArrayList<RideOffer> rideOffers = getRideOffers();


        offersAdapter = new HappyRideOffersAdapter(null, rideOffers);
        offersRecycler.setAdapter(offersAdapter);

    }


    /**
     * Getter. Extra list of ride offers
     *
     * @return list of ride offers
     * @throws RuntimeException In case RideOffer list is empty or null.
     */
    @NonNull
    private ArrayList<RideOffer> getRideOffers() {

        ArrayList<RideOffer> rideOffers = Lists.newArrayList();

        if (getIntent().hasExtra(EXTRA_TAXI_RIDE_OFFER_LIST)) {
            rideOffers.addAll(getIntent().getParcelableArrayListExtra(EXTRA_TAXI_RIDE_OFFER_LIST));
        }
        if (getIntent().hasExtra(EXTRA_PT_RIDE_OFFER_LIST)) {
            rideOffers.addAll(getIntent().getParcelableArrayListExtra(EXTRA_PT_RIDE_OFFER_LIST));
        }

        return rideOffers;
    }


}
