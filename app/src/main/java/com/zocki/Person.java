package com.zocki;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class Person {

    private String name;
    private int age;

    private boolean flag;

    private boolean uflag = true;

    public Person() {
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isUflag() {
        return uflag;
    }

    public void setUflag(boolean uflag) {
        this.uflag = uflag;
    }


    public Person(String zhangsan, int i) {
        this.name = zhangsan;
        this.age = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", flag=" + flag +
                ", uflag=" + uflag +
                '}';
    }
}
