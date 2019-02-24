package com.explead.seasonhelper.winter.logic;


import com.explead.seasonhelper.common.logic.Cell;

public class WinterCell extends Cell {

    public interface OnChangePurposeListener {
        void onChangePurpose();
    }

    public enum PurposeCell {
        EMPTY, WALL
    }

    private OnChangePurposeListener onChangePurposeListener;
    private PurposeCell purpose;

    public WinterCell(int x, int y) {
        super(x, y);
    }

    public void makeEmpty() {
        purpose = PurposeCell.EMPTY;
    }

    public void makeWall() {
        purpose = PurposeCell.WALL;
    }

    public void changePurpose(PurposeCell purpose) {
        if(this.purpose != purpose) {
            this.purpose = purpose;
            onChangePurposeListener.onChangePurpose();
        }
    }

    public int getId() {
        return purpose == PurposeCell.EMPTY ? 0 : 6;
    }

    public WinterCell getCopyCell() {
        return new WinterCell(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        WinterCell cell = (WinterCell) obj;
        return (x == cell.getX() && y == cell.getY());
    }

    public PurposeCell getPurpose() {
        return purpose;
    }

    public void setOnChangePurposeListener(OnChangePurposeListener onChangePurposeListener) {
        this.onChangePurposeListener = onChangePurposeListener;
    }
}
