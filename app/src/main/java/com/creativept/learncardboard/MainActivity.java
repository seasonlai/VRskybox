package com.creativept.learncardboard;

import android.os.Bundle;
import android.view.View;

import com.creativept.learncardboard.render.SkyBoxRender;
import com.google.vr.sdk.base.AndroidCompat;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;

public class MainActivity extends GvrActivity implements View.OnClickListener {


    private float[] cube_position = {0f, 0f, 0f};
    private SkyBoxRender mRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui);
        initializeGvrView();
        initEvent();
    }

    public void initializeGvrView() {
        setContentView(R.layout.common_ui);

        GvrView gvrView = (GvrView) findViewById(R.id.gvr_view);
        gvrView.setEGLConfigChooser(8, 8, 8, 8, 16, 8);

        mRender = new SkyBoxRender(this);
        gvrView.setRenderer(mRender);
        gvrView.setTransitionViewEnabled(true);

        if (gvrView.setAsyncReprojectionEnabled(true)) {
            AndroidCompat.setSustainedPerformanceMode(this, true);
        }

        setGvrView(gvrView);
    }

    private void initEvent() {
        findViewById(R.id.btn_x_add).setOnClickListener(this);
        findViewById(R.id.btn_x_cut).setOnClickListener(this);
        findViewById(R.id.btn_y_add).setOnClickListener(this);
        findViewById(R.id.btn_y_cut).setOnClickListener(this);
        findViewById(R.id.btn_z_add).setOnClickListener(this);
        findViewById(R.id.btn_z_cut).setOnClickListener(this);
    }
//
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_x_add:
                cube_position[0]++;
                break;
            case R.id.btn_x_cut:
                cube_position[0]--;
                break;
            case R.id.btn_y_add:
                cube_position[1]++;
                break;
            case R.id.btn_y_cut:
                cube_position[1]--;
                break;
            case R.id.btn_z_add:
                cube_position[2]++;
                break;
            case R.id.btn_z_cut:
                cube_position[2]--;
                break;
        }
        mRender.updateTextureLocation(cube_position[0], cube_position[1], cube_position[2]);
    }
}
