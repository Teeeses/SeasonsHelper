package com.explead.seasonhelper.winter.ui.winter_views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.app.App;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by develop on 15.09.2017.
 */

public class SnowfallView  extends RelativeLayout {

    private Context context;
    private RelativeLayout root;

    private LayoutInflater inflate;
    public static final int MESSAGE_CREATE_SNOWFLAKE = 0x001;
    public static final int MESSAGE_CREATE_CLOUD = 0x002;

    private int[] SNOWFLAKE = {R.drawable.snowflake1, R.drawable.snowflake2, R.drawable.snowflake5};
    private int[] CLOUDS = {R.drawable.cloud2};
    private ArrayList<View> snowflakeViews = new ArrayList<>();

    private ArrayList<View> cloudViews = new ArrayList<>();

    private Timer timerSnowflake;
    private Timer timerCloud;

    private float[] cloudY;
    private int idCurrentY = 0;

    private int minPeriodicitySnowflake = 150;
    private int maxPeriodicitySnowflake = 500;
    private int periodicityCloud = 10000;

    public SnowfallView(Context context) {
        super(context);
        init(context);
    }

    public SnowfallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SnowfallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        root = this;
        inflate = LayoutInflater.from(context);

        float heightScreen = App.getHeightScreen();
        cloudY = new float[]{heightScreen/4f, heightScreen/2.6f, heightScreen/5.6f, heightScreen/1.8f, heightScreen/3.1f, heightScreen/7f, heightScreen/1.3f, heightScreen/5f};
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MESSAGE_CREATE_SNOWFLAKE) {
                createSnowflake();
            } else if(msg.what == MESSAGE_CREATE_CLOUD) {
                createCloud();
            }
        }
    };

    public void startAnimationSnowflake(final ImageView view) {

        view.setX(new Random().nextInt((int)App.getWidthScreen()));
        final float offsetX;
        final float rotationOffset;
        if(randInt(0, 1) == 1) {
            offsetX = -(0.1f*randInt(1, 10));
            rotationOffset = -(0.1f*randInt(5, 20));
        } else {
            offsetX = 0.1f*randInt(1, 15);
            rotationOffset = 0.1f*randInt(5, 20);
        }


        final ValueAnimator animator = ValueAnimator.ofFloat(-100, 1.5f*App.getHeightScreen());
        animator.setDuration(randInt(8000, 14000));
        animator.setInterpolator(new AccelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setTranslationX(view.getTranslationX() + offsetX);
                view.setTranslationY(value);
                view.setRotation(view.getRotation()+rotationOffset);
            }
        });

        animator.start();
    }

    public void startAnimationCloud(final ImageView view) {
        final ValueAnimator animator = ValueAnimator.ofFloat(-0.5f*App.getWidthScreen(), 1.5f*App.getWidthScreen());
        animator.setDuration(randInt(35000, 60000));
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                view.setTranslationX(value);
            }
        });

        animator.start();
    }

    private void createSnowflake() {
        removeSnowflakes();
        int viewId = new Random().nextInt(SNOWFLAKE.length);
        Drawable d = getResources().getDrawable(SNOWFLAKE[viewId]);

        ImageView imageView = (ImageView) inflate.inflate(R.layout.snowflake, null, false);
        imageView.setImageDrawable(d);
        int size = randInt(20, 40);
        imageView.setLayoutParams(new LayoutParams(size, size));
        imageView.setAlpha(0.1f*randInt(5, 10));
        snowflakeViews.add(imageView);
        root.addView(imageView);

        startAnimationSnowflake(imageView);
    }

    private void createCloud() {
        removeClouds();
        int viewId = new Random().nextInt(CLOUDS.length);
        Drawable d = getResources().getDrawable(CLOUDS[viewId]);
        final ImageView cloud = (ImageView) inflate.inflate(R.layout.cloud, null, false);
        cloud.setAlpha(0.1f*randInt(4, 10));
        cloud.setImageDrawable(d);

        if(randInt(0, 1) == 0) {
            cloud.setScaleX(-1);
        }
        int width = randInt((int)App.getWidthScreen()/4, (int)(App.getWidthScreen()/2.5f));
        cloud.setLayoutParams(new LayoutParams(width, width));

        cloud.setY(cloudY[idCurrentY]);

        cloudViews.add(cloud);
        root.addView(cloud);

        changeIdCurrentYCloud();

        startAnimationCloud(cloud);
    }

    private void changeIdCurrentYCloud() {
        int size = cloudY.length;
        idCurrentY++;
        if(idCurrentY == size) {
            idCurrentY = 0;
        }
    }

    public int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private void removeClouds() {
        for(int i = 0; i < cloudViews.size(); i++) {
            if(cloudViews.get(i).getX() > 1.5*App.getWidthScreen()) {
                root.removeView(cloudViews.get(i));
                cloudViews.remove(i);
            }
        }
    }

    private void removeSnowflakes() {
        for(int i = 0; i < snowflakeViews.size(); i++) {
            if(snowflakeViews.get(i).getY() > App.getHeightScreen()) {
                root.removeView(snowflakeViews.get(i));
                snowflakeViews.remove(i);
            }
        }
    }

    public void stopAnimation() {
        timerSnowflake.cancel();
        timerCloud.cancel();
    }

    public void setPeriodicityCreateSnowflake(int min, int max) {
        minPeriodicitySnowflake = min;
        maxPeriodicitySnowflake = max;
    }

    public void setPeriodicityCreateCloud(int periodicity) {
        periodicityCloud = periodicity;
    }


    public void startAnimation() {
        timerSnowflake = new Timer();
        timerSnowflake.schedule(new ExeTimerTask(MESSAGE_CREATE_SNOWFLAKE), 0, randInt(minPeriodicitySnowflake, maxPeriodicitySnowflake));

        timerCloud = new Timer();
        timerCloud.schedule(new ExeTimerTask(MESSAGE_CREATE_CLOUD), 0, periodicityCloud);
    }

    private class ExeTimerTask extends TimerTask {

        private int msg;

        ExeTimerTask(int msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            mHandler.sendEmptyMessage(msg);
        }
    }
}
