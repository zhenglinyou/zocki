package com.weightlibrary.title;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weightlibrary.R;

/**
 * Created by kaisheng3 on 2017/8/28.
 * 默认title
 */
public class DefTitleView {

    private View mView;
    private ImageView mLeftIcon, mRightIcon;
    private TextView mTitleText;

    public DefTitleView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.default_view_title_layout,null);
        mLeftIcon = (ImageView) mView.findViewById(R.id.image_left_icon);
        mRightIcon = (ImageView) mView.findViewById(R.id.image_right_icon);
        mTitleText = (TextView) mView.findViewById(R.id.text_title_info);
    }

    public View getLayout() {
        return mView;
    }

    public void setTitleText(String titleText) {
        mTitleText.setText(titleText);
    }

    public void setLeftIconRes(int leftIconRes) {
        mLeftIcon.setImageResource(leftIconRes);
    }

    public void setRightIconRes(int rightIconRes) {
        mRightIcon.setImageResource(rightIconRes);
    }

    public void setLeftIconVisibility(boolean leftIconVisibility) {
        mLeftIcon.setVisibility(leftIconVisibility ? View.VISIBLE : View.GONE);
    }

    public void setRightIconVisibility(boolean rightIconVisibility) {
        mRightIcon.setVisibility(rightIconVisibility ? View.VISIBLE : View.GONE);
    }

    public void setTitleVisibility(boolean titleVisibility) {
        mTitleText.setVisibility(titleVisibility ? View.VISIBLE : View.GONE);
    }

    public void setLeftOnClickListener(View.OnClickListener leftOnClickListener) {
        mLeftIcon.setOnClickListener(leftOnClickListener);
    }

    public void setRightOnClickListener(View.OnClickListener rightOnClickListener) {
        mRightIcon.setOnClickListener(rightOnClickListener);
    }
}
