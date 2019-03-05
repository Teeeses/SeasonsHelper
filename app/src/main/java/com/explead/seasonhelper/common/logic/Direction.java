package com.explead.seasonhelper.common.logic;

public enum Direction {

    U(1) {
        public Direction opposite() { return D; }
    },
    R(2) {
        public Direction opposite() { return L; }
    },
    D(3) {
        public Direction opposite() { return U; }
    },
    L(4) {
        public Direction opposite() { return R; }
    };

    private int id;

    Direction(int id) {
        this.id = id;
    }

    public abstract Direction opposite();

    public int getId() {
        return id;
    }

    public int getRotation() {
        return 90*id - 90;
    }
}