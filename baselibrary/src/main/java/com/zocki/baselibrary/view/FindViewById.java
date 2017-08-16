package com.zocki.baselibrary.view;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by kaisheng3 on 2017/8/16.
 * 查找ID
 */
public class FindViewById {

    private SparseArray<WeakReference<View>> mViews = new SparseArray<>();

    private View mView;

    private Activity mActivity;

    public FindViewById(View view) {
        this.mView = view;
    }

    public FindViewById(Activity activity) {
        this.mActivity = activity;
    }

    public final <E extends View> E getView(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if( viewWeakReference != null ) {
            view = viewWeakReference.get();
        }
        if( view == null ) {
            view = mView != null ? mView.findViewById(viewId) : mActivity.findViewById(viewId);
            if( view != null ) mViews.put(viewId,new WeakReference<>(view));
        }
        return (E) view;
    }
}
