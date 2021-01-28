package com.trot.trotwithtabs;

public class GenreItem {

    String name;

    public GenreItem(String name) {
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
        return "GenreItem{" +
                "name='" + name + '\'' +
                '}';
    }
}
