package com.weightlibrary.loading;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by kaisheng3 on 2017/8/25.
 *
 */
public class DefaultStatusBar {

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    public static class Builder {

        private StatusParams P;
        private StatusView mStatusView;
        private Context mContext;

        public Builder( Context context ) {
            this.mContext = context;
            P = new StatusParams();
            mStatusView = new StatusView(context);
        }

        public Builder setLoadingMsg( String loadingMsg ) {
            P.setLoadingMsg( loadingMsg );
            return this;
        }

        public Builder setEmptyMsg( String emptyMsg ) {
            P.setEmptyMsg(emptyMsg);
            return this;
        }

        public Builder setEmptyResId( int emptyResId ) {
            P.setEmptyResId( emptyResId );
            return this;
        }

        public Builder setErrorMsg( String errorMsg ) {
            P.setErrorMsg( errorMsg );
            return this;
        }

        public Builder setErrorResId( int errorResId ) {
            P.setErrorResId( errorResId );
            return this;
        }

        public Builder setEmptyOnClickListener(View.OnClickListener emptyOnClickListener) {
            P.setEmptyOnClickListener(emptyOnClickListener);
            return this;
        }

        public Builder setErrorOnClickListener(View.OnClickListener errorOnClickListener) {
            P.setErrorOnClickListener(errorOnClickListener);
            return this;
        }

        public StatusView create() {
            P.apply( mStatusView );
            return mStatusView;
        }
    }

    public static class StatusParams {
        private String loadingMsg;

        private String emptyMsg;
        private int emptyResId;

        private String errorMsg;
        private int errorResId;

        private View.OnClickListener mEmptyOnClickListener;

        private View.OnClickListener mErrorOnClickListener;

        public void setLoadingMsg(String loadingMsg) {
            this.loadingMsg = loadingMsg;
        }

        public void setEmptyMsg(String emptyMsg) {
            this.emptyMsg = emptyMsg;
        }

        public void setEmptyResId(int emptyResId) {
            this.emptyResId = emptyResId;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public void setErrorResId(int errorResId) {
            this.errorResId = errorResId;
        }

        public void setEmptyOnClickListener(View.OnClickListener mEmptyOnClickListener) {
            this.mEmptyOnClickListener = mEmptyOnClickListener;
        }

        public void setErrorOnClickListener(View.OnClickListener mErrorOnClickListener) {
            this.mErrorOnClickListener = mErrorOnClickListener;
        }

        public void apply(StatusView statusView) {

            // 组装
            if( !TextUtils.isEmpty(loadingMsg) ) statusView.setLoadingMsg(loadingMsg);

            if( !TextUtils.isEmpty(emptyMsg) ) statusView.setEmptyMsg(emptyMsg);

            if( emptyResId != 0 ) statusView.setEmptyResId( emptyResId );

            if( !TextUtils.isEmpty(errorMsg) ) statusView.setErrorMsg( errorMsg );

            if( mEmptyOnClickListener != null ) statusView.setEmptyOnClickListener( mEmptyOnClickListener );

            if( mErrorOnClickListener != null ) statusView.setErrorOnClickListener( mErrorOnClickListener );

            if( errorResId != 0 ) statusView.setErrorResId( errorResId );
        }
    }
}
