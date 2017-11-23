package com.example.game02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    private static final Paint PAINT = new Paint();
    private Droid droid;

    public GameView(Context context) {
        super(context);

        final int FPS = 60;
        final Handler handler = new Handler();
        final Runnable requestRedraw = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        Timer timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(requestRedraw);
            }
        }, 0, 1000 / FPS);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (droid == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droid = new Droid(bitmap, canvas.getWidth(), canvas.getHeight());
        }

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
                return true;
            case MotionEvent.ACTION_UP:
                droid.move();
                break;
        }
        return super.onTouchEvent(event);
    }
}

