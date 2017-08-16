package com.zocki.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by kaisheng3 on 2017/7/17.
 */
public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    public static void inject(ViewFinder viewFinder, Object object) {
        injectFiled(viewFinder,object);
        injectEvent(viewFinder,object);
    }

    private static void injectFiled(ViewFinder viewFinder, Object object) {
        Class<?> clazz = object.getClass();
        // 所有变量
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 注解类型
            ViewId viewById = field.getAnnotation(ViewId.class);
            // 注解不存在
            if( viewById == null ) continue;
            // 注解的值，ViewID
            int viewId = viewById.value();
            // 查找，获取View
            View view = viewFinder.findViewById(viewId);
            if( view == null ) continue;
            try {
                //  能够注入所有修饰符
                field.setAccessible( true );
                field.set(object,view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectEvent(ViewFinder viewFinder, Object object) {
        Class<?> clazz = object.getClass();
        // 所有变量
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if( onClick == null ) continue;
            int[] value = onClick.value();
            if( value.length == 0 ) continue;
            for (int viewId : value) {
                View view = viewFinder.findViewById(viewId);
                if( view == null ) continue;
                view.setOnClickListener( new DeclaredOnClickListener( view, object , method) );
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private View clickView;
        private Object clickObject;
        private Method clickMethod;

        public DeclaredOnClickListener( View view, Object object, Method method ) {
            this.clickView = view;
            this.clickObject = object;
            this.clickMethod = method;
        }

        @Override
        public void onClick(View v) {
            try {
                clickMethod.setAccessible(true);
                clickMethod.invoke( clickObject, clickView );
            } catch (Exception e) {
                try {
                    clickMethod.invoke( clickObject );
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
