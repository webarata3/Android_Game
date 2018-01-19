package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Droid {
    private final Paint PAINT = new Paint();
    private Rect rect;
    private Bitmap droidBitmap;
    private Bitmap fireBitmap;

    private int droidSpeed;
    private int moveMaxWidth;
    private int moveHeight;

    private int hp;
    private static final int INIT_HP = 3;

    public Droid(int droidSpeed, Bitmap droidBitmap, Bitmap fireBitmap, int canvasWidth, int canvasHeight) {
        this.droidSpeed = droidSpeed;
        moveMaxWidth = canvasWidth - droidBitmap.getWidth();
        moveHeight = canvasHeight - droidBitmap.getHeight();

        // ドロイド君を中央に表示する
        int left = moveMaxWidth / 2;
        int top = moveHeight;

        rect = new Rect(left, top, left + droidBitmap.getWidth(), top + droidBitmap.getHeight());
        this.droidBitmap = droidBitmap;
        this.fireBitmap = fireBitmap;

        hp = INIT_HP;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(droidBitmap, rect.left, rect.top, PAINT);
    }

    public void moveLeft() {
        rect.offset(-droidSpeed, 0);
        if (rect.left < 0) {
            rect.offsetTo(0, moveHeight);
        }
    }

    public void moveRight() {
        rect.offset(droidSpeed, 0);
        if (rect.left > moveMaxWidth) {
            rect.offsetTo(moveMaxWidth, moveHeight);
        }
    }

    public void init() {
        hp = INIT_HP;
        rect.offsetTo(moveMaxWidth / 2, moveHeight);
    }

    public boolean checkHit(Missile[] missileList) {
        for (Missile missile : missileList) {
            if (missile.getHit()) return false;
            if (Rect.intersects(rect, missile.getRect())) {
                missile.setHit(true);
                hp = hp - 1;
                return true;
            }
        }
        return false;
    }

    public void drawFire(Canvas canvas) {
        canvas.drawBitmap(fireBitmap, rect.left, rect.top, PAINT);
    }

    public int getHp() {
        return hp;
    }
}
