package com.zocki.framelibrary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.zocki.baselibrary.activity.BaseActivity;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.framelibrary.callback.ISkinChangeListener;
import com.zocki.framelibrary.skin.SkinAttrSupport;
import com.zocki.framelibrary.skin.SkinManager;
import com.zocki.framelibrary.skin.SkinResource;
import com.zocki.framelibrary.skin.attr.SkinAttr;
import com.zocki.framelibrary.skin.attr.SkinView;
import com.zocki.framelibrary.skin.support.SkinAppCompatViewInflater;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaisheng3 on 2017/7/20.
 * 换肤
 */
public abstract class BaseSkinActivity extends BaseActivity
        implements LayoutInflaterFactory, ISkinChangeListener {

    private SkinAppCompatViewInflater mAppCompatViewInflater;

    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);*/

        try {
            // A factory has already been set on this LayoutInflater 异常
            LayoutInflater layoutInflater = LayoutInflater.from(this);

            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.set(layoutInflater,false);

            LayoutInflaterCompat.setFactory(layoutInflater, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 是否需要更换皮肤
        if(SkinManager.getInstance().isChanageSkin()) {
            changeSkin(SkinManager.getInstance().getSkinResource());
        }

        // super.onCreate要在设置之后
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        // 1.拦截View的创建，获取View之后解析属性

        // First let the Activity's Factory try and inflate the view
        /*final View view = callActivityOnCreateView(parent, name, context, attrs);
        if (view != null) {
            return view;
        }*/

        // If the Factory didn't handle it, let our createView() method try
        View view = createView(parent, name, context, attrs);

        // 2.解析属性 src textColor background
        if( view != null ) {

            // LogUtils.e( view.getClass().getName() );

            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
            SkinView skinView = new SkinView(view,skinAttrs);

            // 3.统一交给SkinManager管理
            marginSkinView(skinView);

            // 4.预处理已经加载过的皮肤
            SkinManager.getInstance().checkChangeSkin(skinView);
        }

        return view;
    }

    /**
     * 统一管理皮肤
     * @param skinView
     */
    private void marginSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);

        if( skinViews == null ) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().registe(this, skinViews);
        }

        skinViews.add(skinView);
    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    @Override
    public void changeSkin(SkinResource skinResource) {
        LogUtils.e( "更换皮肤" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unRegiste(this);
    }
}
