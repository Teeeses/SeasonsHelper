package com.explead.screenmovementfinger;

import android.view.MotionEvent;
import android.view.View;

public class SummerMovementFinger {

    private float sizeField;
    private int sizeCells;
    private View field;

    public interface OnFingerMovementCallback {
        void onDown(int x, int y);
        void onMove(int x, int y);
        void onUp(int x, int y);
    }

    private OnFingerMovementCallback onFingerMovementCallback;

    private int lastX = -1;
    private int lastY = -1;

    public SummerMovementFinger(View field, float sizeField, int sizeCells, OnFingerMovementCallback onFingerMovementCallback) {
        this.field = field;
        this.sizeField = sizeField;
        this.sizeCells = sizeCells;
        this.onFingerMovementCallback = onFingerMovementCallback;
    }

    public void setTouchView() {
        field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        final int x = (int) (event.getY() / (sizeField/sizeCells));
                        final int y = (int) (event.getX() / (sizeField/sizeCells));
                        onFingerMovementCallback.onDown(x, y);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        final int x = (int) (event.getY() / (sizeField/sizeCells));
                        final int y = (int) (event.getX() / (sizeField/sizeCells));
                        if(x >= 0 && y >= 0 && x < sizeCells && y < sizeCells) {
                            if (x != lastX || y != lastY) {
                                lastX = x;
                                lastY = y;
                                onFingerMovementCallback.onMove(x, y);
                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        final int x = (int) (event.getY() / (sizeField/sizeCells));
                        final int y = (int) (event.getX() / (sizeField/sizeCells));
                        onFingerMovementCallback.onUp(x, y);
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
