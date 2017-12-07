package com.example.tamayoke;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    private static final Paint PAINT = new Paint();

    private static final int FPS = 60;

    private Robot robot;
    private Arrow leftArrow;
    private Arrow rightArrow;
    private Missile missile;

    private boolean pushLeftArrow;
    private boolean pushRightArrow;

    public GameView(Context context) {
        super(context);

        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
            }
        };
        Timer timer = new Timer(false);
        timer.schedule(timerTask, 0, 1000 / FPS);
    }

    @Override
    public void onDraw(Canvas canvas) {
         init(canvas);
         proc();

         robot.draw(canvas);
         missile.draw(canvas);
         leftArrow.draw(canvas);
         rightArrow.draw(canvas);
    }

    private void proc() {
        missile.move();
        if (pushLeftArrow) {
            robot.moveLeft();
        }
        if (pushRightArrow) {
            robot.moveRight();
        }


    }

    private void init(Canvas canvas) {
        if (robot == null) {
            robot = new Robot(getContext(), canvas);
        }
        if (leftArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
            leftArrow = new Arrow(bitmap, getContext(), canvas, Arrow.Direction.LEFT);
        }
        if (rightArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
            rightArrow = new Arrow(bitmap, getContext(), canvas, Arrow.Direction.RIGHT);
        }
        if (missile == null) {
            missile = new Missile(getContext(), canvas, 0);
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (leftArrow.contains((int) event.getX(), (int) event.getY())) {
                    pushLeftArrow = true;
                } else if (rightArrow.contains((int) event.getX(), (int) event.getY())) {
                    pushRightArrow = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                pushLeftArrow = false;
                pushRightArrow = false;
                break;
        }
        return true;
    }
}
