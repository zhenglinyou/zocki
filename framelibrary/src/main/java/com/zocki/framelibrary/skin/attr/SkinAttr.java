package com.zocki.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by kaisheng3 on 2017/8/10.
 */

public class SkinAttr {

    private String mResName;

    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mSkinType = skinType;
    }

    public void skin(View mView) {
        mSkinType.sink(mView,mResName);
    }
}
