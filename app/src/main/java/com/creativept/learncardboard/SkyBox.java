package com.creativept.learncardboard;

import android.content.Context;

/**
 * 类名
 * 创建时间 2016/12/12
 * 实现的主要功能
 *
 * @author season
 */

public class SkyBox extends BaseSkyBox {


    public SkyBox(Context context) {
        super(context);
    }

    @Override
    protected int getTextureId() {
        return MyGLUtils.loadCubeMap(mContext,
                new int[]{R.drawable.left, R.drawable.right,
                        R.drawable.bottom, R.drawable.top,
                        R.drawable.front, R.drawable.back});
    }
}
