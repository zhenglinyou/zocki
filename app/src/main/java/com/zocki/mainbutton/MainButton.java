package com.zocki.mainbutton;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.zocki.R;
import com.zocki.baselibrary.ioc.OnClick;
import com.zocki.baselibrary.ioc.ViewUtils;

/**
 * Created by kaisheng3 on 2017/8/18.
 */

public class MainButton extends FrameLayout {
    public MainButton(@NonNull Context context) {
        this(context,null);
    }

    public MainButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MainButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_main_button_layout, this);
        ViewUtils.inject(this);
    }

    @OnClick({R.id.button1,R.id.button2,R.id.button3,R.id.button4})
    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                if( onItemClickListener != null ) onItemClickListener.itemClick(0);
                break;
            case R.id.button2:
                if( onItemClickListener != null ) onItemClickListener.itemClick(1);
                break;
            case R.id.button3:
                if( onItemClickListener != null ) onItemClickListener.itemClick(2);
                break;
            case R.id.button4:
                if( onItemClickListener != null ) onItemClickListener.itemClick(3);
                break;
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClick(int position);
    }
}
