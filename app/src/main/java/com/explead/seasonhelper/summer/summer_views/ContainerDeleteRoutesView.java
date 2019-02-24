package com.explead.seasonhelper.summer.summer_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.beans.GetIds;
import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.utils.Utils;

import java.util.ArrayList;

public class ContainerDeleteRoutesView extends RelativeLayout {

    public interface OnContainerDeleteRoutesListener {
        void onClickDeleteRoute(int id);
    }

    private ArrayList<ImageView> imageRoutes = new ArrayList<>();
    private Context context;
    private OnContainerDeleteRoutesListener onContainerDeleteRoutesListener;

    private LinearLayout containerDeleteRoutes;
    private TextView tvDelete;
    private int size;

    public ContainerDeleteRoutesView(Context context) {
        super(context);
        init(context);
    }

    public ContainerDeleteRoutesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContainerDeleteRoutesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.container_delete_routes, this, true);

        tvDelete = view.findViewById(R.id.tvDelete);
        tvDelete.setTypeface(Utils.getTypeFaceLevel(context.getAssets()));

        containerDeleteRoutes = view.findViewById(R.id.containerDeleteRoutes);
    }

    public void addRoute(Cell.ColorCube color, int id) {
        ImageView routeView = new ImageView(context);
        LayoutParams params = new LayoutParams(size, size);
        routeView.setLayoutParams(params);
        routeView.setBackgroundDrawable(context.getResources().getDrawable(GetIds.getIdCubeFromColor(color)));
        routeView.setAdjustViewBounds(true);
        routeView.setTag(id);
        routeView.setOnClickListener(clickRoute);

        tvDelete.setVisibility(VISIBLE);
        imageRoutes.add(routeView);
        containerDeleteRoutes.addView(routeView);
    }

    public OnClickListener clickRoute = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = (int)v.getTag();

            for(int i = 0; i < imageRoutes.size(); i++) {
                ImageView image = imageRoutes.get(i);
                if((int)image.getTag() == id) {
                    containerDeleteRoutes.removeView(image);
                    imageRoutes.remove(i);
                }
            }
            if(imageRoutes.size() == 0) {
                tvDelete.setVisibility(INVISIBLE);
            }
            onContainerDeleteRoutesListener.onClickDeleteRoute(id);
        }
    };

    public void restart() {
        tvDelete.setVisibility(INVISIBLE);
        for(int i = 0; i < imageRoutes.size(); i++) {
            ImageView image = imageRoutes.get(i);
            containerDeleteRoutes.removeView(image);
        }
        imageRoutes.clear();
    }

    public void setOnContainerDeleteRoutesListener(OnContainerDeleteRoutesListener onContainerDeleteRoutesListener) {
        this.onContainerDeleteRoutesListener = onContainerDeleteRoutesListener;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
