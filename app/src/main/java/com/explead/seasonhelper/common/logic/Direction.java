package com.explead.seasonhelper.common.logic;

public enum Direction {

    U(1), R(2), D(3), L(4);

    private int id;

    Direction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getRotation() {
        return 90*id - 90;
    }
}