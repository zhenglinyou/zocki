package com.zocki;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class Person {
    private String name;
    private int age;

    public Person() {
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
                '}';
    }
}
