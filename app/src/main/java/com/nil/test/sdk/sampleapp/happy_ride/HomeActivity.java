package com.nil.test.sdk.sampleapp.happy_ride;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.here.mobility.sdk.common.util.PermissionUtils;
import com.here.mobility.sdk.map.FusedUserLocationSource;
import com.here.mobility.sdk.map.MapController;
import com.here.mobility.sdk.map.MapFragment;
import com.here.mobility.sdk.map.MapView;
import com.nil.test.sdk.sampleapp.R;
import com.nil.test.sdk.sampleapp.get_rides.GetRidesActivity;
import com.nil.test.sdk.sampleapp.util.Constant;


public class HomeActivity extends AppCompatActivity implements MapView.MapReadyListener {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
    }


    private void initViews() {

        // MapFragment initialization
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.loadMapAsync(this);
        }

    }



    /**
     * this callback is called when the map is set-up, before we render any tiles to the screen - so this is the place to set those values
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


    @Override
    public void onMapFailure(@NonNull Exception e) {
        Log.e(LOG_TAG, "onMapFailure: ", e);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == LOCATION_PERMISSIONS_CODE){
            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startLocationUpdates();
            }
        }
    }


    /**
     * Start user location updates.
     */
    @SuppressLint("MissingPermission")
    private void startLocationUpdates(){

        mapController.getUserLocationMarkerManager().setLocationSource(new FusedUserLocationSource(this));

    }


}
