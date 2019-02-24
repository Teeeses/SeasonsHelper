package com.explead.seasonhelper.common.ui.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.explead.seasonhelper.common.ui.MainActivity;

/**
 * Created by Александр on 09.07.2017.
 */

public class GameFragment extends Fragment {

    protected MainActivity activity;

    protected ImageView btnRestart;
    protected ImageView btnMenu;
    protected ImageView btnHelp;
    protected TextView tvNumberLevel;
    protected TextView tvLevel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }
}
