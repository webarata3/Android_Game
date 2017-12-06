package com.example.game07;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Arrow {
    private final Paint PAINT = new Paint();

    public enum Direction {
        LEFT, RIGHT;
    }

    private Rect rect;
    private Bitmap bitmap;

    public Arrow(Bitmap bitmap, int canvasWidth, int canvasHeight, Direction direction) {
        int left = 0;
        if (direction == Direction.RIGHT) {
            left = canvasWidth - bitmap.getWidth();
        }
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
