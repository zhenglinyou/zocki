package com.weightlibrary.title;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by kaisheng3 on 2017/8/28.
 * 默认标题
 */
public class DefaultTitleBar {

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    public static class Builder {

        private TitleParams P;
        private DefTitleView mTitleView;

        public Builder( Context context ) {
            P = new TitleParams();
            mTitleView = new DefTitleView(context);
        }

        public Builder setTitleText(String titleText) {
            P.setTitleText(titleText);
            return this;
        }

        public Builder setLeftIconRes(int leftIconRes) {
            P.setLeftIconRes(leftIconRes);
            return this;
        }

        public Builder setRightIconRes(int rightIconRes){
            P.setRightIconRes(rightIconRes);
            return this;
        }

        public Builder setLeftIconVisibility(boolean visibility) {
            P.setLeftIconVisibility(visibility);
            return this;
        }

        public Builder setRightIconVisibility(boolean visibility) {
            P.setRightIconVisibility(visibility);
            return this;
        }

        public Builder setTitleVisibility(boolean visibility) {
            P.setTitleVisibility(visibility);
            return this;
        }

        public Builder setLeftOnClickListener(View.OnClickListener listener) {
            P.setLeftOnClickListener(listener);
            return this;
        }

        public Builder setRightOnClickListener(View.OnClickListener listener) {
            P.setRightOnClickListener(listener);
            return this;
        }

        public DefTitleView create() {
            P.apply( mTitleView );
            return mTitleView;
        }
    }

    public static class TitleParams {

        private String titleText;

        private int leftIconRes;

        private int rightIconRes;
        private View.OnClickListener leftOnClickListener;
        private View.OnClickListener rightOnClickListener;
        private boolean leftIconVisibility = true;
        private boolean rightIconVisibility = true;
        private boolean titleVisibility = true;

        public void setTitleText(String titleText) {
            this.titleText = titleText;
        }

        public void setLeftIconRes(int leftIconRes) {
            this.leftIconRes = leftIconRes;
        }

        public void setRightIconRes(int rightIconRes) {
            this.rightIconRes = rightIconRes;
        }

        public void setLeftOnClickListener(View.OnClickListener leftOnClickListener) {
            this.leftOnClickListener = leftOnClickListener;
        }

        public void setRightOnClickListener(View.OnClickListener rightOnClickListener) {
            this.rightOnClickListener = rightOnClickListener;
        }

        public void setLeftIconVisibility(boolean leftIconVisibility) {
            this.leftIconVisibility = leftIconVisibility;
        }

        public void setRightIconVisibility(boolean rightIconVisibility) {
            this.rightIconVisibility = rightIconVisibility;
        }

        public void setTitleVisibility(boolean titleVisibility) {
            this.titleVisibility = titleVisibility;
        }

        public void apply(DefTitleView titleView) {

            if( !TextUtils.isEmpty(titleText) ) titleView.setTitleText(titleText);

            if( leftIconRes != 0 ) titleView.setLeftIconRes(leftIconRes);

            if( rightIconRes != 0 ) titleView.setRightIconRes(rightIconRes);

            if( leftOnClickListener != null ) titleView.setLeftOnClickListener(leftOnClickListener);

            if( rightOnClickListener != null ) titleView.setRightOnClickListener(rightOnClickListener);

            titleView.setLeftIconVisibility(leftIconVisibility);

            titleView.setRightIconVisibility(rightIconVisibility);

            titleView.setTitleVisibility(titleVisibility);
        }
    }
}
