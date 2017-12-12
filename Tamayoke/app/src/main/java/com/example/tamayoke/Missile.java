package com.example.tamayoke;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;

import java.util.Random;

public class Missile {
    private static final Paint PAINT = new Paint();
    private final Rect rect;
    private final int MOVE_SPEED;

    /** 動ける最大のX座標 */
    private final int MOVE_MAX_WIDTH;
    private final int CANVAS_HEIGHT;

    public Missile(Context ctx, Canvas canvas, int y) {
        Resources res = ctx.getResources();
        float density = res.getDisplayMetrics().density;
        int width = (int) (res.getDimension(R.dimen.missileWidth) * density);
        int height = (int) (res.getDimension(R.dimen.missileHeight) * density);

        MOVE_SPEED = (int) (res.getDimension(R.dimen.missileSpeed) * density);

        MOVE_MAX_WIDTH = canvas.getWidth() - width;
        CANVAS_HEIGHT = canvas.getHeight();

        int x = getMissileX();
        rect = new Rect(x, y, x + width, y + height);
        rect.offset(0, -height);
        int color = ContextCompat.getColor(ctx, R.color.missileColor);
        PAINT.setColor(color);
    }

    private int getMissileX() {
        Random random = new Random();
        return random.nextInt(MOVE_MAX_WIDTH);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, PAINT);
    }

    public void move() {
        rect.offset(0, MOVE_SPEED);
        if (rect.top > CANVAS_HEIGHT) {
            Random random = new Random();
            int x = random.nextInt(MOVE_MAX_WIDTH);
            rect.offsetTo(getMissileX(),0);
        }
    }

    public Rect getRect() {
        return rect;
    }
}
