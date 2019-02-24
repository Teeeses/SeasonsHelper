package com.explead.seasonhelper.summer.summer_views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.explead.screenmovementfinger.SummerMovementFinger;
import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.summer.logic.FieldSummer;
import com.explead.seasonhelper.summer.logic.SummerCell;
import com.explead.seasonhelper.summer.summer_views.cubes.CubeView;

public class FieldSummerView extends RelativeLayout implements SummerMovementFinger.OnFingerMovementCallback, FieldSummer.OnGameListener {

    private Context context;

    public interface OnGameViewListener {
        void onWin();
        void onCreateField(int sizeCell);
        void onRouteCompleted(Cell.ColorCube color, int id);
    }

    private OnGameViewListener onGameViewListener;

    private CubeView[][] fieldView;

    private float size;
    private FieldSummer fieldSummer;

    public FieldSummerView(Context context) {
        super(context);
        init(context);
    }

    public FieldSummerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FieldSummerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }


    public void clearField() {
        this.removeAllViews();
    }

    /**
     * Создаем поле игры
     * @param size  - размер поля
     */
    public void createField(float size) {
        this.size = size;

        LayoutParams params = new LayoutParams((int) size, (int) size);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        this.setLayoutParams(params);


        int sizeField = fieldSummer.getSizeField();
        float sizeCell = size / sizeField;
        SummerCell[][] summerCells = fieldSummer.getField();

        fieldView = new CubeView[sizeField][sizeField];
        for(int i = 0; i < summerCells.length; i++) {
            for(int j = 0; j < summerCells.length; j++) {
                CubeView view = new CubeView(context);
                view.setCell(summerCells[i][j]);
                view.create(sizeCell);
                fieldView[i][j] = view;
                this.addView(view);
            }
        }

        SummerMovementFinger summerMovementFinger = new SummerMovementFinger(
                this, size, fieldSummer.getSizeField(), this);

        summerMovementFinger.setTouchView();

        onGameViewListener.onCreateField((int)sizeCell);
    }

    @Override
    public void onDown(int x, int y) {
        fieldSummer.down(x, y);
    }

    @Override
    public void onMove(int x, int y) {
        fieldSummer.move(x, y);
    }

    @Override
    public void onUp(int x, int y) {
        fieldSummer.up(x, y);
    }

    public FieldSummer getController() {
        return fieldSummer;
    }

    public void setController(int level) {
        fieldSummer = new FieldSummer(level);
        fieldSummer.setOnGameListener(this);
    }

    @Override
    public void onWin() {
        onGameViewListener.onWin();
    }

    @Override
    public void onRouteCompleted(Cell.ColorCube color, int id) {
        onGameViewListener.onRouteCompleted(color, id);
    }

    public void deleteRoute(int id) {
        fieldSummer.deleteRoute(id);
    }

    public void setOnGameViewListener(OnGameViewListener onGameViewListener) {
        this.onGameViewListener = onGameViewListener;
    }
}
