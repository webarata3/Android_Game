package com.example.game03;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Droid {
    private final Paint PAINT = new Paint();
    private Rect rect;
    private Bitmap bitmap;

    private float acceleration;

    private int canvasHeight;

    public Droid(Bitmap bitmap, int canvasWidth, int canvasHeight) {
        // ドロイド君を中央に表示する
        int left = (canvasWidth - bitmap.getWidth()) / 2;
        int top = canvasHeight - bitmap.getHeight();

        rect = new Rect(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        this.bitmap = bitmap;
        this.canvasHeight = canvasHeight;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, PAINT);
    }

    public void move() {
        acceleration = acceleration - 30;
        if (acceleration < 0 && acceleration < -(canvasHeight - rect.bottom)) {
            acceleration = -(canvasHeight - rect.bottom);
        }
        rect.offset(0, -Math.round(acceleration));
    }

    public void jump(float acceleration) {
        this.acceleration = acceleration;
    }
}
