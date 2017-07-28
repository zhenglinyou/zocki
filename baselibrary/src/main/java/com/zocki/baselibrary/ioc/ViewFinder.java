package com.zocki.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by kaisheng3 on 2017/7/17.
 *
 */
public class ViewFinder {

    private Activity mActivity;
    private View mView;
    private byte viewType;

    public ViewFinder(View view) {
        this.mView = view;
        viewType = 0;
    }

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
        viewType = 1;
    }

    public View findViewById(int viewId) {
        switch ( viewType ) {
            case 0:
                return mView.findViewById(viewId);
            case 1:
                return mActivity.findViewById(viewId);
        }
        return null;
    }
}
