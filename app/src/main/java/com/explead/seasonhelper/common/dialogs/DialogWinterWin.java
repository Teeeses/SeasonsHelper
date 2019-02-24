package com.explead.seasonhelper.common.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.utils.Utils;

public class DialogWinterWin extends Dialog {


    public interface OnDialogCompletionListener {
        void onMenu();
        void onNextLevel();
    }

    private OnDialogCompletionListener mOnDialogCompletionListener;

    private Context context;

    public DialogWinterWin(@NonNull Context context, OnDialogCompletionListener mOnDialogCompletionListener) {
        super(context);
        this.context = context;
        this.mOnDialogCompletionListener = mOnDialogCompletionListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_winter_win);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        Drawable drawable = new ColorDrawable(Color.BLACK);
        drawable.setAlpha(50);
        getWindow().setBackgroundDrawable(drawable);

        ImageView imageLuminescence = findViewById(R.id.imageLuminescence);
        imageLuminescence.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_luminescence));

        TextView textCompleted = findViewById(R.id.textCompleted);
        textCompleted.setTypeface(Utils.getTypeGecko(context.getAssets()));
        textCompleted.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_text_completed));

        ImageView imageCompleted = findViewById(R.id.imageCompleted);
        imageCompleted.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_image_win_cube));

        ImageView btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnDialogCompletionListener.onMenu();
                dismiss();
            }
        });

        ImageView btnNextLevel = findViewById(R.id.btnNextLevel);
        btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnDialogCompletionListener.onNextLevel();
                dismiss();
            }
        });

        btnMenu.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_buttons_win));
        btnNextLevel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_buttons_win));
    }

    @Override
    public void onBackPressed() {
        mOnDialogCompletionListener.onMenu();
        super.onBackPressed();

    }
}