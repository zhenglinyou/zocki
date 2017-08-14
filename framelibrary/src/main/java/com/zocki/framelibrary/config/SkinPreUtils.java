package com.zocki.framelibrary.config;

import android.content.Context;

/**
 * Created by kaisheng3 on 2017/8/14.
 */

public class SkinPreUtils {

    private static SkinPreUtils instance;

    public static SkinPreUtils getInstance() {
        if( instance == null ) {
            synchronized (SkinPreUtils.class) {
                if (instance == null) {
                    instance = new SkinPreUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 保存皮肤路径
     * @param skinPath 皮肤路径
     */
    public void saveSkinPath(Context context,String skinPath) {
        context.getSharedPreferences(SkinConfig.SKIN_INFO_SP, Context.MODE_PRIVATE)
                .edit()
                .putString(SkinConfig.SKIN_SAVE_PATH,skinPath)
                .apply();
    }

    /**
     * 获取皮肤路径
     * @return
     */
    public String getSkinPath(Context context) {
        return context.getSharedPreferences(SkinConfig.SKIN_INFO_SP, Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_SAVE_PATH,null);
    }

    /**
     * 皮肤不存在，删除
     * @param context
     */
    public void clearSkinInfo(Context context) {
        context.getSharedPreferences(SkinConfig.SKIN_INFO_SP, Context.MODE_PRIVATE)
                .edit()
                .putString(SkinConfig.SKIN_SAVE_PATH,"")
                .apply();
    }
}
