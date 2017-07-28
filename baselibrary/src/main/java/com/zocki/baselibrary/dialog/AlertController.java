package com.zocki.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class AlertController {

    private AlertDialog mAlertDialog;
    private Window mWindow;

    public void setDialogViewHelper(DialogViewHelper dialogViewHelper) {
        this.mDialogViewHelper = dialogViewHelper;
    }

    private DialogViewHelper mDialogViewHelper;

    public AlertController(AlertDialog alertDialog, Window window) {
        this.mAlertDialog = alertDialog;
        this.mWindow = window;
    }

    public void setText(int viewId, CharSequence text) {
        mDialogViewHelper.setText(viewId,text);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mDialogViewHelper.setOnClickListener(viewId,listener);
    }

    public <T extends View> T getView(int resId) {
        return mDialogViewHelper.getView(resId);
    }

    public AlertDialog getAlertDialog() {
        return mAlertDialog;
    }

    public Window getWindow() {
        return mWindow;
    }


    public static class AlertParams{

        public Context mContext;
        public int themeResId;

        public int mViewLayoutResId;

        public View mView;

        // 点击空白是否能够取消
        public boolean mCancelable = false;
        // 取消监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        // 消失监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        // 按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;

        public SparseArray<CharSequence> mTextArray = new SparseArray<>();

        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        // 动画
        public int mAnimations = 0;
        // 显示位置
        public int mGravity = Gravity.CENTER;

        // 宽高
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.themeResId = themeResId;
        }

        public AlertParams() {
        }

        /**
         * 绑定和设置参数
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            // 设置参数

            // 设置布局
            DialogViewHelper viewHelper = null;
            if( mViewLayoutResId != 0 ) {
                viewHelper = new DialogViewHelper(mContext,mViewLayoutResId);
            }

            if( mView != null ) {
                viewHelper = new DialogViewHelper(mView);
                viewHelper.setContentView(mView);
            }

            if( viewHelper == null ) {
                throw new IllegalArgumentException( "布局未设置 setContentView()" );
            }

            mAlert.setDialogViewHelper(viewHelper);

            // 设置布局
            mAlert.getAlertDialog().setContentView( viewHelper.getContentView() );

            // 点击外部是否取消
            mAlert.getAlertDialog().setCancelable( mCancelable );

            // 设置文本
            for(int i = 0, N = mTextArray.size(); i < N; i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            // 设置点击
            for(int i = 0, N = mClickArray.size(); i < N; i++) {
                viewHelper.setOnClickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }

            // 配置自定义的效果 动画

            Window window = mAlert.getWindow();

            window.setGravity(mGravity);

            // 动画
            if( mAnimations != 0 ) {
                window.setWindowAnimations(mAnimations);
            }

            // 设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }
    }
}
