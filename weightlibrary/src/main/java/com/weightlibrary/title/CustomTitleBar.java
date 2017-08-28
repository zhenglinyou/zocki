package com.weightlibrary.title;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by kaisheng3 on 2017/8/28.
 * 自定义title
 */
public class CustomTitleBar {

    public static Builder Builder(Context context, int layoutResId) {
        return new Builder(context,layoutResId);
    }

    public static class Builder {
        private Params P;
        private CustomTitleView customTitleView;

        public Builder(Context context, int layoutResId) {
            customTitleView = new CustomTitleView(context,layoutResId);
            P = new Params();
        }

        public Builder setViewListener(int id, View.OnClickListener onClickListener) {
            P.setViewListener(id,onClickListener);
            return this;
        }

        public Builder setViewText(int id, String text) {
            P.setViewText(id,text);
            return this;
        }

        public Builder setImageRes(int id, int imageResId) {
            P.setImageRes(id,imageResId);
            return this;
        }

        public Builder setViewBgColor(int id, int color) {
            P.setViewBgColor(id,color);
            return this;
        }

        public Builder setViewBgRes(int id, int resId) {
            P.setViewBgRes(id,resId);
            return this;
        }

        public Builder setVisibility(int id, boolean visibility) {
            P.setVisibility(id,visibility);
            return this;
        }

        public CustomTitleView create() {
            P.apply(customTitleView);
            return customTitleView;
        }
    }

    public static class Params {

        private SparseArray<View.OnClickListener> mListeners = new SparseArray<>();

        private SparseArray<String> mTextInfos = new SparseArray<>();

        private SparseIntArray mImageRess = new SparseIntArray();

        private SparseIntArray mViewBgColor = new SparseIntArray();

        private SparseIntArray mViewBgRes = new SparseIntArray();

        private SparseBooleanArray mViewVisibility = new SparseBooleanArray();

        public void setViewListener(int id, View.OnClickListener onClickListener) {
            if( onClickListener != null && id != 0) mListeners.put(id,onClickListener);
        }

        public void setViewText(int id, String text) {
            if( id != 0 ) mTextInfos.put(id,text);
        }

        public void setImageRes(int id, int imageResId) {
            if( id != 0 && imageResId != 0 ) mImageRess.put(id,imageResId);
        }

        public void setViewBgColor(int id, int color) {
            if( id != 0 && color != 0 ) mViewBgColor.put(id,color);
        }

        public void setViewBgRes(int id, int resBg) {
            if( id != 0 && resBg != 0 ) mViewBgRes.put(id,resBg);
        }

        public void setVisibility(int id, boolean visibility) {
            if( id != 0 ) mViewVisibility.put(id,visibility);
        }

        public void apply(CustomTitleView customTitleView) {

            int N = mListeners.size();
            for( int i = 0; i < N; i++ ) {
                int key = mListeners.keyAt(i);
                customTitleView.setViewListener(key, mListeners.get(key));
            }

            N = mTextInfos.size();
            for( int i = 0; i < N; i++ ) {
                int key = mTextInfos.keyAt(i);
                customTitleView.setViewText( key, mTextInfos.get(key));
            }

            N = mImageRess.size();
            for( int i = 0; i < N; i++ ) {
                int key = mImageRess.keyAt(i);
                customTitleView.setImageRes(key, mImageRess.get(key));
            }

            N = mViewBgColor.size();
            for( int i = 0; i < N; i++ ) {
                int key = mViewBgColor.keyAt(i);
                customTitleView.setViewBgColor(key, mViewBgColor.get(key));
            }

            N = mViewBgRes.size();
            for( int i = 0; i < N; i++ ) {
                int key = mViewBgRes.keyAt(i);
                customTitleView.setViewBgRes(key, mViewBgRes.get(key));
            }

            N = mViewVisibility.size();
            for( int i = 0; i < N; i++ ) {
                int key = mViewVisibility.keyAt(i);
                customTitleView.setVisibility(key, mViewVisibility.get(key));
            }
        }


    }
}
