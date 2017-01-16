package com.creativept.learncardboard.render;

import com.creativept.learncardboard.shape.Texture;
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

/**
 * 类名
 * 创建时间 2016/12/14
 * 实现的主要功能
 *
 * @author zjc
 */

public class TextureRender implements GvrView.StereoRenderer {

    private Texture mTexture;
    private final float[] model = new float[16];
    private final float[] modelView = new float[16];
    private float[] modelViewProjection = new float[16];

    @Override
    public void onNewFrame(HeadTransform headTransform) {

    }

    @Override
    public void onDrawEye(Eye eye) {

    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {

    }

    @Override
    public void onRendererShutdown() {

    }
}
