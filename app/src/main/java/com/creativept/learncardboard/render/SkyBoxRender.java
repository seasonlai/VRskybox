package com.creativept.learncardboard.render;

import android.content.Context;
import android.opengl.Matrix;

import com.creativept.learncardboard.shape.BaseSkyBox;
import com.creativept.learncardboard.shape.SkyBox;
import com.creativept.learncardboard.shape.Texture;
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

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

public class SkyBoxRender extends BaseSkyBoxRender {


    public SkyBoxRender(Context context) {
        super(context);
    }

    @Override
    protected float getZ_NEAR() {
        return 0.1f;
    }

    @Override
    protected float getZ_FAR() {
        return 10f;
    }

    @Override
    protected void setCameraLocation(float[] camera) {
        Matrix.setLookAtM(camera, 0, 0f, 0f, 0.01f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    protected void initSkyBoxMatrix(float[] model) {
        setIdentityM(model, 0);
        translateM(model, 0, 0f, 0f, 0f);
    }

    @Override
    protected BaseSkyBox getSkyBox() {
        return new SkyBox(mContext);
    }

    @Override
    protected void newFrame(HeadTransform headTransform) {

    }

    @Override
    public void drawEye(Eye eye, float[] perspectiveMatrix, float[] viewMatrix) {
        multiplyMM(modelView, 0, viewMatrix, 0, model, 0);
        multiplyMM(modelViewProjection, 0, perspectiveMatrix, 0, modelView, 0);
        mTexture.draw(modelViewProjection);
    }

    @Override
    public void finishFrame(Viewport viewport) {
    }


    private Texture mTexture;
    private final float[] model = new float[16];
    private final float[] modelView = new float[16];
    private float[] modelViewProjection = new float[16];

    @Override
    public void surfaceCreated(EGLConfig eglConfig) {
        mTexture = new Texture(mContext);

        updateTextureLocation(0f, 0f, 0f);
    }

    public void updateTextureLocation(float x, float y, float z) {
        setIdentityM(model, 0);
        translateM(model, 0, x, y, z);
    }

    @Override
    public void surfaceChanged(int i, int i1) {

    }

    @Override
    public void rendererShutdown() {
    }
}
