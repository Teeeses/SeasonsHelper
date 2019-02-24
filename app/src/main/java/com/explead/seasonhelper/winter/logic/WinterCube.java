package com.explead.seasonhelper.winter.logic;

import com.explead.seasonhelper.common.logic.Cell;

public class WinterCube extends Cell {

    public interface OnMoveListener {
        void onUp(int x, int y);
        void onDown(int x, int y);
        void onRight(int x, int y);
        void onLeft(int x, int y);
    }

    private OnMoveListener onMoveListener;
    private WinterInsideCube insideCube;

    public WinterCube(int x, int y) {
        super(x, y);
    }

    public WinterCube(WinterCube cube) {
        super(cube.getX(), cube.getY());
        this.color = cube.getColor();
    }

    public void create(ColorCube color, WinterInsideCube insideCube) {
        this.color = color;
        this.insideCube = insideCube;
    }

    public WinterInsideCube getInsideCube() {
        return insideCube;
    }

    public void up(int x, int y) {
        onMoveListener.onUp(x, y);
    }

    public void down(int x, int y) {
        onMoveListener.onDown(x, y);
    }

    public void right(int x, int y) {
        onMoveListener.onRight(x, y);
    }

    public void left(int x, int y) {
        onMoveListener.onLeft(x, y);
    }

    public boolean isTruePlace() {
        return x == insideCube.getX() && y == insideCube.getY();
    }

    public boolean isCoordinate(int x, int y) {
        return this.x == x && this.y == y;
    }

    public void setOnMoveListener(OnMoveListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }
}