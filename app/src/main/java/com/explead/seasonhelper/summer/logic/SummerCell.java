package com.explead.seasonhelper.summer.logic;

import com.explead.seasonhelper.common.logic.Cell;

public class SummerCell extends Cell {

    public interface OnCellViewListener {
        void onDeleteAllFromRoute();
        void onDeleteHalfFromRoute();
        void onSetInRoute(int angle, ColorCube color, boolean state);
    }

    public enum PurposeCell {
        EMPTY, WALL, ROUTE
    }

    //Номер маршрута
    private int id;
    private OnCellViewListener onCellListener;
    private PurposeCell permanentPurpose;
    private PurposeCell purpose;

    public SummerCell(int x, int y) {
        super(x, y);
    }

    public void makeEmpty() {
        permanentPurpose = PurposeCell.EMPTY;
        purpose = PurposeCell.EMPTY;
    }

    public void makeWall() {
        permanentPurpose = PurposeCell.WALL;
        purpose = PurposeCell.WALL;
    }

    public void makeCube(ColorCube color) {
        permanentPurpose = PurposeCell.ROUTE;
        purpose = PurposeCell.ROUTE;
        this.color = color;
    }


    public boolean equals(SummerCell cell) {
        if(cell.getX() == x && cell.getY() == y) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param angle - угол картинки
     * @param color - цвет маршрута
     * @param state - накладывается первая картинка маршрута или вторая
     */
    public void addInRoute(int angle, ColorCube color, int id, boolean state) {
        this.id = id;
        purpose = PurposeCell.ROUTE;
        onCellListener.onSetInRoute(angle, color, state);
    }

    public void deleteAllFromRoute() {
        purpose = permanentPurpose;
        if(purpose == PurposeCell.EMPTY) {
            this.id = -1;
        }
        onCellListener.onDeleteAllFromRoute();
    }

    public void deleteHalfFromRoute() {
        onCellListener.onDeleteHalfFromRoute();
    }

    public boolean isRoute() {
        return purpose == PurposeCell.ROUTE;
    }

    public boolean isWall() {
        return purpose == PurposeCell.WALL;
    }

    public PurposeCell getPurpose() {
        return purpose;
    }

    public void setPurpose(PurposeCell purpose) {
        this.purpose = purpose;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOnCellListener(OnCellViewListener onCellListener) {
        this.onCellListener = onCellListener;
    }
}
