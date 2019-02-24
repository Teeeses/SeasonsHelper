package com.explead.seasonhelper.common.beans;

import com.explead.seasonhelper.common.logic.ContainerCells;

import java.util.ArrayList;

/**
 * Created by Александр on 09.07.2017.
 */

public class LevelContainer {

    private int[][] field;
    private ArrayList<ContainerCells> cells;

    public LevelContainer(int[][] field, ArrayList<ContainerCells> cells) {
        this.field = field;
        this.cells = cells;
    }

    public int[][] getField() {
        return field;
    }

    public ArrayList<ContainerCells> getCells() {
        return cells;
    }

    public ArrayList<ContainerCells> getCopyCells() {
        ArrayList<ContainerCells> copy = new ArrayList<>();
        for(int i = 0; i < cells.size(); i++) {
            ContainerCells container = new ContainerCells(cells.get(i).getOneCell().getCopyCell(),
                    cells.get(i).getTwoCell().getCopyCell(), cells.get(i).getColor());
            copy.add(container);
        }
        return copy;
    }

    public int[][] getCopyField() {
        int size = field.length;
        int[][] copy = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                copy[i][j] = field[i][j];
            }
        }
        return copy;
    }

}
