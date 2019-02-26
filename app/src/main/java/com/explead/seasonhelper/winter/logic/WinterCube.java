package com.explead.seasonhelper.winter.logic;

import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.Direction;

public class WinterCube extends Cell {

    public interface OnMoveListener {
        void onUp(int x, int y);
        void onDown(int x, int y);
        void onRight(int x, int y);
        void onLeft(int x, int y);
    }

    private Direction direction = null;
    private int lastX, lastY;

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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void saveLastInfo() {
        lastX = x;
        lastY = y;
    }

    public void saveNewInfo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        if(direction == Direction.U) {
            onMoveListener.onUp(x, y);
        } else if(direction == Direction.R) {
            onMoveListener.onRight(x, y);
        } else if(direction == Direction.D) {
            onMoveListener.onDown(x, y);
        } else if(direction == Direction.L) {
            onMoveListener.onLeft(x, y);
        }
        saveLastInfo();
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }
}
