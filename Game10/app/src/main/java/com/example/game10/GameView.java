package com.example.game10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
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

    private static final int FPS = 60;

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
            missile.initMissile();
            canvas.drawColor(Color.RED);
        }
    }

    private void initOnDraw(Canvas canvas) {
        if (droid == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            droid = new Droid(bitmap, canvas.getWidth(), canvas.getHeight());
        }
        if (leftArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap, 64, 64, false);
            leftArrow = new LeftArrow(bitmap, canvas.getWidth(), canvas.getHeight());
        }
        if (rightArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap, 64, 64, false);
            rightArrow = new RightArrow(bitmap, canvas.getWidth(), canvas.getHeight());
        }
        if (missile == null) {
            missile = new Missile(canvas.getWidth(), canvas.getHeight());
        }
    }

    private void initMissile(Rect rect, Canvas canvas) {
        Random random = new Random();
        int x = random.nextInt(canvas.getWidth());
        rect.offsetTo(x, -40);
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
        return super.onTouchEvent(event);
    }
}
