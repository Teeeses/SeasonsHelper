package com.explead.seasonhelper.summer.logic;

import com.explead.seasonhelper.common.logic.Cell;

import java.util.ArrayList;

public class Route {

    public interface OnRouteListener {
        void onRouteCompleted(Cell.ColorCube color, int id);
    }
    public enum StateRoute {
        NEW, BEGUN, COMPLETED
    }

    private OnRouteListener onRouteListener;

    private int id;
    private Cell.ColorCube color;
    private ArrayList<SummerCell> route = new ArrayList<>();
    private SummerCell oneCell;
    private SummerCell twoCell;

    private StateRoute state = StateRoute.NEW;

    public Route(SummerCell oneCell, SummerCell twoCell, Cell.ColorCube color, int id) {
        this.id = id;
        this.color = color;
        this.oneCell = oneCell;
        this.twoCell = twoCell;
    }


    public void start(SummerCell cell) {
        if(state == StateRoute.NEW) {
            route.add(cell);
            state = StateRoute.BEGUN;
        }
    }

    public void move(SummerCell cell) {
        if(state == StateRoute.BEGUN) {
            if (!cell.isRoute()) {
                if (nearWithCell(route.get(route.size() - 1), cell)) {
                    addCellInRoute(cell);
                    SummerCell endCell = getEndCellInRoute();
                    if(nearWithCell(cell, endCell)) {
                        addCellInRoute(endCell);
                        route.add(endCell);
                        state = StateRoute.COMPLETED;
                        onRouteListener.onRouteCompleted(color, id);
                    }
                }
            } else if(nearWithCell(route.get(route.size()-1), cell)) {
                SummerCell lastCell = route.get(route.size() - 1);
                deleteAnyCellFromRoute(lastCell, cell);
            }
        }
    }

    public void up() {
        if(route.size() <= 1) {
            route.clear();
            state = StateRoute.NEW;
        }
    }

    public void deleteAnyCellFromRoute(SummerCell lastCell, SummerCell currentCell) {
        SummerCell endCell = lastCell;
        while(!endCell.equals(currentCell)) {
            deleteCellFromRoute(endCell, route.get(route.size() - 2));
            endCell = route.get(route.size()-1);
        }
    }

    private void deleteCellFromRoute(SummerCell lastCell, SummerCell currentCell) {
        route.remove(lastCell);
        lastCell.deleteAllFromRoute();
        currentCell.deleteHalfFromRoute();
    }

    private void addCellInRoute(SummerCell cell) {
        SummerCell lastCell = route.get(route.size()-1);
        int defineDirection = defineDirection(lastCell, cell);
        cell.addInRoute(defineDirection, color, id,true);
        lastCell.addInRoute((defineDirection + 180) % 360, color, id,false);
        route.add(cell);
    }

    public void deleteAllRoute() {
        deleteAnyCellFromRoute(route.get(route.size()-1), route.get(0));
        route.clear();
        state = StateRoute.NEW;
    }

    //Маршрут закончен или нет
    private boolean isEndRoute(SummerCell cell) {
        return cell == getEndCellInRoute();
    }

    private SummerCell getEndCellInRoute() {
        return oneCell.equals(route.get(0)) ? twoCell : oneCell;
    }

    private int defineDirection(SummerCell lastCell, SummerCell newCell) {
        int angle = 0;

        if(lastCell.getY() < newCell.getY()) {
            //Left
            angle = 180;
        } else if(lastCell.getX() < newCell.getX()) {
            //Up
            angle = 270;
        } else if(lastCell.getX() > newCell.getX()) {
            //Down
            angle = 90;
        }

        return angle;
    }

    //Можно пойти на эту клетку или нет
    private boolean nearWithCell(SummerCell lastCell, SummerCell newCell) {
        int differenceX = Math.abs(lastCell.getX() - newCell.getX());
        int differentY = Math.abs(lastCell.getY() - newCell.getY());
        int difference = differenceX + differentY;
        return difference == 1;
    }

    public StateRoute getState() {
        return state;
    }

    public Cell.ColorCube getColor() {
        return color;
    }

    public void setOnRouteListener(OnRouteListener onRouteListener) {
        this.onRouteListener = onRouteListener;
    }
}
