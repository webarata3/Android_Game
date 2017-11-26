package com.example.game15;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class LeftArrow {
    private final Paint PAINT = new Paint();
    private Rect rect;
    private Bitmap bitmap;

    public LeftArrow(Bitmap bitmap, int canvasWidth, int canvasHeight) {
        int left = 0;
        int top = canvasHeight - bitmap.getHeight();
        rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, PAINT);
    }

    public boolean contains(int x, int y) {
        return rect.contains(x, y);
    }
}
