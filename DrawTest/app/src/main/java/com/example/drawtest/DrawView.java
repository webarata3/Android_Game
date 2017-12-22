package com.example.drawtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    private final Paint PAINT = new Paint();

    private final float VIEW_WIDTH = 720;
    private final float VIEW_HEIGHT = 900;
    private float scale;

    private boolean init;

    public DrawView(Context context) {
        super(context);

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        initOnDraw(canvas);

        PAINT.setColor(Color.WHITE);
        canvas.drawRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT, PAINT);

        // 矩形
        PAINT.setColor(Color.RED);
        canvas.drawRect(50, 50, 100, 100, PAINT);

        // 円
        canvas.drawCircle(50, 150, 25, PAINT);

        // 点
        PAINT.setStrokeWidth(1.0f);
        for (int i = 0; i < 100; i++) {
            if (i % 5 != 0) {
                continue;
            }

            canvas.drawPoint(200, 50 + i, PAINT);
        }

        // 線
        canvas.drawLine(250, 50, 250, 150, PAINT);

        // 文字
        PAINT.setTextSize(40);
        canvas.drawText("Hello Android", 50, 400, PAINT);
    }

    public void initOnDraw(Canvas canvas) {
        if (!init) {
            float scaleX = getWidth() / VIEW_WIDTH;
            float scaleY = getHeight() / VIEW_HEIGHT;
            scale = scaleX > scaleY ? scaleY : scaleX;
            canvas.scale(scale, scale);

            PAINT.setColor(Color.BLACK);
            canvas.drawRect(0, 0, getWidth(), getHeight(), PAINT);
        }
    }
}
