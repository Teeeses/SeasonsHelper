package com.explead.seasonhelper.winter.logic;

import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.winter.interfaces.OnMoveCubeListener;

public class WinterCube extends Cell {

    private OnMoveCubeListener onMoveListener;
    private WinterInsideCube insideCube;

    private Direction direction;
    private Direction lastDirection;
    private int lastX, lastY;

    //Есть ли движение
    private boolean MOVE = false;

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

    public void move() {
        if(lastDirection == Direction.U) {
            onMoveListener.onUp(lastX, x);
        } else if(lastDirection == Direction.R) {
            onMoveListener.onRight(lastY, y);
        } else if(lastDirection == Direction.D) {
            onMoveListener.onDown(lastX, x);
        } else if(lastDirection == Direction.L) {
            onMoveListener.onLeft(lastY, y);
        }
    }

    public boolean isTruePlace() {
        return x == insideCube.getX() && y == insideCube.getY();
    }

    public boolean isCoordinate(int x, int y) {
        return this.x == x && this.y == y;
    }

    public void setOnMoveListener(OnMoveCubeListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }

    public void setLastCoordinate() {
        lastX = x;
        lastY = y;
    }

    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setLastDirection() {
        this.lastDirection = direction;
    }

    public void setMOVE(boolean MOVE) {
        this.MOVE = MOVE;
    }

    public boolean getMOVE() {
        return MOVE;
    }
}
