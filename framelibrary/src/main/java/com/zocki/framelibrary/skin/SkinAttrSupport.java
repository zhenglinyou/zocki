package com.zocki.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.zocki.framelibrary.skin.attr.SkinAttr;
import com.zocki.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaisheng3 on 2017/8/10.
 */
public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        // background ... color

        List<SkinAttr> skinAttrs = new ArrayList<>();

        for( int i = 0, N = attrs.getAttributeCount(); i < N; i++ ) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            SkinType skinType = getSkinType( attrName );

            if( skinType != null ) {
                // attrValue 是 @123123123 类型资源  需要转化为资源名称
                String resName = getResName(context,attrValue);

                if(TextUtils.isEmpty(resName)) {
                    continue;
                }

                SkinAttr skinAttr = new SkinAttr(resName,skinType);
                skinAttrs.add( skinAttr );
            }
        }

        return skinAttrs;
    }

    /**
     * 获取资源名称
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if( attrValue.startsWith("@") ) {
            attrValue = attrValue.substring(1);
            int resId = Integer.valueOf(attrValue);
            // 通过资源id获取名称
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if( skinType.getResName().equals(attrName) ) {
                return skinType;
            }
        }
        return null;
    }
}
