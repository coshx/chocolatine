package com.coshx.chocolatine.views;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @class OnSwipeTouchListener
 * @brief https://gist.github.com/nesquena/ed58f34791da00da9751
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > getSwipeThreshold() && Math.abs(velocityX) > getSwipeVelocityThreshold()) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > getSwipeThreshold() && Math.abs(velocityY) > getSwipeVelocityThreshold()) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeUp() {
    }

    public void onSwipeDown() {
    }

    public int getSwipeThreshold() {
        return 100;
    }

    public int getSwipeVelocityThreshold() {
        return 100;
    }
}
