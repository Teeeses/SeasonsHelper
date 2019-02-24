package com.explead.seasonhelper.winter.ui.winter_views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.common.ui.CellView;
import com.explead.seasonhelper.winter.genrate.GeneratorField;
import com.explead.seasonhelper.winter.logic.WinterCell;

public class WinterCellView extends CellView implements WinterCell.OnChangePurposeListener {

    protected int id;
    private WinterCell cell;

    public WinterCellView(Context context) {
        super(context);
        init(context);
    }

    public WinterCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WinterCellView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void create(float size, WinterCell cell) {
        this.size = size;
        this.cell = cell;
        this.cell.setOnChangePurposeListener(this);
        this.setLayoutParams(new RelativeLayout.LayoutParams((int)(size + 2f), (int)(size + 2f)));
        calculateGlobalValue(cell.getX(), cell.getY());
        setBackground();
    }


    public WinterCell getCell() {
        return cell;
    }

    protected void setBackground() {
        if(cell.getPurpose() == WinterCell.PurposeCell.EMPTY || cell.getPurpose() == WinterCell.PurposeCell.ARROW) {
            boolean value = ((cell.getX() + cell.getY()) % 2) == 0;
            if(value) {
                setBackground(context.getResources().getColor(R.color.cell_light));
            } else {
                setBackground(context.getResources().getColor(R.color.cell_dark));
            }
        }
        if(cell.getPurpose() == WinterCell.PurposeCell.WALL) {
            setBackground(Color.TRANSPARENT);
        }
    }

    @Override
    public void onChangePurpose() {
        setBackground();
    }

    public void setBackground(int color) {
        this.setBackgroundColor(color);
    }

}
