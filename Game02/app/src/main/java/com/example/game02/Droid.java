package com.example.game02;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Droid {
    private final Paint PAINT = new Paint();
    private Rect rect;
    private Bitmap bitmap;

    public Droid(Bitmap bitmap, int width, int height) {
        // ドロイド君を中央に表示する
        int left = (width - bitmap.getWidth()) / 2;
        int top = height - bitmap.getHeight();

        rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, PAINT);
    }

    public void move() {
        rect.top = rect.top - 5;
    }
}
