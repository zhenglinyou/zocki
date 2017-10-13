package com.zocki.fragment;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.weightlibrary.loading.StatusView;
import com.weightlibrary.title.CustomTitleBar;
import com.weightlibrary.title.CustomTitleView;
import com.zocki.R;
import com.zocki.TestActivity;
import com.zocki.TimerTaskUtil;
import com.zocki.baselibrary.fragment.BaseFragment;
import com.zocki.db.library.dao.IDBDao;
import com.zocki.db.library.factory.DBDaoFactory;
import com.zocki.dbtest.Person2;

public class Button1Fragment extends BaseFragment implements View.OnClickListener {

    private StatusView statusView;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_1;
    }

    @Override
    public void initView() {

        getView(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestActivity.startActivity(getContext(),TestActivity.class);
            }
        });

        getView(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doubleClick();
            }
        });

    }

    private void setSimulateClick(PhotoView view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 30;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);
        view.dispatchTouchEvent(downEvent);
        view.dispatchTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    private void doubleClick() {

        final TimerTaskUtil taskUtil = new TimerTaskUtil(1000,0);
        taskUtil.setTimerTaskListener(new TimerTaskUtil.ITimerTaskListener() {
            @Override
            public void callback(int step) {

            }
        });

        taskUtil.start();

        taskUtil.stop();

        final PhotoView photoView = getView(R.id.img);

        ViewGroup.LayoutParams layoutParams = photoView.getLayoutParams();

        final int x = layoutParams.width / 2 ;
        final int y = layoutParams.height / 2 ;

        setSimulateClick(photoView, x, y);

        photoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                setSimulateClick(photoView, x, y);
            }
        },100);

        /*
        final View view = getView(R.id.button5);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.5f,1f);
        valueAnimator.setInterpolator(sim);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                view.setScaleX(animatedValue);
                view.setScaleY(animatedValue);
            }
        });

        valueAnimator.setDuration(2000);
        valueAnimator.start();
        */

    }

    private Interpolator sim = new Interpolator() {
        @Override
        public float getInterpolation(float input) {

            float factor = 0.2f;
            double value = Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1;

            return (float) value;
        }
    };



    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:

                /*Person2 person2 = new Person2();
                person2.setAaa(111);
                person2.setBbb(222);

                IDBDao<Person2> dao = DBDaoFactory.getInstance().getDao(Person2.class);
                long insert = dao.insert(person2);

                Toast.makeText(getContext(),"创建表" + insert,Toast.LENGTH_SHORT).show();*/

                TestActivity.startActivity(getContext(),TestActivity.class);
                break;
        }
    }

    @Override
    public void initData() {
    }

    @Override
    protected View getTitleView() {
        CustomTitleView customTitleView = CustomTitleBar.Builder(getContext(), R.layout.default_view_title_layout).create();
        return customTitleView.getLayout();
    }

    /*
    @Override
    protected View getLoadingView() {
        statusView = DefaultStatusBar.Builder(getContext())
                .setEmptyOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e( "111111111111111111111" );
                    }
                }).create();
        statusView.showEmpty();
        return statusView.getLayout();
    }
    */
}
