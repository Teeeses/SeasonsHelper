package com.explead.seasonhelper.winter.ui.winter_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.beans.GetIds;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.common.ui.CellView;
import com.explead.seasonhelper.winter.logic.WinterArrow;
import com.explead.seasonhelper.winter.logic.WinterInsideCube;

public class WinterArrowView extends CellView {

    private Context context;
    private WinterArrow cell;

    public WinterArrowView(Context context) {
        super(context);
        init(context);
    }

    public WinterArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WinterArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.arrow));
    }

    public void create(float size, WinterArrow cell) {
        this.size = size;
        this.cell = cell;
        this.setLayoutParams(new RelativeLayout.LayoutParams((int)(size + 2f), (int)(size + 2f)));
        calculateGlobalValue(cell.getX(), cell.getY());
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.arrow));
        this.setRotation(cell.getDirection().getRotation());
    }

    public WinterArrow getCell() {
        return cell;
    }
}
