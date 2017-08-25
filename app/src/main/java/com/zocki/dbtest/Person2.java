package com.zocki.dbtest;

import com.zocki.db.library.annotation.Column;
import com.zocki.db.library.annotation.Extend;
import com.zocki.db.library.annotation.TableName;

/**
 * Created by kaisheng3 on 2017/8/21.
 */
@TableName( name = "Person333")
public class Person2 {

    @Column(defaultValue = "10", columnName = "ccc1")
    private int ccc;

    @Column(defaultValue = "123123")
    private String kekk;

    @Extend
    private int ddd;

    private int bbb;

    @Column(notNull = true)
    private int aaa;

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getBbb() {
        return bbb;
    }

    public void setBbb(int bbb) {
        this.bbb = bbb;
    }

    private int ttt;

    public int getTtt() {
        return ttt;
    }

    public void setTtt(int ttt) {
        this.ttt = ttt;
    }

    public int getCcc() {
        return ccc;
    }

    public void setCcc(int ccc) {
        this.ccc = ccc;
    }

    public int getAaa() {
        return aaa;
    }

    public void setAaa(int aaa) {
        this.aaa = aaa;
    }

}
