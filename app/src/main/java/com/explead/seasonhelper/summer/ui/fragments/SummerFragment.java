package com.explead.seasonhelper.summer.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.app.App;
import com.explead.seasonhelper.common.dialogs.DialogLevelCompletion;
import com.explead.seasonhelper.common.dialogs.DialogSummerHelp;
import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.ui.fragments.GameFragment;
import com.explead.seasonhelper.common.utils.Utils;
import com.explead.seasonhelper.summer.summer_views.ContainerDeleteRoutesView;
import com.explead.seasonhelper.summer.summer_views.FieldSummerView;

/**
 * Created by Александр on 09.07.2017.
 */

public class SummerFragment extends GameFragment implements FieldSummerView.OnGameViewListener, ContainerDeleteRoutesView.OnContainerDeleteRoutesListener {

    private int level;

    private FieldSummerView fieldView;

    private ContainerDeleteRoutesView containerDeleteRoutesView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summer, container, false);

        level = getArguments().getInt("level");

        tvNumberLevel = view.findViewById(R.id.tvNumberLevel);
        tvLevel = view.findViewById(R.id.tvLevel);
        tvNumberLevel.setTypeface(Utils.getTypeFaceLevel(getContext().getAssets()));
        tvLevel.setTypeface(Utils.getTypeFaceLevel(getContext().getAssets()));

        fieldView = view.findViewById(R.id.fieldView);

        btnRestart = view.findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(btnRestartClick);

        btnHelp = view.findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(btnHelpClick);

        btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(btnMenuClick);

        containerDeleteRoutesView = view.findViewById(R.id.containerDeleteRoutesView);
        containerDeleteRoutesView.setOnContainerDeleteRoutesListener(this);

        startGame(level);

        return view;
    }

    View.OnClickListener btnMenuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };

    View.OnClickListener btnRestartClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startGame(level);
            containerDeleteRoutesView.restart();
        }
    };

    View.OnClickListener btnHelpClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogSummerHelp dialog = new DialogSummerHelp(getActivity());
            dialog.show();
        }
    };

    private void startGame(int level) {
        tvNumberLevel.setText(String.format("%s", level));
        fieldView.setOnGameViewListener(this);
        fieldView.setController(level);
        fieldView.clearField();
        fieldView.createField(App.getWidthScreen()*0.96f);
    }

    @Override
    public void onCreateField(int sizeCell) {
        containerDeleteRoutesView.setSize((int)(sizeCell*0.8));
    }

    @Override
    public void onClickDeleteRoute(int id) {
        fieldView.deleteRoute(id);
    }

    @Override
    public void onWin() {
        activity.setCurrentSummerLevel(level);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogLevelCompletion dialog = new DialogLevelCompletion(activity, onDialogCompletionListener);
                dialog.show();
            }
        }, 500);
    }

    @Override
    public void onRouteCompleted(Cell.ColorCube color, int id) {
        containerDeleteRoutesView.addRoute(color, id);
    }

    private DialogLevelCompletion.OnDialogCompletionListener onDialogCompletionListener =
        new DialogLevelCompletion.OnDialogCompletionListener() {
            @Override
            public void onMenu() {
                activity.onBackPressed();
            }

            @Override
            public void onNextLevel() {
                level++;
                startGame(level);
            }
        };

}
