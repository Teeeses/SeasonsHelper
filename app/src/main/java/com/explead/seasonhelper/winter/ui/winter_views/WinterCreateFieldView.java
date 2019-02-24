package com.explead.seasonhelper.winter.ui.winter_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.common.app.App;
import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.ContainerCells;
import com.explead.seasonhelper.winter.logic.WinterCell;
import com.explead.seasonhelper.winter.logic.WinterCube;
import com.explead.seasonhelper.winter.logic.WinterInsideCube;
import com.explead.seasonhelper.winter.ui.winter_views.cubes.CubeView;
import com.explead.seasonhelper.winter.ui.winter_views.cubes.InsideCubeView;

import java.util.ArrayList;

public class WinterCreateFieldView extends RelativeLayout {


    public interface OnFieldCreateViewListener {
        void onCellClick(WinterCellView winterCellView);
    }

    private Context context;

    private OnFieldCreateViewListener onFieldCreateViewListener;
    private WinterCell[][] field;
    private ArrayList<CubeView> cubes = new ArrayList<>();
    private ArrayList<InsideCubeView> inside_cubes = new ArrayList<>();
    private float size;
    private float sizeCell;

    public WinterCreateFieldView(Context context) {
        super(context);
        init(context);
    }

    public WinterCreateFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WinterCreateFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        size = App.getWidthScreen()*0.96f;
    }

    public void createField(int number) {

        LayoutParams params = new LayoutParams((int) size, (int) size);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        this.setLayoutParams(params);

        sizeCell = size / number;

        field = new WinterCell[number][number];
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                WinterCell cell = new WinterCell(i, j);
                cell.makeEmpty();
                WinterCellView winterCellView = new WinterCellView(context);
                winterCellView.create(sizeCell, cell);
                this.addView(winterCellView);

                winterCellView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WinterCellView winterCellView = (WinterCellView) v;
                        onFieldCreateViewListener.onCellClick(winterCellView);
                    }
                });

                field[i][j] = cell;
            }
        }
    }

    public void createCube(int x, int y, Cell.ColorCube color) {
        CubeView cubeView = getCubeViewFromArray(color);
        if(cubeView != null) {
            cubes.remove(cubeView);
            this.removeView(cubeView);
        }
        WinterCube winterCube = new WinterCube(x, y);
        winterCube.setColor(color);
        cubeView = new CubeView(context);
        cubeView.create(sizeCell, winterCube);
        cubes.add(cubeView);
        this.addView(cubeView);
    }

    public void createInsideCube(int x, int y, Cell.ColorCube color) {
        InsideCubeView insideCubeView = getInsideCubeViewFromArray(color);
        if(insideCubeView != null) {
            inside_cubes.remove(insideCubeView);
            this.removeView(insideCubeView);
        }
        WinterInsideCube winterInsideCube = new WinterInsideCube(x, y, color);
        insideCubeView = new InsideCubeView(context);
        insideCubeView.create(sizeCell, winterInsideCube);
        inside_cubes.add(insideCubeView);
        this.addView(insideCubeView);
    }

    private CubeView getCubeViewFromArray(Cell.ColorCube color) {
        for (int i = 0; i < cubes.size(); i++) {
            CubeView view = cubes.get(i);
            if(view.getCell().getColor() == color) {
                return view;
            }
        }
        return null;
    }

    private InsideCubeView getInsideCubeViewFromArray(Cell.ColorCube color) {
        for (int i = 0; i < inside_cubes.size(); i++) {
            InsideCubeView view = inside_cubes.get(i);
            if(view.getCell().getColor() == color) {
                return view;
            }
        }
        return null;
    }

    public void deleteOnThisCell(int x, int y) {
        for(int i = 0; i < cubes.size(); i++) {
            CubeView view = cubes.get(i);
            if(view.getCell().getX() == x && view.getCell().getY() == y) {
                cubes.remove(view);
                this.removeView(view);
            }
        }
        for(int k = 0; k < inside_cubes.size(); k++) {
            InsideCubeView view = inside_cubes.get(k);
            if(view.getCell().getX() == x && view.getCell().getY() == y) {
                inside_cubes.remove(view);
                this.removeView(view);
            }
        }
    }

    public int[][] getMassField() {
        int[][] mass = new int[field.length][field.length];
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field.length; j++) {
                mass[i][j] = field[i][j].getId();
            }
        }
        return mass;
    }

    public ArrayList<ContainerCells> getContainer() {
        ArrayList<ContainerCells> containers = new ArrayList<>();
        for(int i = 0; i < cubes.size(); i++) {
            Cell cell = cubes.get(i).getCell();
            Cell inside_cell = null;
            for(int j = 0; j < inside_cubes.size(); j++) {
                if(cell.getColor() == inside_cubes.get(j).getCell().getColor()) {
                    inside_cell = inside_cubes.get(j).getCell();
                }
            }
            ContainerCells container = new ContainerCells(cell, inside_cell, cell.getColor());
            containers.add(container);
        }
        return containers;
    }

    public WinterCell[][] getField() {
        return field;
    }

    public ArrayList<CubeView> getCubes() {
        return cubes;
    }

    public void removeAll() {
        this.removeAllViews();
        cubes.clear();
        inside_cubes.clear();
    }


    public void deleteAllInsideCube() {
        for(int i = 0; i < inside_cubes.size(); i++) {
            this.removeView(inside_cubes.get(i));
        }
        inside_cubes.clear();
    }

    public void addOnFieldInsideCube(ArrayList<Cell> result) {
        deleteAllInsideCube();
        for(int i = 0; i < result.size(); i++) {
            Cell cell = result.get(i);
            WinterInsideCube winterInsideCube = new WinterInsideCube(cell.getX(), cell.getY(), cell.getColor());
            InsideCubeView insideCubeView = new InsideCubeView(context);
            insideCubeView.create(sizeCell, winterInsideCube);
            inside_cubes.add(insideCubeView);
            this.addView(insideCubeView);
        }
    }

    public void setOnFieldCreateViewListener(OnFieldCreateViewListener onFieldCreateViewListener) {
        this.onFieldCreateViewListener = onFieldCreateViewListener;
    }
}
