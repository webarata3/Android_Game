package com.example.game11;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class Missile {
    private static final Paint PAINT = new Paint();

    private static final int MISSILE_WIDTH = 10;
    private static final int MISSILE_HEIGHT = 40;

    private int missileSpeed;
    private int canvasWidth;
    private int canvasHeight;

    private Rect rect;

    public Rect getRect() {
        return rect;
    }

    public Missile(int missileSpeed, int canvasWidth, int canvasHeight) {
        rect = new Rect(0, 0, MISSILE_WIDTH, MISSILE_HEIGHT);
        this.missileSpeed = missileSpeed;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        initMissile();
    }

    public void initMissile() {
        Random random = new Random();
        int x = random.nextInt(canvasWidth - MISSILE_WIDTH);
        rect.offsetTo(x, -MISSILE_HEIGHT);
    }

    public void move() {
        rect.offset(0, missileSpeed);
        if (rect.top > canvasHeight) {
            initMissile();
        }
    }

    public void draw(Canvas canvas) {
        PAINT.setColor(Color.RED);
        canvas.drawRect(rect, PAINT);
    }
}
