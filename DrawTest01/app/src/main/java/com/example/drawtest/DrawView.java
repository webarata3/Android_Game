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

        // 線
        canvas.drawLine(250, 50, 250, 150, PAINT);
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
