package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.here.mobility.sdk.core.net.ResponseException;
import com.here.mobility.sdk.core.net.ResponseFuture;
import com.here.mobility.sdk.core.net.ResponseListener;
import com.here.mobility.sdk.demand.CreateRideRequest;
import com.here.mobility.sdk.demand.DemandClient;
import com.here.mobility.sdk.demand.PassengerDetails;
import com.here.mobility.sdk.demand.PublicTransportRideOffer;
import com.here.mobility.sdk.demand.Ride;
import com.here.mobility.sdk.demand.RideOffer;
import com.here.mobility.sdk.demand.TaxiRideOffer;
import com.nil.test.sdk.sampleapp.R;
import com.nil.test.sdk.sampleapp.ride_offers.RideOffersActivity;
import com.nil.test.sdk.sampleapp.ride_status.RideStatusActivity;

import java.util.ArrayList;

import static com.nil.test.sdk.sampleapp.happy_ride.OrderConfirmationActivity.CONFIRMATION_KEY;
import static com.nil.test.sdk.sampleapp.ride_offers.RideOffersActivity.EXTRA_PT_RIDE_OFFER_LIST;
import static com.nil.test.sdk.sampleapp.ride_offers.RideOffersActivity.EXTRA_TAXI_RIDE_OFFER_LIST;

public class MarketplaceActivity extends BaseActivity {


    /**
     * Use DemandClient to request ride.
     */
    private DemandClient demandClient;

    private RecyclerView.Adapter offersAdapter;
    private RecyclerView offersRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        demandClient = DemandClient.newInstance(this);
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

    @Override
    protected boolean showToolbar() {
        return true;
    }


    @Override
    protected String getToolbarTitle() {
        return getString(R.string.market_place_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //It's important to call shutdown function when the client is no longer needed.
        if (demandClient != null) {
            demandClient.shutdownNow();
        }
    }


    /**
     * Future ride listener.
     */
    private ResponseListener<Ride> rideFutureListener = new ResponseListener<Ride>() {
        @Override
        public void onResponse(Ride ride) {
            Intent intent = new Intent(MarketplaceActivity.this, OrderConfirmationActivity.class);
            intent.putExtra(CONFIRMATION_KEY, ride.getRideId());
            startActivity(intent);
        }

        @Override
        public void onError(@NonNull ResponseException e) {
        }
    };


    private void initViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        offersRecycler.setHasFixedSize(true);
        offersRecycler.addItemDecoration(new RecyclerItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider), RecyclerView.VERTICAL));
        offersRecycler.setLayoutManager(layoutManager);

        //Received ride offers list from Intent.extra and update the list.
        ArrayList<RideOffer> rideOffers = getRideOffers();


        offersAdapter = new HappyRideOffersAdapter(offer -> {
            offer.accept(new RideOffer.Visitor<Void>() {
                @Override
                public Void visit(@NonNull TaxiRideOffer taxiRideOffer) {
                    requestRide(taxiRideOffer);
                    return null;
                }

                @Override
                public Void visit(@NonNull PublicTransportRideOffer publicTransportRideOffer) {
                    return null;
                }
            });
        }, rideOffers);
        offersRecycler.setAdapter(offersAdapter);

    }


    /**
     * Request to book Ride Offer.
     *
     * @param taxiRideOffer An offer for ride. should be received from RideOffersRequest.
     */
    private void requestRide(@NonNull TaxiRideOffer taxiRideOffer) {

        PassengerDetails passengerDetails = PassengerDetails.builder()
                .setName("Lior")
                .setPhoneNumber("054")
                .build();

        CreateRideRequest rideRequest = CreateRideRequest.create(taxiRideOffer.getOfferId(), passengerDetails);

        //Request to book a ride.
        ResponseFuture<Ride> rideRequestFuture = demandClient.createRide(rideRequest);

        //Register for ride request updates.
        rideRequestFuture.registerListener(rideFutureListener);

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
