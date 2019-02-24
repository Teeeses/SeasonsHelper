package com.explead.seasonhelper.winter.ui.winter_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.logic.Direction;

public class WinterArrowView extends View {

    private Context context;

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

    public void changeRotation(Direction direction) {
        this.setRotation(direction.getRotation());
    }

}
