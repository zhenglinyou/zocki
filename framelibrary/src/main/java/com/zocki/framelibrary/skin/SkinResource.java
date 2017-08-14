package com.zocki.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Method;

/**
 * Created by kaisheng3 on 2017/8/10.
 */
public class SkinResource {

    // 整个资源通过此对象获取
    private Resources mResources;

    private String mPackageName;

    public SkinResource(Context context, String skinPath) {
        try {

            // 获取另外的apk包中的资源
            AssetManager assetManager = AssetManager.class.newInstance();

            // 加载资源文件
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, skinPath);

            mResources = new Resources( assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());

            // 获取包名
            PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            mPackageName = packageInfo.packageName;

           /* // 资源名称，资源类型，包名
            int id = resources.getIdentifier("image", "drawable", "com.resources.demo");

            if( id > 0 ) {
                Drawable drawable = resources.getDrawable(id);

                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageDrawable( drawable );
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过名字获取Drawable
     * @param resName
     * @return
     */
    public Drawable getDrawableByName( String resName ) {
        try {
            int id = mResources.getIdentifier(resName, "drawable", mPackageName);
            Drawable drawable = mResources.getDrawable(id);
            return drawable;
        } catch (Exception e ) {
            return null;
        }
    }

    /**
     * 通过名字获取Color
     * @param resName
     * @return
     */
    public ColorStateList getColorByName( String resName ) {
        try {
            int id = mResources.getIdentifier(resName, "color", mPackageName);
            ColorStateList color = mResources.getColorStateList(id);
            return color;
        } catch (Exception e ) {
            return null;
        }
    }


}
