package com.creativept.learncardboard.render;

import android.content.Context;

import com.creativept.learncardboard.shape.BaseSkyBox;
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glEnable;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * 类名
 * 创建时间 2016/12/12
 * 实现的主要功能
 *
 * @author zjc
 */

public abstract class BaseSkyBoxRender implements GvrView.StereoRenderer {

    protected Context mContext;
    private final float[] view = new float[16];
    private final float[] modelView = new float[16];
    private final float[] camera = new float[16];
    private final float[] model = new float[16];
    private float[] modelViewProjection = new float[16];
    private BaseSkyBox mSkyBox;
    private float[] perspective;

    public BaseSkyBoxRender(Context context) {
        this.mContext = context;
    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {
        setCameraLocation(camera);

        newFrame(headTransform);
    }

    @Override
    public void onDrawEye(Eye eye) {

        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        multiplyMM(view, 0, eye.getEyeView(), 0, camera, 0);
        perspective = eye.getPerspective(getZ_NEAR(), getZ_FAR());
        if (mSkyBox != null) {
            multiplyMM(modelView, 0, view, 0, model, 0);
            multiplyMM(modelViewProjection, 0, perspective, 0, modelView, 0);
            mSkyBox.draw(modelViewProjection);
        }

        drawEye(eye, perspective, view);


    }

    @Override
    public void onFinishFrame(Viewport viewport) {
        finishFrame(viewport);
    }

    @Override
    public void onSurfaceChanged(int i, int i1) {
        surfaceChanged(i, i1);
    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        surfaceCreated(eglConfig);
        initSkyBoxMatrix(model);
        mSkyBox = getSkyBox();
    }

    @Override
    public void onRendererShutdown() {
        rendererShutdown();
    }

    protected abstract float getZ_NEAR();

    protected abstract float getZ_FAR();

    protected abstract void setCameraLocation(float[] camera);

    protected abstract void initSkyBoxMatrix(float[] model);

    protected void updateSkyBoxMatrix(float[] m, int offset) {
        if (m == null || offset < 0 || offset + 16 > m.length) {
            throw new RuntimeException("BaseSkyBoxRender的updateModelLocation的参数不对！");
        }
        System.arraycopy(model, 0, m, offset, offset + 16);
    }

    protected void updateSkyBoxLocation(float x, float y, float z) {
        setIdentityM(model, 0);
        translateM(model, 0, x, y, z);
    }

    protected void updateCameraMatrix(float[] m, int offset) {
        if (m == null || offset < 0 || offset + 16 > m.length) {
            throw new RuntimeException("BaseSkyBoxRender的updateCameraMatrix的参数不对！");
        }
        System.arraycopy(camera, 0, m, offset, offset + 16);
    }

    protected float[] getCameraMatrix() {
        float[] result = new float[camera.length];
        System.arraycopy(result, 0, camera, 0, camera.length);
        return result;
    }

    protected float[] getViewMatrix() {
        return view;
    }

    protected float[] getPerspectiveMatrix() {
        return perspective;
    }

    protected abstract BaseSkyBox getSkyBox();

    protected abstract void newFrame(HeadTransform headTransform);

    public abstract void drawEye(Eye eye, float[] perspectiveMatrix, float[] viewMatrix);

    public abstract void finishFrame(Viewport viewport);

    public abstract void surfaceCreated(EGLConfig eglConfig);

    public abstract void surfaceChanged(int i, int i1);

    public abstract void rendererShutdown();
}
