package com.explead.seasonhelper.winter.ui.winter_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.winter.logic.WinterCell;

public class BaseView extends View {

    protected Context context;
    protected int id;
    protected int background;

    protected WinterCell cell;

    protected float globalX;
    protected float globalY;

    protected float size;

    public BaseView(Context context) {
        super(context);
        init(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    protected void init(Context context) {
        this.context = context;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(float size, WinterCell cell) {
        this.size = size;
        this.cell = cell.getCopyCell();
        this.setLayoutParams(new RelativeLayout.LayoutParams((int)(size + 2f), (int)(size + 2f)));
        calculateGlobalValue();
    }

    public void calculateGlobalValue() {
        globalX = cell.getX()*size;
        globalY = cell.getY()*size;
        this.setX(globalY);
        this.setY(globalX);
    }

    public WinterCell getCell() {
        return cell;
    }

    protected void setBackground() {
        setBackgroundDrawable(context.getResources().getDrawable(background));
    }

    public void setBackground(int color) {
        this.setBackgroundColor(color);
    }

    public float coordToGlobal(int coordinate) {
        return coordinate*size;
    }
}
