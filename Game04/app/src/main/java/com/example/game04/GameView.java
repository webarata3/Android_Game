package com.example.game04;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    private Bitmap leftArrow;
    private Bitmap rightArrow;

    private Rect leftRect;
    private Rect rightRect;

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
        if (droid == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            droid = new Droid(bitmap, canvas.getWidth(), canvas.getHeight());
        }

        if (leftArrow == null) {
            leftArrow = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
            leftArrow = Bitmap.createScaledBitmap(leftArrow, 64, 64, false);
            int left = 0;
            int top = canvas.getHeight() - leftArrow.getHeight();
            leftRect = new Rect(left, top, left + leftArrow.getWidth(), top + leftArrow.getHeight());
        }
        if (rightArrow == null) {
            rightArrow = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
            rightArrow = Bitmap.createScaledBitmap(rightArrow, 64, 64, false);
            int left = canvas.getWidth() - rightArrow.getWidth();
            int top = canvas.getHeight() - rightArrow.getHeight();
            rightRect = new Rect(left, top, left + rightArrow.getWidth(), top + rightArrow.getHeight());
        }

        canvas.drawBitmap(leftArrow, leftRect.left, leftRect.top, PAINT);
        canvas.drawBitmap(rightArrow, rightRect.left, rightRect.top, PAINT);

        droid.draw(canvas);
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
                break;
            case MotionEvent.ACTION_UP:
                if (leftRect.contains((int) event.getX(), (int) event.getY())) {
                    droid.moveLeft();
                }
                if (rightRect.contains((int) event.getX(), (int) event.getY())) {
                    droid.moveRight();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
