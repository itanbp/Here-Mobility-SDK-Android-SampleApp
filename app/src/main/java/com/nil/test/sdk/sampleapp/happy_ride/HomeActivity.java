package com.nil.test.sdk.sampleapp.happy_ride;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.here.mobility.sdk.common.util.PermissionUtils;
import com.here.mobility.sdk.core.auth.UserAuthenticationException;
import com.here.mobility.sdk.core.net.ResponseException;
import com.here.mobility.sdk.core.net.ResponseListener;
import com.here.mobility.sdk.demand.DemandClient;
import com.here.mobility.sdk.demand.RideQuery;
import com.here.mobility.sdk.demand.RideQueryResponse;
import com.here.mobility.sdk.map.FusedUserLocationSource;
import com.here.mobility.sdk.map.MapController;
import com.here.mobility.sdk.map.MapFragment;
import com.here.mobility.sdk.map.MapView;
import com.nil.test.sdk.sampleapp.R;
import com.nil.test.sdk.sampleapp.get_rides.GetRidesActivity;
import com.nil.test.sdk.sampleapp.registration.RegistrationDialog;
import com.nil.test.sdk.sampleapp.util.AuthUtils;
import com.nil.test.sdk.sampleapp.util.Constant;


public class HomeActivity extends BaseActivity implements MapView.MapReadyListener {

    public static final String CONCERT_KEY = "CONCERT_KEY";

    @NonNull
    private static final String LOG_TAG = GetRidesActivity.class.getSimpleName();

    /**
     * Location permission code.
     */
    private static final int LOCATION_PERMISSIONS_CODE = 42;


    /**
     * MapController zoom level.
     */
    private static final float MAP_ZOOM = 14.5f;


    /**
     * Used to interact with the map.
     */
    private MapController mapController;

    /**
     * Use DemandClient to request ride offers.
     */
    private DemandClient demandClient;


    private RecyclerView.Adapter galleryAdapter;
    private RecyclerView galleryRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        galleryRecycler = (RecyclerView) findViewById(R.id.home_concerts_gallery);

        //Initialize DemandClient.
        demandClient = DemandClient.newInstance(this);

        initViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    private void initViews() {

        // MapFragment initialization
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.loadMapAsync(this);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        galleryRecycler.setHasFixedSize(true);
        galleryRecycler.addItemDecoration(new RecyclerItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider)));
        galleryRecycler.setLayoutManager(layoutManager);

        galleryAdapter = new ConcertsAdapter(concert -> {
            Intent intent = new Intent(this, OrderRideActivity.class);
            intent.putExtra(CONCERT_KEY, concert);
            startActivity(intent);
        });
        galleryRecycler.setAdapter(galleryAdapter);

    }


    /**
     * this callback is called when the map is set-up, before we render any tiles to the screen - so this is the place to set those values
     *
     * @param mapController map controller to interact with the map.
     */
    @Override
    public void onMapReady(@NonNull MapController mapController) {

        this.mapController = mapController;
        //Set the map center position.
        mapController.setPosition(Constant.CENTER_OF_LONDON);
        //Set map zoom.
        mapController.setZoom(MAP_ZOOM);

        if (!PermissionUtils.hasAnyLocationPermissions(this)) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSIONS_CODE);
        } else {
            startLocationUpdates();
        }
    }


    /**
     * Get rides response listener.
     */
    @NonNull
    private ResponseListener<RideQueryResponse> getRideListener = new ResponseListener<RideQueryResponse>() {

        @Override
        public void onResponse(@NonNull RideQueryResponse rideQueryResponse) {
            //setActiveRides(rideQueryResponse.getRides());
        }

        @Override
        public void onError(@NonNull ResponseException e) {
            if(e.getCause().getCause() instanceof UserAuthenticationException){
                showRegistrationDialog();
            }else{
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    protected void onStart() {
        super.onStart();

        // Get Active rides.
        getActiveRides();
    }


    /**
     * Show registration dialog.
     */
    private void showRegistrationDialog() {
        RegistrationDialog dialog = new RegistrationDialog(HomeActivity.this);
        dialog.setPositiveButton(R.string.register, (d, which) -> {
            String userName = dialog.getUserName();
            if (!userName.isEmpty()) {

                // The user registration should be done with your app's backend (see the documentation for more info).
                // This is a snippet to generate the token in the app, for testing purposes.
                AuthUtils.registerUser(userName,
                        getString(R.string.here_sdk_app_id),
                        getString(R.string.here_sdk_app_secret));
                //getActiveRides();
            } else {
                Toast.makeText(this, R.string.register_not_valid_user_name, Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    /**
     * Request ride attach to user.
     */
    private void getActiveRides() {

        //Build a ride query.
        RideQuery rideQuery = RideQuery.builder()
                .setStatusFilter(RideQuery.StatusFilter.ALL)
                .build();

        //get the rides and register a listener.
        demandClient.getRides(rideQuery).registerListener(getRideListener);
    }

    @Override
    public void onMapFailure(@NonNull Exception e) {
        Log.e(LOG_TAG, "onMapFailure: ", e);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSIONS_CODE) {
            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
        }
    }


    @Override
    protected int getStatusBarColor() {
        return R.color.material_grey_400;
    }


    /**
     * Start user location updates.
     */
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {

        mapController.getUserLocationMarkerManager().setLocationSource(new FusedUserLocationSource(this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //It's important to call shutdownNow function when the client is no longer needed.
        if (demandClient != null) {
            demandClient.shutdownNow();
        }
    }



}
