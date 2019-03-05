package com.explead.seasonhelper.winter.ui.fragments;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.explead.screenmovementfinger.WinterMovementFinger;
import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.app.App;
import com.explead.seasonhelper.common.dialogs.DialogWinterHelp;
import com.explead.seasonhelper.common.dialogs.DialogWinterWin;
import com.explead.seasonhelper.common.logic.ContainerCells;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.common.ui.fragments.GameFragment;
import com.explead.seasonhelper.winter.interfaces.OnControllerListener;
import com.explead.seasonhelper.winter.logic.FieldWinter;
import com.explead.seasonhelper.winter.ui.WinterGameBar;
import com.explead.seasonhelper.winter.ui.winter_views.FieldWinterView;

import java.util.ArrayList;

/**
 * Created by Александр on 09.07.2017.
 */

public class WinterFragment extends GameFragment implements OnControllerListener, WinterGameBar.OnMenuClickListener {

    private FieldWinterView fieldView;
    private int level = 1;

    private WinterGameBar bar;
    private FieldWinter fieldWinter;

    private SoundPool soundPool;

    private static int[][] winterMass;
    private static  ArrayList<ContainerCells> winterContainer;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_winter, container, false);

        winterMass = App.getWinterMass();
        winterContainer = App.getWinterContainer();

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPool.load(getActivity(), R.raw.sound_win, 1);
        soundPool.load(getActivity(), R.raw.sound_move_cube, 2);


        String directions = getArguments().getString("directions");
        TextView tvDirections = view.findViewById(R.id.tvDirections);
        tvDirections.setText(directions);

        level = getArguments().getInt("level");
        if(level == 1) {
            openHelpDialog();
        }

        bar = view.findViewById(R.id.gameBar);
        bar.setOnMenuClickListener(this);

        fieldView = view.findViewById(R.id.fieldView);

        startGame(winterMass, winterContainer);

        WinterMovementFinger winterMovementFinger = new WinterMovementFinger(onSideFingerMovementCallback);
        winterMovementFinger.setTouchView(view);

        return view;
    }

    public WinterMovementFinger.OnSideFingerMovementCallback onSideFingerMovementCallback =
            new WinterMovementFinger.OnSideFingerMovementCallback() {
        @Override
        public void onUp() {
            fieldWinter.move(Direction.U);
        }

        @Override
        public void onDown() {
            fieldWinter.move(Direction.D);
        }

        @Override
        public void onRight() {
            fieldWinter.move(Direction.R);
        }

        @Override
        public void onLeft() {
            fieldWinter.move(Direction.L);
        }
    };

    private void openHelpDialog() {
        DialogWinterHelp dialog = new DialogWinterHelp(getActivity());
        dialog.show();
    }

    @Override
    public void onMenu() {
        activity.onBackPressed();
    }

    @Override
    public void onRestart() {
        fieldView.clearField();
        startGame(winterMass, winterContainer);
    }

    @Override
    public void onHelp() {
        DialogWinterHelp dialog = new DialogWinterHelp(getActivity());
        dialog.show();
    }

    private DialogWinterWin.OnDialogCompletionListener onDialogCompletionListener =
            new DialogWinterWin.OnDialogCompletionListener() {
        @Override
        public void onMenu() {
            activity.onBackPressed();
        }

        @Override
        public void onNextLevel() {}
    };

    @Override
    public void onWin() {
        soundPool.play(1, 1f, 1f, 1, 0, 1f);
        activity.setCurrentWinterLevel(level);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogWinterWin dialog = new DialogWinterWin(activity, onDialogCompletionListener);
                dialog.show();
            }
        }, 500);
    }

    public void startGame(int[][] mass, ArrayList<ContainerCells> cubes) {
        bar.setNumberLevel(level);

        fieldWinter = new FieldWinter(mass, cubes);
        fieldWinter.setOnControllerListener(this);
        fieldView.setFieldWinter(fieldWinter);

        fieldView.clearField();
        fieldView.createField();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
