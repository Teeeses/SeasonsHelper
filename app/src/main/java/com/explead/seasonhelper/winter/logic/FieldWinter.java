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

    public void move(Direction direction) {
        sort(direction);
        setDirectionsMoveCubes(direction);
        while(!isEndMove()) {

            for(int i = 0; i < cubes.size(); i++) {
                WinterCube cube = cubes.get(i);
                Direction cubeDirection = cube.getDirection();
                if(cubeDirection != null) {
                    if (cubeDirection == Direction.U) {
                        moveU(cube);
                    }
                    if (cubeDirection == Direction.R) {
                        moveR(cube);
                    }
                    if (cubeDirection == Direction.D) {
                        moveD(cube);
                    }
                    if (cubeDirection == Direction.L) {
                        moveL(cube);
                    }
                }
            }
        }

        checkWin();
    }

    private void setDirectionsMoveCubes(Direction direction) {
        for(WinterCube cube: cubes) {
            cube.setDirection(direction);
            cube.saveLastInfo();
        }
    }

    private boolean isEndMove() {
        for(WinterCube cube: cubes) {
            if(cube.getDirection() != null) {
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

    private boolean isCubeOnPlace(int x, int y) {
        for(WinterCube cube: cubes) {
            if(cube.getX() == x && cube.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void moveU(WinterCube cube) {
        if(cube.getX() == 0) {
            cube.move();
            cube.setDirection(null);
        } else {
            int x = cube.getX() - 1;
            int y = cube.getY();
            WinterCell cell = field[x][y];
            if(!isCubeOnPlace(cell.getX(), cell.getY())) {
                if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                    cube.saveNewInfo(x, y);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                    cube.move();
                    cube.setDirection(null);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                    cube.saveNewInfo(x, y);
                    cube.move();
                    cube.setDirection(cell.getDirection());
                }
            } else {
                cube.move();
                cube.setDirection(null);
            }
        }
    }

    private void moveR(WinterCube cube) {
        if(cube.getY() == field.length-1) {
            cube.move();
            cube.setDirection(null);
        } else {
            int x = cube.getX();
            int y = cube.getY() + 1;
            WinterCell cell = field[x][y];
            if(!isCubeOnPlace(cell.getX(), cell.getY())) {
                if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                    cube.saveNewInfo(x, y);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                    cube.move();
                    cube.setDirection(null);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                    cube.saveNewInfo(x, y);
                    cube.move();
                    cube.setDirection(cell.getDirection());
                }
            } else {
                cube.move();
                cube.setDirection(null);
            }
        }
    }

    private void moveD(WinterCube cube) {
        if(cube.getX() == field.length-1) {
            cube.move();
            cube.setDirection(null);
        } else {
            int x = cube.getX() + 1;
            int y = cube.getY();
            WinterCell cell = field[x][y];
            if(!isCubeOnPlace(cell.getX(), cell.getY())) {
                if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                    cube.saveNewInfo(x, y);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                    cube.move();
                    cube.setDirection(null);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                    cube.saveNewInfo(x, y);
                    cube.move();
                    cube.setDirection(cell.getDirection());
                }
            } else {
                cube.move();
                cube.setDirection(null);
            }
        }
    }

    private void moveL(WinterCube cube) {
        if(cube.getY() == 0) {
            cube.move();
            cube.setDirection(null);
        } else {
            int x = cube.getX();
            int y = cube.getY() - 1;
            WinterCell cell = field[x][y];
            if(!isCubeOnPlace(cell.getX(), cell.getY())) {
                if (cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                    cube.saveNewInfo(x, y);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
                    cube.move();
                    cube.setDirection(null);
                } else if (cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                    cube.saveNewInfo(x, y);
                    cube.move();
                    cube.setDirection(cell.getDirection());
                }
            } else {
                cube.move();
                cube.setDirection(null);
            }
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
