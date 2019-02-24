package com.explead.seasonhelper.common.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class CellView extends View {

    protected Context context;
    protected float globalX;
    protected float globalY;

    protected float size;

    public CellView(Context context) {
        super(context);
    }

    public CellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void calculateGlobalValue(int x, int y) {
        globalX = x*size;
        globalY = y*size;
        this.setX(globalY);
        this.setY(globalX);
    }

    public float coordinateToGlobal(int coordinate) {
        return coordinate*size;
    }
}
