package com.creativept.learncardboard.shape;

import android.content.Context;

import com.creativept.learncardboard.R;
import com.creativept.learncardboard.util.TextureUtil;

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
        return TextureUtil.loadCubeMap(mContext,
                new int[]{R.drawable.left, R.drawable.right,
                        R.drawable.bottom, R.drawable.top,
                        R.drawable.front, R.drawable.back});
    }
}
