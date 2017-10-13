package com.zocki;

import android.os.Handler;
import android.os.Looper;

public class TimerTaskUtil {
    private int mIntervalTime, mStartTime;
    private Handler mHandler;
    private Runnable mRunnable;
    private ITimerTaskListener timerTaskListener;
    private int step;
    private boolean isRunning = false;

    /**
     * @param IntervalTime 间隔时间
     * @param StartTime 多少时间后开始
     */
    public TimerTaskUtil( int IntervalTime, int StartTime ) {
        this.mIntervalTime = IntervalTime;
        this.mStartTime = StartTime;
        this.step = 0;
        this.isRunning = true;
        mHandler = new Handler( Looper.getMainLooper() );
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if( isRunning ) {
                    if (timerTaskListener != null)
                        timerTaskListener.callback(step++);
                    mHandler.postDelayed(this, mIntervalTime);
                } else {
                    mHandler.removeCallbacks(this);
                }
            }
        };
        //mHandler.postDelayed(mRunnable,mIntervalTime);
    }

    public void stop() {
        isRunning = false;
        mHandler.removeCallbacks(mRunnable);
    }

    public void start() {
        step = 0 ;
        isRunning = true;
        mHandler.postDelayed(mRunnable,mIntervalTime);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setTimerTaskListener( ITimerTaskListener l ) {
        this.timerTaskListener = l;
    }

    public interface ITimerTaskListener {
        /**
         * @param step 次数
         */
        void callback(int step);
    }
}
