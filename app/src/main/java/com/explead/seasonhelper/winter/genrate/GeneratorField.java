package com.explead.seasonhelper.winter.genrate;

import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.winter.interfaces.OnActionMoveCubeListener;
import com.explead.seasonhelper.winter.logic.WinterCell;
import com.explead.seasonhelper.winter.logic.WinterCube;
import com.explead.seasonhelper.winter.logic.WinterComparators;
import com.explead.seasonhelper.winter.ui.winter_views.cubes.CubeView;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by develop on 21.09.2017.
 */
public class GeneratorField {

    public interface OnGenerateListener {
        void onResult(ArrayList<GenerateContainer> containers);
        void onError(String error);
    }

    private WinterCell[][] field;
    private static final short ITERATION = 200;
    private OnGenerateListener onGenerateListener;

    private ArrayList<GenerateContainer> containers;


    public GeneratorField(WinterCell[][] field, OnGenerateListener onGenerateListener) {
        this.field = field;
        this.containers = new ArrayList<>();
        this.onGenerateListener = onGenerateListener;
    }

    private ArrayList<WinterCube> converterArray(ArrayList<CubeView> cubes) {
        ArrayList<WinterCube> list = new ArrayList<>();
        for(CubeView cube: cubes) {
            WinterCube copy = new WinterCube(cube.getCell().getX(), cube.getCell().getY());
            copy.setColor(cube.getCell().getColor());
            list.add(copy);
        }
        return list;
    }

    /**
     * Поиск совпадений текущего положения кубиков с позициями в которых они уже находились
     */
    private GenerateContainer findEqually(ArrayList<WinterCube> cubes) {

        for(GenerateContainer container: containers) {
            ArrayList<Cell> position = container.getCells();

            ArrayList<Boolean> values = new ArrayList<>();
            for(Cell cell: position) {
                Cell.ColorCube colorCell = cell.getColor();
                for(WinterCube cube: cubes) {
                    Cell.ColorCube colorCube = cube.getColor();
                    if(colorCell == colorCube) {
                        if(cell.getX() == cube.getX() && cell.getY() == cube.getY()) {
                            values.add(true);
                        } else {
                            values.add(false);
                        }
                    }
                }
            }

            boolean result = true;
            for (boolean v: values) {
                if (!v) {
                    result = false;
                }
            }
            if(result)
                return container;
        }

        return null;
    }

    public void startGenerator(ArrayList<CubeView> cubes) {
        ArrayList<WinterCube> cells = converterArray(cubes);
        try {
            ArrayList<Direction> directions = new ArrayList<>();
            containers.add(new GenerateContainer(cells, directions));
            algorithm(0, cells, directions);
        } catch (StackOverflowError e) {
            onGenerateListener.onError(e.getStackTrace().getClass().getName());
        }
        try {
            Collections.sort(containers, WinterComparators.SORT_CONTAINERS);
            onGenerateListener.onResult(containers);
        } catch (IndexOutOfBoundsException e) {
            onGenerateListener.onError(e.getStackTrace().getClass().getName());
        }
    }

    /**
     * Проверям кубики находятся на том же месте, где были запомнены
     */
    private boolean equalRememberAndCubes(ArrayList<WinterCube> cubes, ArrayList<WinterCube> remember) {
        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            WinterCube rem = remember.get(i);
            if(!(cube.getX() == rem.getX() && cube.getY() == rem.getY())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Запомниаем расположение кубиков
     */
    private ArrayList<WinterCube> rememberCubes(ArrayList<WinterCube> cubes) {
        ArrayList<WinterCube> rememberCubes = new ArrayList<>();
        for(WinterCube cube: cubes) {
            rememberCubes.add(new WinterCube(cube));
        }
        return rememberCubes;
    }

    /**
     * Обход в глубину для рекурсивного алгоритма
     * @param steps - глубина рекусрии
     * @param cubes - массив кубиков
     * @param directions - массив всех предыдущих направление
     * @param direction - последнее напрвление
     */
    private void move(int steps, ArrayList<WinterCube> cubes, ArrayList<Direction> directions, Direction direction) {
        ArrayList<WinterCube> rememberCubes = rememberCubes(cubes);

        move(cubes, direction);
        Collections.sort(cubes, WinterComparators.ID);

        if(!equalRememberAndCubes(cubes, rememberCubes)) {
            directions.add(direction);
            GenerateContainer container = findEqually(cubes);
            if (container == null) {
                containers.add(new GenerateContainer(cubes, directions));
                algorithm(steps, cubes, directions);
            } else {
                container.plusAmount();
                boolean value = container.setDirections(directions);
                if(value) {
                    algorithm(steps, cubes, directions);
                }
            }

            cubes.clear();
            cubes.addAll(rememberCubes);
            directions.remove(directions.size() - 1);
        }
    }

    /**
     * Алгоритм создания самого тяжелого уровня на созданном поле
     * @param steps - глубина рекурсии
     * @param cubes - массив кубиков
     * @param directions - последнее направление
     */
    private void algorithm(int steps, ArrayList<WinterCube> cubes, ArrayList<Direction> directions) {
        if(steps < ITERATION) {
            steps++;

            move(steps, cubes , directions, Direction.U);
            move(steps, cubes , directions, Direction.D);
            move(steps, cubes , directions, Direction.R);
            move(steps, cubes , directions, Direction.L);
        }
    }

    private boolean canNextMove(ArrayList<WinterCube> cubes) {
        for(WinterCube cube: cubes) {
            if(cube.getMOVE()) {
                return false;
            }
        }
        return true;
    }

    private void setDirections(ArrayList<WinterCube> cubes, Direction direction) {
        for(WinterCube cube: cubes) {
            cube.setDirection(direction);
        }
    }

    private void setLastInfoCube(ArrayList<WinterCube> cubes) {
        for(WinterCube cube: cubes) {
            cube.setLastCoordinate();
            cube.setLastDirection();
        }
    }

    public void move(ArrayList<WinterCube> cubes, Direction direction) {

        if(canNextMove(cubes)) {
            setDirections(cubes, direction);
            while (!isEndMove(cubes)) {
                setLastInfoCube(cubes);
                for (WinterCube cube : cubes) {
                    moveCube(cube, cubes);
                }
                installDirectionArrows(cubes);
            }
        }
    }

    private WinterCell getNextFieldCell(int x, int y, Direction direction) {
        WinterCell cell = null;
        if(direction == Direction.U) {
            if(x != 0) {
                cell = field[x-1][y];
            }
        } else if(direction == Direction.R) {
            if(y != field.length-1) {
                cell = field[x][y+1];
            }
        } else if(direction == Direction.D) {
            if(x != field.length-1) {
                cell = field[x+1][y];
            }
        } else if(direction == Direction.L) {
            if(y != 0) {
                cell = field[x][y-1];
            }
        }
        return cell;
    }

    private void installDirectionArrows(ArrayList<WinterCube> cubes) {
        for (WinterCube cube: cubes) {
            WinterCell winterCell = field[cube.getX()][cube.getY()];
            if(winterCell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                Direction direction = winterCell.getDirection();
                cube.setDirection(direction);

                setDirectionNextCube(cube.getX(), cube.getY(), cube.getDirection(), cubes);
            }
        }
    }

    private void setDirectionNextCube(int x, int y, Direction direction, ArrayList<WinterCube> cubes) {
        WinterCell cell = getNextFieldCell(x, y, direction);
        //Если такая клеткая существет
        if(cell != null) {
            WinterCube nextCube = isCubeOnPlace(cell.getX(), cell.getY(), cubes);
            if(nextCube != null && nextCube.getDirection() == null) {
                nextCube.setDirection(direction);
                setDirectionNextCube(cell.getX(), cell.getY(), direction, cubes);
            }
        }
    }

    private void actionMoveCube(WinterCube cube, WinterCube cubeOnPlace, WinterCell cell,
                                OnActionMoveCubeListener onActionMoveCubeListener) {

        if (cell.getPurpose() == WinterCell.PurposeCell.WALL) {
            onActionMoveCubeListener.onNotMove();
        } else if(cubeOnPlace != null) {
            if(cubeOnPlace.getDirection() == null) {
                onActionMoveCubeListener.onNotMove();
            } else {
                onActionMoveCubeListener.onCubeOnPlace(cubeOnPlace);
            }
        } else {
            if(cell.getPurpose() == WinterCell.PurposeCell.EMPTY) {
                onActionMoveCubeListener.onGoOnCell();
            } else if(cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
                if(cube.getDirection() == cell.getDirection()) {
                    onActionMoveCubeListener.onGoOnCell();
                } else {
                    onActionMoveCubeListener.onArrow();
                }
            }
        }
    }

    private void moveCube(final WinterCube cube, final ArrayList<WinterCube> cubes) {

        WinterCell cell = getNextFieldCell(cube.getX(), cube.getY(), cube.getDirection());
        if(cell != null) {
            final int x = cell.getX();
            final int y = cell.getY();
            //null - если клетка свободна
            WinterCube cubeOnPlace = isCubeOnPlace(x, y, cubes);

            actionMoveCube(cube, cubeOnPlace, cell, new OnActionMoveCubeListener() {
                @Override
                public void onNotMove() {
                    cube.setDirection(null);
                }
                @Override
                public void onGoOnCell() {
                    cube.setCoordinate(x, y);
                    moveCube(cube, cubes);
                }

                @Override
                public void onArrow() {
                    cube.setCoordinate(x, y);
                    cube.setDirection(null);
                }

                @Override
                public void onCubeOnPlace(WinterCube cubeOnPlace) {
                    moveCube(cubeOnPlace, cubes);
                    moveCube(cube, cubes);
                }
            });
        } else {
            cube.setDirection(null);
        }
    }

    private WinterCube isCubeOnPlace(int x, int y, ArrayList<WinterCube> cubes) {
        for(WinterCube cube: cubes) {
            if(cube.getX() == x && cube.getY() == y) {
                return cube;
            }
        }
        return null;
    }

    private boolean isEndMove(ArrayList<WinterCube> cubes) {
        for(WinterCube cube: cubes) {
            if(cube.getDirection() != null) {
                return false;
            }
        }
        return true;
    }
}
