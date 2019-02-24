package com.explead.seasonhelper.common.logic;


import java.io.Serializable;

public class ContainerCells implements Serializable {

    private Cell oneCell;
    private Cell twoCell;

    private Cell.ColorCube color;

    public ContainerCells(Cell cell, Cell twoCell, Cell.ColorCube color) {
        this.oneCell = cell;
        this.twoCell = twoCell;
        this.color = color;
    }

    public Cell getOneCell() {
        return oneCell;
    }

    public Cell getTwoCell() {
        return twoCell;
    }

    public Cell.ColorCube getColor() {
        return color;
    }
}
