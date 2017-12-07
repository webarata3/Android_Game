package com.example.tamayoke;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;

public class Robot {
    private static final Paint PAINT = new Paint();
    private final int WIDTH;
    private final int HEIGHT;

    private final Rect rect;
    private final int COLOR;
    private final int MOVE_SPEED;

    private final int MOVE_MAX_WIDTH;
    private final int MOVE_HEIGHT;

    public Robot(Context ctx, Canvas canvas) {
        Resources res = ctx.getResources();
        float density = res.getDisplayMetrics().density;
        WIDTH = (int) (res.getDimension(R.dimen.robotWidth) * density);
        HEIGHT = (int) (res.getDimension(R.dimen.robotHeight) * density);

        int x = (canvas.getWidth() - WIDTH) / 2;
        int y = canvas.getHeight() - HEIGHT;

        rect = new Rect(x, y, x + WIDTH, y + HEIGHT);
        COLOR = ContextCompat.getColor(ctx, R.color.robotColor);

        MOVE_SPEED = (int) (res.getDimension(R.dimen.robotMoveSpeed) * density);

        MOVE_MAX_WIDTH = canvas.getWidth() - rect.width();
        MOVE_HEIGHT = canvas.getHeight() - HEIGHT;
    }

    public void draw(Canvas canvas) {
        PAINT.setColor(COLOR);
        canvas.drawRect(rect, PAINT);
    }

    public void moveLeft() {
        rect.offset(-MOVE_SPEED, 0);
        if (rect.left < 0) {
            rect.offsetTo(0, MOVE_HEIGHT);
        }
    }

    public void moveRight() {
        rect.offset(MOVE_SPEED, 0);
        if (rect.left > MOVE_MAX_WIDTH) {
            rect.offsetTo(MOVE_MAX_WIDTH, MOVE_HEIGHT);
        }
    }

    public boolean hit(Missile missile) {
        return rect.contains(missile.getRect());
    }
}
