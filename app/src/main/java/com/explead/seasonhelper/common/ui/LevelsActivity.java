package com.explead.seasonhelper.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.adapters.MyPagerAdapter;
import com.explead.seasonhelper.common.app.App;
import com.explead.seasonhelper.common.utils.Utils;

import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class LevelsActivity extends BaseActivity {

    private ScrollerViewPager viewPager;
    private MyPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        sPref = getSharedPreferences(Utils.APP_PREFERENCES, Context.MODE_PRIVATE);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        App.setWidthScreen(displaymetrics.widthPixels);
        App.setHeightScreen(displaymetrics.heightPixels);

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

    }

    public void openGameActivity(int mode, String directions) {
        Intent intent = new Intent(LevelsActivity.this, MainActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("directions", directions);
        startActivity(intent);
    }
}
