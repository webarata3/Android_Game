package com.example.game12;

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
    private Droid droid;
    private LeftArrow leftArrow;
    private RightArrow rightArrow;
    private Missile missile;

    private boolean pushLeftArrow;
    private boolean pushRightArrow;

    private final int DROID_SIZE;
    private final int DROID_SPEED;
    private final int ARROW_SIZE;
    private final int MISSILE_SPEED;
    private final int MISSILE_SIZE;

    private TimerTask timerTask;

    public GameView(Context context) {
        super(context);

        final int FPS = 60;
        final Handler handler = new Handler();
        timerTask = new TimerTask() {
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
        double density = getContext().getResources().getDisplayMetrics().density;
        DROID_SIZE = (int) (density * 50.0);
        DROID_SPEED = (int) (density * 5.0);
        ARROW_SIZE = (int) (density * 50.0);
        MISSILE_SPEED = (int) (density * 5.0);
        MISSILE_SIZE = (int) (density * 5.0);
    }

    @Override
    public void onDraw(Canvas canvas) {
        initOnDraw(canvas);

        leftArrow.draw(canvas);
        rightArrow.draw(canvas);

        if (pushLeftArrow) {
            droid.moveLeft();
        }
        if (pushRightArrow) {
            droid.moveRight();
        }

        droid.draw(canvas);
        missile.move();
        missile.draw(canvas);

        if (droid.hit(missile)) {
            droid.drawFire(canvas);
            timerTask.cancel();
        }
    }

    private void initOnDraw(Canvas canvas) {
        if (droid == null) {
            Bitmap droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droidBitmap = Bitmap.createScaledBitmap(droidBitmap, DROID_SIZE, DROID_SIZE, false);
            Bitmap fireBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
            fireBitmap = Bitmap.createScaledBitmap(fireBitmap, DROID_SIZE, DROID_SIZE, false);
            droid = new Droid(DROID_SPEED, droidBitmap, fireBitmap, canvas.getWidth(), canvas.getHeight());
        }
        if (leftArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap, ARROW_SIZE, ARROW_SIZE, false);
            leftArrow = new LeftArrow(bitmap, canvas.getWidth(), canvas.getHeight());
        }
        if (rightArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap, ARROW_SIZE, ARROW_SIZE, false);
            rightArrow = new RightArrow(bitmap, canvas.getWidth(), canvas.getHeight());
        }
        if (missile == null) {
            missile = new Missile(MISSILE_SPEED, MISSILE_SIZE, canvas.getWidth(), canvas.getHeight());
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
                return true;
            case MotionEvent.ACTION_UP:
                pushLeftArrow = false;
                pushRightArrow = false;
                break;
        }
        return super.onTouchEvent(event);
    }
}
