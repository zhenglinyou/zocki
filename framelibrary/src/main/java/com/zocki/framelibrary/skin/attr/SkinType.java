package com.zocki.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zocki.framelibrary.skin.SkinManager;
import com.zocki.framelibrary.skin.SkinResource;

/**
 * Created by kaisheng3 on 2017/8/10.
 */
public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void sink(View mView, String mResName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(mResName);
            if( color != null ) {
                TextView textView = (TextView) mView;
                textView.setTextColor(color);
            }
        }
    }, BACKGROUND("background"){
        @Override
        public void sink(View mView, String mResName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(mResName);
            if( drawable != null ) {
                mView.setBackground(drawable);
            } else {
                ColorStateList color = skinResource.getColorByName(mResName);
                if( color != null ) {
                    mView.setBackgroundColor(color.getDefaultColor());
                }
            }
        }
    }, SRC("src") {
        @Override
        public void sink(View mView, String mResName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(mResName);
            if( drawable != null ) {
                ImageView imageView = (ImageView) mView;
                imageView.setImageDrawable(drawable);
            }
        }
    };

    public SkinResource getSkinResource() {
       return SkinManager.getInstance().getSkinResource();
    }

    private String mResName;
    private boolean resName;

    SkinType( String mResName ) {
        this.mResName = mResName;
    }

    public abstract void sink(View mView, String mResName);

    public String getResName() {
        return mResName;
    }
}
