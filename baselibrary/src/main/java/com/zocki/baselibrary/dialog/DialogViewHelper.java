package com.zocki.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by kaisheng3 on 2017/7/25.
 * dialog view 的辅助处理类
 */
public class DialogViewHelper {

    private View mRootContentView;

    private SparseArray<WeakReference<View>> mViews = new SparseArray<>();

    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        mRootContentView = LayoutInflater.from(mContext).inflate(mViewLayoutResId,null);
    }

    public DialogViewHelper(View mView) {
        mRootContentView = mView;
    }

    public void setContentView(View contentView) {
        this.mRootContentView = contentView;
    }

    public < T extends View > T  getView(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if( viewWeakReference != null ) {
            view = viewWeakReference.get();
        }
        if( view == null ) {
            view = mRootContentView.findViewById(viewId);
            if( view != null ) mViews.put(viewId,new WeakReference<>(view));
        }
        return (T) view;
    }

    public void setText(int viewId, CharSequence text) {
        TextView textview = getView(viewId);
        if( textview != null ) {
            textview.setText(text);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if( view != null ) {
            view.setOnClickListener(listener);
        }
    }

    public View getContentView() {
        return mRootContentView;
    }
}
