package com.zocki.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.zocki.framelibrary.callback.ISkinChangeListener;
import com.zocki.framelibrary.config.SkinConfig;
import com.zocki.framelibrary.config.SkinPreUtils;
import com.zocki.framelibrary.skin.attr.SkinView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by kaisheng3 on 2017/8/10.
 */

public class SkinManager {
    private static SkinManager instance;
    private static Context mContext;
    private static SkinResource mSkinResource;

    private static Map<ISkinChangeListener,List<SkinView>> mSkinViews = new HashMap<>();

    static {
        instance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return instance;
    }

    public List<SkinView> getSkinViews(ISkinChangeListener skinChangeListener) {
        return mSkinViews.get(skinChangeListener);
    }

    /**
     * 获取当前皮肤资源
     * @return
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    public static void init(Context context) {
        mContext = context.getApplicationContext();

        // 程序的每一次启动都需要来到这里
        String currentSkinPath = SkinPreUtils.getInstance().getSkinPath(mContext);
        if( !TextUtils.isEmpty(currentSkinPath) ) {

            File file = new File(currentSkinPath);

            // 皮肤可能被删
            if( !file.exists() ) {
                SkinPreUtils.getInstance().clearSkinInfo(mContext);
                return;
            }

            // 校验签名

            mSkinResource = new SkinResource(mContext,currentSkinPath);
        }
    }

    /**
     * 加载皮肤
     * @param skinPath 皮肤路径
     * @return
     */
    public int loadSkin(String skinPath) {

        // 1.判断文件是否存在
        File file = new File(skinPath);
        if( !file.exists() ) {
            return SkinConfig.SKIN_FILE_NOEXISTS;
        }

        // 2.判断是否是当前文件
        if( skinPath.equals( SkinPreUtils.getInstance().getSkinPath(mContext) ) ) {
            return SkinConfig.SKIN_SKIN_ISREPEAT;
        }

        // 判断是否是apk包
        // 获取包名
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
        String mPackageName = packageInfo.packageName;

        // 不是apk资源文件
        if( TextUtils.isEmpty(mPackageName) ) {
            return SkinConfig.SKIN_FILE_IS_NOAPK;
        }

        //签名校验

        // 3.遍历赋值
        mSkinResource = new SkinResource(mContext,skinPath);

        changeSkin();

        saveSkinPath(skinPath);

        return SkinConfig.SKIN_CHAGE_SUCCESS;
    }

    /**
     * 保存皮肤路径
     * @param skinPath
     */
    private void saveSkinPath(String skinPath) {
        SkinPreUtils.getInstance().saveSkinPath(mContext,skinPath);
    }


    /**
     * 注册皮肤接口
     * @param skinChangeListener
     * @param skinViews
     */
    public void registe(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener,skinViews);
    }

    /**
     * 反注册皮肤接口
     * @param skinChangeListener
     */
    public void unRegiste(ISkinChangeListener skinChangeListener) {
        mSkinViews.get(skinChangeListener).clear();
        mSkinViews.remove(skinChangeListener);
    }

    /**
     * 启动后View的创建，检查皮肤师傅需要更换
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView) {
        if(isChanageSkin()) skinView.skin();
    }

    public boolean isChanageSkin() {
        String currentSkinPath = SkinPreUtils.getInstance().getSkinPath(mContext);
        if( !TextUtils.isEmpty(currentSkinPath) ) {
            return true;
        }
        return false;
    }

    /**
     * 改变皮肤
     */
    private void changeSkin() {

        Set<ISkinChangeListener> keys = mSkinViews.keySet();

        for (ISkinChangeListener key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }

            key.changeSkin(mSkinResource);
        }
    }

    /**
     * 恢复默认皮肤
     */
    public int restoreDefaultSkin() {
        String currentSkinPath = SkinPreUtils.getInstance().getSkinPath(mContext);
        if( TextUtils.isEmpty(currentSkinPath) ) {
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }

        // 运行当前APK资源
        mSkinResource = new SkinResource(mContext,mContext.getPackageResourcePath());

        changeSkin();

        SkinPreUtils.getInstance().clearSkinInfo(mContext);

        return SkinConfig.SKIN_CHAGE_SUCCESS;
    }
}
