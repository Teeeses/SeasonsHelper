package com.explead.seasonhelper.winter.logic;


import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.Direction;

public class WinterCell extends Cell {

    public interface OnChangePurposeListener {
        void onChangePurpose();
    }

    public enum PurposeCell {
        EMPTY, WALL, ARROW
    }

    private OnChangePurposeListener onChangePurposeListener;
    private PurposeCell purpose;


    //Если не null - значит на этой клетке стрелка
    private Direction direction = null;

    public WinterCell(int x, int y) {
        super(x, y);
    }

    public void makeEmpty() {
        purpose = PurposeCell.EMPTY;
        direction = null;
    }

    public void makeWall() {
        purpose = PurposeCell.WALL;
        direction = null;
    }

    public void makeArrow(char id) {
        purpose = PurposeCell.ARROW;
        findDirection(id);
    }

    public void changePurpose(PurposeCell purpose) {
        if(this.purpose != purpose) {
            this.purpose = purpose;
            onChangePurposeListener.onChangePurpose();
        }
    }

    public char getId() {
        return purpose == PurposeCell.EMPTY ? 'O' : 'X';
    }

    public void findDirection(char id) {
        switch (id) {
            case 'U':
                direction = Direction.U;
                break;
            case 'R':
                direction = Direction.R;
                break;
            case 'D':
                direction = Direction.D;
                break;
            case 'L':
                direction = Direction.L;
                break;
        }
    }

    public WinterCell getCopyCell() {
        return new WinterCell(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        WinterCell cell = (WinterCell) obj;
        return (x == cell.getX() && y == cell.getY());
    }

    public Direction getDirection() {
        return direction;
    }

    public PurposeCell getPurpose() {
        return purpose;
    }

    public void setOnChangePurposeListener(OnChangePurposeListener onChangePurposeListener) {
        this.onChangePurposeListener = onChangePurposeListener;
    }
}
