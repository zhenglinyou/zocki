package com.zocki.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zocki.R;
import com.zocki.baselibrary.logger.LogUtils;

/**
 * Created by kaisheng3 on 2017/8/24.
 *
 */
public class XTextView extends View {

    private int textSize;
    private String text;
    private Paint mPaint;
    private int mTextColor = Color.BLACK;

    public XTextView(Context context) {
        this(context,null);
    }

    public XTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XTextView);
        textSize = typedArray.getDimensionPixelSize(R.styleable.XTextView_Xsize, sp2px(15));
        text = typedArray.getString(R.styleable.XTextView_Xtext);
        mTextColor = typedArray.getColor(R.styleable.XTextView_Xcolor,Color.BLACK);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(textSize);
        mPaint.setAntiAlias(true);
    }

    private int sp2px(int sp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = ( fontMetricsInt.bottom - fontMetricsInt.top ) / 2 - fontMetricsInt.bottom;
        LogUtils.e( getHeight() );
        LogUtils.e( fontMetricsInt.bottom + " " + fontMetricsInt.top );
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText( text, 0, baseLine, mPaint );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if( widthMode == MeasureSpec.AT_MOST ) {
            Rect bounds = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            widthSize = bounds.width();
        }

        if( heightMode == MeasureSpec.AT_MOST ) {
            Rect bounds = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            heightSize = bounds.height();
        }

        setMeasuredDimension(widthSize,heightSize);
    }
}
