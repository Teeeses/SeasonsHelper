package com.explead.seasonhelper.common.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.beans.Level;
import com.explead.seasonhelper.common.utils.Utils;
import com.explead.seasonhelper.summer.ui.fragments.SummerFragment;
import com.explead.seasonhelper.winter.ui.fragments.WinterFragment;

public class MainActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = getSharedPreferences(Utils.APP_PREFERENCES, Context.MODE_PRIVATE);

        int mode = getIntent().getExtras().getInt("mode");
        String directions = getIntent().getExtras().getString("directions");

        if(mode == Level.SUMMER) {
            openSummerFragment(1);
        } else if(mode == Level.WINTER) {
            openWinterFragment(1, directions);
        }
    }

    public void openSummerFragment(int level) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new SummerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void openWinterFragment(int level, String directions) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new WinterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        bundle.putString("directions", directions);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public Fragment getFragment() {
        return fragment;
    }
}
