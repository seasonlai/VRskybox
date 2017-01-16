package com.creativept.learncardboard.shape;

import android.content.Context;

import com.creativept.learncardboard.R;
import com.creativept.learncardboard.util.TextureUtil;

/**
 * 类名
 * 创建时间 2016/12/12 * 实现的主要功能
 *
 * @author zjc
 */

public class Texture extends BaseTexture {


    public Texture(Context context) {
        super(context);
    }

    @Override
    protected int getTextureId() {
        return TextureUtil.loadTexture(mContext, R.drawable.test);
    }
}
