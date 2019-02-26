package com.explead.seasonhelper.winter.ui.winter_views.cubes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.common.beans.GetIds;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.common.ui.CellView;
import com.explead.seasonhelper.winter.logic.WinterCube;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class CubeView extends CellView implements WinterCube.OnMoveListener {

    protected WinterCube cell;

    private ArrayList<ValueAnimator> animationQueue = new ArrayList<>();

    public CubeView(Context context) {
        super(context);
        init(context);
    }

    public CubeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CubeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    public void create(float size, WinterCube cell) {
        this.size = size;
        this.cell = cell;
        cell.setOnMoveListener(this);
        this.setLayoutParams(new RelativeLayout.LayoutParams((int)(size + 2f), (int)(size + 2f)));
        calculateGlobalValue(cell.getX(), cell.getY());
        this.setBackgroundDrawable(context.getResources().getDrawable(GetIds.getIdWinterCubeFromColor(cell.getColor())));
    }

    @Override
    public void onUp(int x, int y) {
        float from = coordinateToGlobal(cell.getLastX());
        float to = coordinateToGlobal(x);
        setAnimationFromPointToPoint(from, to, "up");
    }

    @Override
    public void onDown(int x, int y) {
        float from = coordinateToGlobal(cell.getLastX());
        float to = coordinateToGlobal(x);
        setAnimationFromPointToPoint(from, to, "down");
    }

    @Override
    public void onRight(int x, int y) {
        float from = coordinateToGlobal(cell.getLastY());
        float to = coordinateToGlobal(y);
        setAnimationFromPointToPoint(from, to, "right");
    }

    @Override
    public void onLeft(int x, int y) {
        float from = coordinateToGlobal(cell.getLastY());
        float to = coordinateToGlobal(y);
        setAnimationFromPointToPoint(from, to, "left");
    }


    public void setAnimationFromPointToPoint(float from, float to, final String direction) {

        final View view = this;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if(direction.equals("up") || direction.equals("down")) {
                    view.setTranslationY(value);
                } else {
                    view.setTranslationX(value);
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationQueue.remove(0);
                if(animationQueue.size() != 0) {
                    animationQueue.get(0).start();
                }
            }
        });

        valueAnimator.setDuration(200);

        if(animationQueue.size() == 0) {
            valueAnimator.start();
        }
        animationQueue.add(valueAnimator);
    }

    public WinterCube getCell() {
        return cell;
    }
}
