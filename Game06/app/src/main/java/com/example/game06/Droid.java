package com.example.game06;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Droid {
    private final Paint PAINT = new Paint();
    private Rect rect;
    private Bitmap bitmap;

    public Droid(Bitmap bitmap, int canvasWidth, int canvasHeight) {
        // ドロイド君を中央に表示する
        int left = (canvasWidth - bitmap.getWidth()) / 2;
        int top = canvasHeight - bitmap.getHeight();

        rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, PAINT);
    }

    public void moveLeft() {
        rect.offset(-10, 0);
    }

    public void moveRight() {
        rect.offset(10, 0);
    }
}
