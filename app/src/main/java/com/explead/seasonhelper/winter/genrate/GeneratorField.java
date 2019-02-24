package com.explead.seasonhelper.winter.genrate;

import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.Direction;
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

        if(direction == Direction.U)
            moveUp(cubes);
        if(direction == Direction.D)
            moveDown(cubes);
        if(direction == Direction.R)
            moveRight(cubes);
        if(direction == Direction.L)
            moveLeft(cubes);
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

    private void moveRight(ArrayList<WinterCube> cubes) {

        Collections.sort(cubes, WinterComparators.RIGHT);

        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while(field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && y < field.length-1) {
                y++;
                if(field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    y--;
                    break;
                } else {
                    for(int k = i-1; k >= 0; k--){
                        if(cubes.get(k).isCoordinate(x, y)) {
                            y--;
                            break loop;
                        }
                    }
                }
            }
            cube.setX(x);
            cube.setY(y);
        }
    }

    private void moveLeft(ArrayList<WinterCube> cubes) {

        Collections.sort(cubes, WinterComparators.LEFT);

        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while(field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && y > 0) {
                y--;
                if(field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    y++;
                    break;
                } else {
                    for(int k = i-1; k >= 0; k--){
                        if(cubes.get(k).isCoordinate(x, y)) {
                            y++;
                            break loop;
                        }
                    }
                }
            }
            cube.setX(x);
            cube.setY(y);
        }
    }

    private void moveUp(ArrayList<WinterCube> cubes) {

        Collections.sort(cubes, WinterComparators.UP);

        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while(field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && x > 0) {
                x--;
                if(field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    x++;
                    break;
                } else {
                    for(int k = i-1; k >= 0; k--){
                        if(cubes.get(k).isCoordinate(x, y)) {
                            x++;
                            break loop;
                        }
                    }
                }
            }
            cube.setX(x);
            cube.setY(y);
        }
    }

    private void moveDown(ArrayList<WinterCube> cubes) {

        Collections.sort(cubes, WinterComparators.DOWN);

        for(int i = 0; i < cubes.size(); i++) {
            WinterCube cube = cubes.get(i);
            int x = cube.getX();
            int y = cube.getY();
            loop:
            while(field[x][y].getPurpose() == WinterCell.PurposeCell.EMPTY && x < field.length-1) {
                x++;
                if(field[x][y].getPurpose() == WinterCell.PurposeCell.WALL) {
                    x--;
                    break;
                } else {
                    for(int k = i-1; k >= 0; k--){
                        if(cubes.get(k).isCoordinate(x, y)) {
                            x--;
                            break loop;
                        }
                    }
                }
            }
            cube.setX(x);
            cube.setY(y);
        }
    }
}
