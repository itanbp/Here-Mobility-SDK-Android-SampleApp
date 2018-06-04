package com.nil.test.sdk.sampleapp.happy_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.here.mobility.sdk.map.MapFragment;
import com.nil.test.sdk.sampleapp.R;

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


    private void initViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        offersRecycler.setHasFixedSize(true);
        offersRecycler.addItemDecoration(new RecyclerItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider)));
        offersRecycler.setLayoutManager(layoutManager);

        offersAdapter = new HappyRideOffersAdapter(null);
        offersRecycler.setAdapter(offersAdapter);

    }


}
