package com.example.game01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
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
        if (droidBitmap == null) {
            droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droidBitmap = Bitmap.createScaledBitmap(droidBitmap, 100, 100, false);
        }

        if (droidRect == null) {
            int left = (canvas.getWidth() - droidBitmap.getWidth()) / 2;
            int top = canvas.getHeight() - droidBitmap.getHeight();

            droidRect = new Rect(left, top, left + droidBitmap.getWidth(), top + droidBitmap.getHeight());
        }

        canvas.drawBitmap(droidBitmap, droidRect.left, droidRect.top, PAINT);
    }
}

