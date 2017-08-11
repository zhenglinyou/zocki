package com.zocki.framelibrary.skin;

import android.app.Activity;
import android.content.Context;

import com.zocki.framelibrary.BaseSkinActivity;
import com.zocki.framelibrary.skin.attr.SkinView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kaisheng3 on 2017/8/10.
 */

public class SkinManager {
    private static SkinManager instance;
    private static Context mContext;
    private SkinResource mSkinResource;

    private static Map<Activity,List<SkinView>> mSkinViews = new HashMap<>();

    static {
        instance = new SkinManager();
    }



    public static SkinManager getInstance() {
        return instance;
    }

    public static void init( Context context ) {
        mContext = context.getApplicationContext();
    }

    /**
     * 加载皮肤
     * @param skinPath 皮肤路径
     * @return
     */
    public int loadSkin(String skinPath) {
        mSkinResource = new SkinResource(mContext,skinPath);

        Set<Activity> keys = mSkinViews.keySet();

        for (Activity key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }

        return 0;
    }

    public int restore() {
        return 0;
    }

    public List<SkinView> getSkinViews(Activity activity ) {
        return mSkinViews.get(activity);
    }

    public void registe(BaseSkinActivity activity, List<SkinView> skinViews) {
        mSkinViews.put(activity,skinViews);
    }

    /**
     * 获取当前皮肤资源
     * @return
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
