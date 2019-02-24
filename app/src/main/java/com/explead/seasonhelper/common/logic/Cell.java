package com.explead.seasonhelper.common.logic;

import android.support.annotation.Nullable;

public class Cell {

    protected int x;
    protected int y;
    protected ColorCube color;

    public enum ColorCube {
        WHITE(0), RED(1), BLUE(2), YELLOW(3), GREEN(4), VIOLET(5);

        private final int id;
        ColorCube(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        this.color = cell.getColor();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Cell cell = (Cell) obj;
        return x == cell.getX() && y == cell.getY();
    }

    public Cell getCopyCell() {
        return new Cell(x, y);
    }

    public ColorCube getColor() {
        return color;
    }

    public void setColor(ColorCube color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
