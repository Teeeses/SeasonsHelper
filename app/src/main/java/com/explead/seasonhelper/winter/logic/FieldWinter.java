package com.explead.seasonhelper.winter.logic;

import android.util.Log;

import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.ContainerCells;
import com.explead.seasonhelper.common.logic.Direction;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Александр on 09.07.2017.
 */

public class FieldWinter {

    public interface OnControllerListener {
        void onWin();
    }

    private OnControllerListener onControllerListener;

    private static final int EMPTY_CELL = 0, WALL_CELL = 6;

    private int level;
    private WinterCell[][] field;
    private ArrayList<WinterCube> cubes = new ArrayList<>();

    public FieldWinter(int[][] mass, ArrayList<ContainerCells> cubes) {
        createField(mass);
        addActionCellsOnField(cubes);
    }

    private void createField(int[][] mass) {
        field = new WinterCell[mass.length][mass.length];
        for(int i = 0; i < mass.length; i++) {
            for(int j = 0; j < mass.length; j++) {
                WinterCell cell = new WinterCell(i, j);
                if(mass[i][j] == WALL_CELL) {
                    cell.makeWall();
                } else if(mass[i][j] == EMPTY_CELL) {
                    cell.makeEmpty();
                } else {
                    cell.makeArrow(mass[i][j]);
                }
                field[i][j] = cell;
            }
        }
    }

    /**
     * Добавляем стартовые клетки на поле
     * Прибавляем +1 для того, что бы отличить пустые клетки от активной клетки с индексом 0
     */
    private void addActionCellsOnField(ArrayList<ContainerCells> actionCells) {
        for (int i = 0; i < actionCells.size(); i++) {
            Cell cell = actionCells.get(i).getOneCell();
            Cell insideCell = actionCells.get(i).getTwoCell();
            Cell.ColorCube color = actionCells.get(i).getColor();
            WinterCube winterCube = new WinterCube(cell.getX(), cell.getY());
            WinterInsideCube winterInsideCube = new WinterInsideCube(insideCell.getX(), insideCell.getY(), color);
            winterCube.create(color, winterInsideCube);

            cubes.add(winterCube);
        }
    }


    public void moveRight() {
        Collections.sort(cubes, WinterComparators.RIGHT);
        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while (field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && y < field.length - 1) {
                y++;
                if (field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    y--;
                    break;
                } else {
                    for (int k = i - 1; k >= 0; k--) {
                        if (cubes.get(k).isCoordinate(x, y)) {
                            y--;
                            break loop;
                        }
                    }
                }
            }
            cube.right(x, y);
        }
        checkWin();
    }

    public void moveLeft() {
        Collections.sort(cubes, WinterComparators.LEFT);
        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while (field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && y > 0) {
                y--;
                if (field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    y++;
                    break;
                } else {
                    for (int k = i - 1; k >= 0; k--) {
                        if (cubes.get(k).isCoordinate(x, y)) {
                            y++;
                            break loop;
                        }
                    }
                }
            }
            cube.left(x, y);
        }
        checkWin();
    }

    public void moveUp() {
        Collections.sort(cubes, WinterComparators.UP);
        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while (field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && x > 0) {
                x--;
                if (field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    x++;
                    break;
                } else {
                    for (int k = i - 1; k >= 0; k--) {
                        if (cubes.get(k).isCoordinate(x, y)) {
                            x++;
                            break loop;
                        }
                    }
                }
            }
            cube.up(x, y);
        }
        checkWin();
    }


    public void moveDown() {
        Collections.sort(cubes, WinterComparators.DOWN);

        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while (field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && x < field.length - 1) {
                x++;
                if (field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    x--;
                    break;
                } else {
                    for (int k = i - 1; k >= 0; k--) {
                        if (cubes.get(k).isCoordinate(x, y)) {
                            x--;
                            break loop;
                        }
                    }
                }
            }
            cube.down(x, y);
        }
        checkWin();
    }

    public void checkWin() {
        boolean value = true;
        for (int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            if(!cube.isTruePlace()) {
                value = false;
            }
        }
        if(value) {
            onControllerListener.onWin();
        }
    }

    public int getLevel() {
        return level;
    }

    public WinterCell[][] getField() {
        return field;
    }

    public ArrayList<WinterCube> getCubes() {
        return cubes;
    }

    public int getSizeField() {
        return field.length;
    }

    public void setOnControllerListener(OnControllerListener onControllerListener) {
        this.onControllerListener = onControllerListener;
    }
}
