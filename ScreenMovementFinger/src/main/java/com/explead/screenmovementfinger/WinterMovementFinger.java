package com.explead.screenmovementfinger;

import android.view.MotionEvent;
import android.view.View;

public class WinterMovementFinger {

    public interface OnSideFingerMovementCallback {
        void onUp();
        void onDown();
        void onRight();
        void onLeft();
    }

    private OnSideFingerMovementCallback onSideFingerMovementCallback;
    private int start_x, start_y, end_x, end_y;

    public WinterMovementFinger(OnSideFingerMovementCallback onSideFingerMovementCallback) {
        this.onSideFingerMovementCallback = onSideFingerMovementCallback;
    }

    public void setTouchView(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        start_x = (int) event.getX();
                        start_y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        end_x = (int) event.getX();
                        end_y = (int) event.getY();
                        move(start_x, start_y, end_x, end_y);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    private void move(int start_x, int start_y, int end_x, int end_y) {
        int side1 = (start_x - end_x);
        int side2 = (start_y - end_y);
        int hypotenuse = (int) (Math.sqrt(Math.abs(side1 * side1) + Math.abs(side2 * side2)));
        double angle = (Math.asin((double) side2 / hypotenuse)) * 57.295f;
        if (hypotenuse > 50 && ((angle < 30 && angle > -30) || (angle > 60) || (angle < -60))) {
            if ((side1 <= 0 && side2 >= 0 && angle < 30) || (side1 <= 0 && side2 <= 0 && angle > -30)) {
                onSideFingerMovementCallback.onRight();
            } else if ((side1 <= 0 && side2 >= 0 && angle > 60) || (side1 >= 0 && side2 >= 0 && angle > 60)) {
                onSideFingerMovementCallback.onUp();
            } else if ((side1 >= 0 && side2 >= 0 && angle < 30) || (side1 >= 0 && side2 <= 0 && angle > -30)) {
                onSideFingerMovementCallback.onLeft();
            } else if ((side1 >= 0 && side2 <= 0 && angle < -60) || (side1 <= 0 && side2 <= 0 && angle < -60)) {
                onSideFingerMovementCallback.onDown();
            }
        }
    }
}
