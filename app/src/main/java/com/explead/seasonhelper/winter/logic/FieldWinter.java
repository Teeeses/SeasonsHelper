package com.explead.seasonhelper.winter.logic;

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

    private void setDirections(Direction direction) {
        for(WinterCube cube: cubes) {
            cube.setDirection(direction);
        }
    }

    public void move(Direction direction) {

        setDirections(direction);
        while(!isEndMove()) {
            for(WinterCube cube: cubes) {
                if(cube.getDirection() == Direction.U) {
                    moveU(cube);
                } else if(cube.getDirection() == Direction.R) {
                    moveR(cube);
                } else if(cube.getDirection() == Direction.D) {
                    moveD(cube);
                } else if(cube.getDirection() == Direction.L) {
                    moveL(cube);
                }
            }
        }

        checkWin();
    }

    private void moveU(WinterCube cube) {
        boolean isEnd = false;
        int value = 0;
        int x = cube.getX(), y = cube.getY();
        while(!isEnd) {
            if (x == 0) {
                cube.setDirection(null);
                isEnd = true;
            } else {
                int x_ = cube.getX() - 1 - value;
                int y_ = cube.getY();
                WinterCell cell = field[x_][y_];
                if (!isCubeOnPlace(x_, y_)) {
                    if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                        value++;
                        x = x_;
                        y = y_;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                        cube.setDirection(null);
                        isEnd = true;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                        cube.setDirection(cell.getDirection());
                        value++;
                        x = x_;
                        y = y_;
                        isEnd = true;
                    }
                } else {
                    cube.setDirection(null);
                    isEnd = true;
                }
            }
        }
        if(value != 0)
            cube.up(x, y);
    }

    private void moveR(WinterCube cube) {
        boolean isEnd = false;
        int value = 0;
        int x = cube.getX(), y = cube.getY();
        while(!isEnd) {
            if (y == field.length-1) {
                cube.setDirection(null);
                isEnd = true;
            } else {
                int x_ = cube.getX();
                int y_ = cube.getY() + 1 + value;
                WinterCell cell = field[x_][y_];
                if (!isCubeOnPlace(x_, y_)) {
                    if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                        value++;
                        x = x_;
                        y = y_;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                        cube.setDirection(null);
                        isEnd = true;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                        cube.setDirection(cell.getDirection());
                        value++;
                        x = x_;
                        y = y_;
                        isEnd = true;
                    }
                } else {
                    cube.setDirection(null);
                    isEnd = true;
                }
            }
        }
        if(value != 0)
            cube.right(x, y);
    }

    private void moveD(WinterCube cube) {
        boolean isEnd = false;
        int value = 0;
        int x = cube.getX(), y = cube.getY();
        while(!isEnd) {
            if (x == field.length-1) {
                cube.setDirection(null);
                isEnd = true;
            } else {
                int x_ = cube.getX() + 1 + value;
                int y_ = cube.getY();
                WinterCell cell = field[x_][y_];
                if (!isCubeOnPlace(x_, y_)) {
                    if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                        value++;
                        x = x_;
                        y = y_;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                        cube.setDirection(null);
                        isEnd = true;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                        cube.setDirection(cell.getDirection());
                        value++;
                        x = x_;
                        y = y_;
                        isEnd = true;
                    }
                } else {
                    cube.setDirection(null);
                    isEnd = true;
                }
            }
        }
        if(value != 0)
            cube.down(x, y);

    }

    private void moveL(WinterCube cube) {
        boolean isEnd = false;
        int value = 0;
        int x = cube.getX(), y = cube.getY();
        while(!isEnd) {
            if (y == 0) {
                cube.setDirection(null);
                isEnd = true;
            } else {
                int x_ = cube.getX();
                int y_ = cube.getY() - 1 - value;
                WinterCell cell = field[x_][y_];
                if (!isCubeOnPlace(x_, y_)) {
                    if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                        value++;
                        x = x_;
                        y = y_;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                        cube.setDirection(null);
                        isEnd = true;
                    } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                        cube.setDirection(cell.getDirection());
                        value++;
                        x = x_;
                        y = y_;
                        isEnd = true;
                    }
                } else {
                    cube.setDirection(null);
                    isEnd = true;
                }
            }
        }
        if(value != 0)
            cube.left(x, y);
    }

    private boolean isCubeOnPlace(int x, int y) {
        for(WinterCube cube: cubes) {
            if(cube.getX() == x && cube.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean isEndMove() {
        for(WinterCube cube: cubes) {
            if(cube.getDirection() != null) {
                sort(cube.getDirection());
                return false;
            }
        }
        return true;
    }

    private void sort(Direction direction) {
        if(direction == Direction.U) {
            Collections.sort(cubes, WinterComparators.UP);
        }
        if(direction == Direction.R) {
            Collections.sort(cubes, WinterComparators.RIGHT);
        }
        if(direction == Direction.D) {
            Collections.sort(cubes, WinterComparators.DOWN);
        }
        if(direction == Direction.L) {
            Collections.sort(cubes, WinterComparators.LEFT);
        }
    }

    private void checkWin() {
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
