package com.trot.trotwithtabs;

public class SingerItem {

    String name;

    public SingerItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SingerItem{" +
                "name='" + name + '\'' +
                '}';
    }
}
