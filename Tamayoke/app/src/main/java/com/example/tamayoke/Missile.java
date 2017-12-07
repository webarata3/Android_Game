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
    private final int WIDTH;
    private final int HEIGHT;

    private final Rect rect;
    private final int COLOR;
    private final int MOVE_SPEED;

    private final int MOVE_MAX_WIDTH;
    private final int MOVE_MAX_HEIGHT;
    private final int CANVAS_HEIGHT;

    public Missile(Context ctx, Canvas canvas, int y) {
        Resources res = ctx.getResources();
        float density = res.getDisplayMetrics().density;
        WIDTH = (int) (res.getDimension(R.dimen.missileWidth) * density);
        HEIGHT = (int) (res.getDimension(R.dimen.missileHeight) * density);

        MOVE_SPEED = (int) (res.getDimension(R.dimen.missileSpeed) * density);

        MOVE_MAX_WIDTH = canvas.getWidth() - WIDTH;
        MOVE_MAX_HEIGHT = canvas.getHeight() - HEIGHT;
        CANVAS_HEIGHT = canvas.getHeight();

        int x = getMissileX();
        rect = new Rect(x, y, x + WIDTH, y + HEIGHT);
        rect.offset(0, -HEIGHT);
        COLOR = ContextCompat.getColor(ctx, R.color.missileColor);
    }

    private int getMissileX() {
        Random random = new Random();
        return random.nextInt(MOVE_MAX_WIDTH);
    }

    public void draw(Canvas canvas) {
        PAINT.setColor(COLOR);
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
