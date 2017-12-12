package com.example.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class Missile {
    private static final Paint PAINT = new Paint();

    private int missileSpeed;
    private int canvasWidth;
    private int canvasHeight;

    private final int MISSILE_WIDTH;
    private final int MISSILE_HEIGHT;

    private Rect rect;

    private int initOffsetY;

    public Rect getRect() {
        return rect;
    }

    public Missile(int missileSpeed, int missileSize, int canvasWidth, int canvasHeight, int initOffsetY) {
        MISSILE_WIDTH = missileSize;
        MISSILE_HEIGHT = missileSize * 4;
        rect = new Rect(0, 0, MISSILE_WIDTH, MISSILE_HEIGHT);
        Random random = new Random();
        int x = random.nextInt(canvasWidth - MISSILE_WIDTH);
        this.missileSpeed = missileSpeed;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.initOffsetY = initOffsetY;
        initMissile();
    }

    public void initMissile() {
        Random random = new Random();
        int x = random.nextInt(canvasWidth - MISSILE_WIDTH);
        rect.offsetTo(x, initOffsetY);
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
