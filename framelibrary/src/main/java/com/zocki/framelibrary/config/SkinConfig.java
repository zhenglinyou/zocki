package com.zocki.framelibrary.config;

/**
 * Created by kaisheng3 on 2017/8/14.
 */

public class SkinConfig {

    // 皮肤SP名称
    public static final String SKIN_INFO_SP = "SKIN_INFO_SP";

    // 皮肤路径
    public static final String SKIN_SAVE_PATH = "SKIN_SAVE_PATH";

    // 恢复默认，什么都不做,
    public static final int SKIN_CHANGE_NOTHING = -1;
    // 改变成功
    public static final int SKIN_CHAGE_SUCCESS = 1;
    // 皮肤文件不存在
    public static final int SKIN_FILE_NOEXISTS = -2;
    // 切换皮肤重复，与上一次皮肤一样
    public static final int SKIN_SKIN_ISREPEAT = -3;
    // 不是apk文件
    public static final int SKIN_FILE_IS_NOAPK = -4;
}
