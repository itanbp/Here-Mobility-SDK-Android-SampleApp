package com.nil.test.sdk.sampleapp.happy_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.nil.test.sdk.sampleapp.R;

import ai.deepar.ar.CameraGrabber;
import ai.deepar.ar.DeepAR;

public class CameraActivity extends PermissionsActivity {


    private SurfaceView arView;
    private ImageView captureButton;
    private ImageView switchMaskButton;

    private CameraGrabber cameraGrabber;
    private DeepAR deepAR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }


    @Override
    protected int getStatusBarColor() {
        return R.color.material_grey_400;
    }


    @Override
    protected boolean showToolbar() {
        return true;
    }


    @Override
    protected String getToolbarTitle() {
        return "";
    }



}
