package com.weightlibrary.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weightlibrary.R;

/**
 * Created by kaisheng3 on 2017/8/25.
 */

public class StatusView {

    private View mView;

    /*
        private String loadingMsg;
        private String emptyMsg;
        private int emptyResId;
        private String errorMsg;
        private int errorResId;
        private Context context;
    */

    private View mProgressLayout;
    private View mEmptyLayout;
    private View mErrorLayout;

    private ProgressBar mProgressBar;
    private TextView mLoadingMsg;

    private TextView mEmptyMsgView;
    private ImageView mEmptyIcon;

    private TextView mErrorMsgView;
    private ImageView mErrorIcon;

    private View[] mStatusLayouts = new View[3];

    StatusView(Context context) {
        // this.context = context;
        if( context == null ) {
            throw new NullPointerException("StatusView context is null");
        }
        mView = LayoutInflater.from(context).inflate(R.layout.default_view_status_layout,null);

        mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        mLoadingMsg = (TextView) mView.findViewById(R.id.loading_msg);

        mEmptyMsgView = (TextView) mView.findViewById(R.id.empty_msg);
        mEmptyIcon = (ImageView) mView.findViewById(R.id.image_empty_icon);

        mErrorMsgView = (TextView) mView.findViewById(R.id.error_msg);
        mErrorIcon = (ImageView) mView.findViewById(R.id.image_error_icon);

        mStatusLayouts[0] = mProgressLayout = mView.findViewById(R.id.ll_progress_layout);
        mStatusLayouts[1] =mEmptyLayout = mView.findViewById(R.id.ll_empty_layout);
        mStatusLayouts[2] =mErrorLayout = mView.findViewById(R.id.ll_error_layout);

        showProgress();
    }

    public View getLayout() {
        return mView;
    }

    public void setLoadingMsg(String loadingMsg) {
        // this.loadingMsg = loadingMsg;
        mLoadingMsg.setText(loadingMsg);
    }

    public void setEmptyMsg(String emptyMsg) {
        // this.emptyMsg = emptyMsg;
        mEmptyMsgView.setText(emptyMsg);
    }

    public void setEmptyResId(int emptyResId) {
        // this.emptyResId = emptyResId;
        mEmptyIcon.setImageResource(emptyResId);
    }

    public void setErrorMsg(String errorMsg) {
        // this.errorMsg = errorMsg;
        mErrorMsgView.setText(errorMsg);
    }

    public void setErrorResId(int errorResId) {
        // this.errorResId = errorResId;
        mErrorIcon.setImageResource(errorResId);
    }

    public void setEmptyOnClickListener(View.OnClickListener emptyOnClickListener) {
        mEmptyLayout.setOnClickListener(emptyOnClickListener);
    }

    public void setErrorOnClickListener(View.OnClickListener errorOnClickListener) {
        mErrorLayout.setOnClickListener(errorOnClickListener);
    }

    public void showProgress() {
        showView(mProgressLayout);
    }

    public void showEmpty() {
        showView(mEmptyLayout);
    }

    public void showError() {
        showView(mErrorLayout);
    }

    private void showView(View view) {
        for( int i = 0; i < mStatusLayouts.length; i++ ) {
            if( mStatusLayouts[i] == view ) mStatusLayouts[i].setVisibility(View.VISIBLE);
            else mStatusLayouts[i].setVisibility(View.GONE);
        }
    }
}
