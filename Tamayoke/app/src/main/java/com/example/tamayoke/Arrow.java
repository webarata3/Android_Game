package com.example.tamayoke;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;

public class Arrow {
    public enum Direction {
        LEFT {
            @Override
            public int getInitX(int canvasWidth, int arrowWidth) {
                return 0;
            }
        }, RIGHT {
            @Override
            public int getInitX(int canvasWidth, int arrowWidth) {
                return canvasWidth - arrowWidth;
            }
        };

        public abstract int getInitX(int canvasWidth, int arrowWidth);
    }

    private static final Paint PAINT = new Paint();

    private final Rect rect;
    private final int COLOR;
    private final Bitmap BITMAP;

    public Arrow(Bitmap bitmap, Context ctx, Canvas canvas, Direction direction) {
        Resources res = ctx.getResources();

        float density = res.getDisplayMetrics().density;
        int width = (int) (res.getDimension(R.dimen.arrowWidth) * density);
        int height = (int) (res.getDimension(R.dimen.arrowHeight) * density);
        BITMAP = Bitmap.createScaledBitmap(bitmap, width, height, false);

        int x = direction.getInitX(canvas.getWidth(), width);
        int y = canvas.getHeight() - height;

        rect = new Rect(x, y, x + width, y + height);
        COLOR = ContextCompat.getColor(ctx, R.color.arrowColor);
    }

    public void draw(Canvas canvas) {
        PAINT.setColor(COLOR);
        canvas.drawBitmap(BITMAP, rect.left, rect.top, PAINT);
    }

    public boolean contains(int x, int y) {
        return rect.contains(x, y);
    }
}
