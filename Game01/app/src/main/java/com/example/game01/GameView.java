package com.example.game01;

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
    private Bitmap droidBitmap;
    private Rect droidRect;

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
        if (droidBitmap == null) {
            droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
        }

        if (droidRect == null) {
            // ドロイド君を中央に表示する
            int left = (canvas.getWidth() - droidBitmap.getWidth()) / 2;
            int top = canvas.getHeight() - droidBitmap.getHeight();

            droidRect = new Rect(left, top, left + droidBitmap.getWidth(), top + droidBitmap.getHeight());
        }

        canvas.drawBitmap(droidBitmap, droidRect.left, droidRect.top, PAINT);
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
                droidRect.offset(0, -5);
                break;
        }
        return super.onTouchEvent(event);
    }
}

