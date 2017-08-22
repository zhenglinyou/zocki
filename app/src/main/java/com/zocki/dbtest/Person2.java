package com.zocki.dbtest;

import com.zocki.db.library.annotation.Column;

/**
 * Created by kaisheng3 on 2017/8/21.
 */

public class Person2 {

    @Column(notNull = true)
    private int aaa;
    // CREATE TABLE IF NOT EXISTS Person2(ID INTEGER PRIMARY KEY AUTOINCREMENT, aaa integer NOT NULL, bbb integer, Pser integer default 10, ttt integer)
    @Column(defaultValue = "10")
    private int ccc;
    private int bbb;

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
