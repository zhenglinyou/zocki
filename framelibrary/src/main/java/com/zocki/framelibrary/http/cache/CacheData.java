package com.zocki.framelibrary.http.cache;

/**
 * Created by kaisheng3 on 2017/8/1.
 *
 */
public class CacheData {

    private String key;

    private String value;

    private long createTime;

    public CacheData() {
    }

    public CacheData(String key, String value, long createTime) {
        this.key = key;
        this.value = value;
        this.createTime = createTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
