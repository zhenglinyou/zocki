package com.weightlibrary.custome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kaisheng3 on 2017/8/29.
 * 自定义view set attr
 */
public class CustomView {
    private View mView;

    public View getLayout() {
        return mView;
    }

    public CustomView(Context context, int layoutResId) {
        mView = LayoutInflater.from(context).inflate(layoutResId,null);
    }

    public void setViewListener(int id, View.OnClickListener onClickListener) {
        View view = mView.findViewById(id);
        if( view != null ) view.setOnClickListener(onClickListener);
    }

    public void setViewText(int id, String text) {
        try {
            TextView view = (TextView) mView.findViewById(id);
            if( view != null ) view.setText(text);
        } catch (Exception e) {
            Log.e("CustomTitleView","CustomTitleView # setViewText() id not find");
        }
    }

    public void setImageRes(int id, int imageResId) {
        try {
            ImageView view = (ImageView) mView.findViewById(id);
            if( view != null ) view.setImageResource(imageResId);
        } catch (Exception e) {
            Log.e("CustomTitleView","CustomTitleView # setImageRes() id not find");
        }
    }

    public void setViewBgColor(int id, int color) {
        View view = mView.findViewById(id);
        if( view != null ) view.setBackgroundColor(color);
    }

    public void setViewBgRes(int id, int resId) {
        View view = mView.findViewById(id);
        if( view != null ) view.setBackgroundResource(resId);
    }

    public void setVisibility(int id, boolean visibility) {
        View view = mView.findViewById(id);
        if( view != null ) view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
