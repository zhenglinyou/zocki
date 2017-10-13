package com.zocki;

import android.animation.TypeEvaluator;


/**
 * Created by kaisheng3 on 2017/9/5.
 */

public class MyTypeEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        Point point = new Point();
        point.x = startValue.x+fraction * (endValue.x - startValue.x);//fraction * 3就是时间，fraction代表时间流逝的百分比，3代表3000ms即3s
        point.y = startValue.y+fraction*(endValue.y - startValue.y);
        return point;
    }
}
