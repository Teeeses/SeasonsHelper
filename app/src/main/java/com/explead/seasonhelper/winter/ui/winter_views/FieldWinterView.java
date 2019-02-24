package com.explead.seasonhelper.winter.ui.winter_views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.common.app.App;
import com.explead.seasonhelper.winter.logic.FieldWinter;
import com.explead.seasonhelper.winter.logic.WinterArrow;
import com.explead.seasonhelper.winter.logic.WinterCell;
import com.explead.seasonhelper.winter.logic.WinterCube;
import com.explead.seasonhelper.winter.logic.WinterInsideCube;
import com.explead.seasonhelper.winter.ui.winter_views.cubes.CubeView;
import com.explead.seasonhelper.winter.ui.winter_views.cubes.InsideCubeView;

import java.util.ArrayList;

/**
 * Created by Александр on 13.07.2017.
 */

public class FieldWinterView extends RelativeLayout {

    private Context context;

    private ArrayList<WinterCube> cubes = new ArrayList<>();

    private float size;
    private FieldWinter fieldWinter;

    public FieldWinterView(Context context) {
        super(context);
        init(context);
    }

    public FieldWinterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FieldWinterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        size = App.getWidthScreen()*0.96f;
    }

    /**
     * Создаем поле игры
     */
    public void createField() {

        LayoutParams params = new LayoutParams((int) size, (int) size);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        this.setLayoutParams(params);

        float sizeCell = size / fieldWinter.getSizeField();

        WinterCell[][] field = fieldWinter.getField();
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field.length; j++) {
                WinterCellView winterCellView = new WinterCellView(context);
                winterCellView.create(sizeCell, field[i][j]);
                this.addView(winterCellView);

                if(field[i][j].getPurpose() == WinterCell.PurposeCell.ARROW) {
                    WinterArrowView winterArrowView = new WinterArrowView(context);
                    WinterArrow arrow = new WinterArrow(i, j, field[i][j].getDirection().getId());
                    winterArrowView.create(sizeCell, arrow);
                    this.addView(winterArrowView);
                }
            }
        }

        cubes = fieldWinter.getCubes();
        for (int i = 0; i < cubes.size(); i++) {
            WinterCube winterCube = cubes.get(i);
            CubeView cubeView = new CubeView(context);
            cubeView.create(sizeCell, winterCube);
            this.addView(cubeView);

            WinterInsideCube winterInsideCube = cubes.get(i).getInsideCube();
            InsideCubeView insideCubeView = new InsideCubeView(context);
            insideCubeView.create(sizeCell, winterInsideCube);
            this.addView(insideCubeView);
        }
    }

    public void clearField() {
        cubes = new ArrayList<>();
        this.removeAllViews();
    }

    public void setFieldWinter(FieldWinter fieldWinter) {
        this.fieldWinter = fieldWinter;
    }
}