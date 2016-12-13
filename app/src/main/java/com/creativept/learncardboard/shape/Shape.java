package com.creativept.learncardboard.shape;

import android.content.Context;

/**
 * 类名
 * 创建时间 2016/12/12
 * 实现的主要功能
 *
 * @author zjc
 */

public abstract class Shape {

    protected Context mContext;
    protected int mProgram;

    public Shape(Context context) {
        this.mContext = context;
    }

    protected abstract void createProgram();

    protected abstract void initData();

    public abstract void draw(float[] matrix);
}
