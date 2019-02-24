package com.explead.seasonhelper.summer.summer_views.cubes;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.beans.GetIds;
import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.summer.logic.SummerCell;


public class CubeView extends RelativeLayout implements SummerCell.OnCellViewListener {

    private float size;

    private Context context;
    private SummerCell cell;

    //Изображение маршрута
    private ImageView routeViewOne;
    private ImageView routeViewTwo;

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
        routeViewOne = new ImageView(context);
        routeViewTwo = new ImageView(context);
    }

    public void setCell(SummerCell cell) {
        this.cell = cell;
        this.cell.setOnCellListener(this);
    }

    public void create(float size) {
        this.size = size;
        this.setLayoutParams(new LayoutParams((int)(size + 2f), (int)(size + 2f)));
        calculateGlobalValue();
        setBackground();
    }

    public void calculateGlobalValue() {
        this.setX(cell.getY()*size);
        this.setY(cell.getX()*size);
    }

    public void setBackground() {
        if(cell.getPurpose() == SummerCell.PurposeCell.EMPTY) {
            //setBackgroundColor(Color.rgb(202, 202, 202));
            this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grass));
        }
        if(cell.getPurpose() == SummerCell.PurposeCell.WALL) {
            setBackgroundColor(Color.TRANSPARENT);
        }
        if(cell.getPurpose() == SummerCell.PurposeCell.ROUTE) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
            if(cell.getColor() == Cell.ColorCube.RED) {
                this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grass));
                image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red));
            }
            if(cell.getColor() == Cell.ColorCube.BLUE) {
                this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grass));
                image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.blue));
            }
            if(cell.getColor() == Cell.ColorCube.GREEN) {
                this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grass));
                image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green));
            }
            if(cell.getColor() == Cell.ColorCube.YELLOW) {
                this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grass));
                image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.yellow));
            }
            if(cell.getColor() == Cell.ColorCube.VIOLET) {
                this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grass));
                image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.violet));
            }
            this.addView(image);
        }
    }



    @Override
    public void onDeleteAllFromRoute() {
        this.removeView(routeViewOne);
    }

    @Override
    public void onDeleteHalfFromRoute() {
        this.removeView(routeViewTwo);
    }

    @Override
    public void onSetInRoute(int angle, Cell.ColorCube color, boolean state) {
        ImageView routeView = routeViewTwo;
        if(state) {
            routeView = routeViewOne;
        }
        routeView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        routeView.setBackgroundDrawable(context.getResources().getDrawable(GetIds.getIdRouteFromColor(color)));

        routeView.setRotation(angle);

        this.addView(routeView);
    }
}
