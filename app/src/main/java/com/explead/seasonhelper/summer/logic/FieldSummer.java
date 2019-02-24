package com.explead.seasonhelper.summer.logic;

import com.explead.seasonhelper.common.app.App;
import com.explead.seasonhelper.common.beans.LevelContainer;
import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.ContainerCells;

import java.util.ArrayList;


public class FieldSummer implements Route.OnRouteListener {

    public interface OnGameListener {
        void onWin();
        void onRouteCompleted(Cell.ColorCube color, int id);
    }

    private OnGameListener onGameListener;
    private int level;
    private SummerCell[][] field;
    private ArrayList<Route> routes = new ArrayList<>();

    private static final int EMPTY_CELL = 0, WALL_CELL = 6;

    private boolean blockingMove = false;
    private int idCurrentRoute;

    public FieldSummer(int level) {
        this.level = level;
        /*LevelContainer container = App.getSummerLevels().get(level - 1);
        createField(container.getCopyField());
        addActionCellsOnField(container.getCopyCells());*/
    }

    private void createField(int[][] mass) {
        field = new SummerCell[mass.length][mass.length];
        for(int i = 0; i < mass.length; i++) {
            for(int j = 0; j < mass.length; j++) {
                SummerCell cell = new SummerCell(i, j);
                if(mass[i][j] == WALL_CELL) {
                    cell.makeWall();
                }
                if(mass[i][j] == EMPTY_CELL) {
                    cell.makeEmpty();
                }
                field[i][j] = cell;
            }
        }
    }

    /**
     * Добавляем стартовые клетки на поле
     */
    private void addActionCellsOnField(ArrayList<ContainerCells> actionCells) {
        for (int i = 0; i < actionCells.size(); i++) {
            Cell oneCell = actionCells.get(i).getOneCell();
            Cell twoCell = actionCells.get(i).getTwoCell();

            SummerCell summerOne = new SummerCell(oneCell.getX(), oneCell.getY());
            summerOne.makeCube(actionCells.get(i).getColor());
            field[summerOne.getX()][summerOne.getY()] = summerOne;


            SummerCell summerTwo = new SummerCell(twoCell.getX(), twoCell.getY());
            summerTwo.makeCube(actionCells.get(i).getColor());
            field[summerTwo.getX()][summerTwo.getY()] = summerTwo;

            summerOne.setId(i);
            summerTwo.setId(i);
            Route route = new Route(summerOne, summerTwo, actionCells.get(i).getColor(), i);
            route.setOnRouteListener(this);
            routes.add(route);
        }
    }

    public void down(int x, int y) {
        blockingMove = false;
        SummerCell cell = field[x][y];
        if(cell.isRoute()) {
            int id = cell.getId();
            idCurrentRoute = id;
            Route route = routes.get(id);
            route.start(cell);
        } else {
            blockingMove = true;
        }
    }

    public void move(int x, int y) {
        if(!blockingMove) {
            SummerCell cell = field[x][y];
            Route route = routes.get(idCurrentRoute);
            if(!cell.isWall() && (cell.getId() == idCurrentRoute || cell.getPurpose() == SummerCell.PurposeCell.EMPTY)) {
                route.move(cell);
            }
        }
    }

    public void up(int x, int y) {
        if(!blockingMove) {
            Route route = routes.get(idCurrentRoute);
            route.up();
        }
    }

    public void deleteRoute(int id) {
        Route route = routes.get(id);
        route.deleteAllRoute();
    }

    @Override
    public void onRouteCompleted(Cell.ColorCube color, int id) {
        onGameListener.onRouteCompleted(color, id);
        if(checkWin()) {
            onGameListener.onWin();
        }
    }

    private boolean checkWin() {
        boolean win = true;
        for(int i = 0; i < routes.size(); i++) {
            if(routes.get(i).getState() != Route.StateRoute.COMPLETED) {
                win = false;
            }
        }
        return win;
    }

    public void setOnGameListener(OnGameListener onGameListener) {
        this.onGameListener = onGameListener;
    }

    public int getLevel() {
        return level;
    }

    public SummerCell[][] getField() {
        return field;
    }

    public int getSizeField() {
        return field.length;
    }
}
