package com.example.drawtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class DrawView extends View {
    private final Paint PAINT = new Paint();

    private static final int VIEW_WIDTH = 600;
    private static final int VIEW_HEIGHT = 300;

    private int displayWidth;
    private int displayHeight;

    private int marginLeft;
    private int marginTop;

    private float scale;

    private boolean init;

    public DrawView(Context context) {
        super(context);

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        initOnDraw(canvas);

        PAINT.setColor(Color.WHITE);
        canvas.drawRect(marginLeft, marginTop, VIEW_WIDTH + marginLeft, VIEW_HEIGHT + marginTop, PAINT);

        // 矩形
        PAINT.setColor(Color.RED);
        canvas.drawRect(50, 50, 100, 100, PAINT);

        // 円
        canvas.drawCircle(50, 150, 25, PAINT);

        // 点
        PAINT.setStrokeWidth(1.0f);
        for (int i = 0; i < 100; i++) {
            if (i % 5 != 0) {
                continue;
            }

            canvas.drawPoint(200, 50 + i, PAINT);
        }

        // 線
        canvas.drawLine(250, 50, 250, 150, PAINT);

        // 文字
        PAINT.setTextSize(40);
        canvas.drawText("Hello Android", 50, 400, PAINT);
    }

    public void initOnDraw(Canvas canvas) {
        if (!init) {
            int canvasWidth = canvas.getWidth();
            int canvasHeight = canvas.getHeight();

            // 最小公倍数を求め、アスペクト比を計算する
            int lcm = calcLcm(VIEW_WIDTH, VIEW_HEIGHT);
            int ratioW = VIEW_WIDTH / lcm;
            int ratioH = VIEW_HEIGHT / lcm;

            // 四角の大きさが小さい方にアスペクト比を掛ける
            double w = canvasWidth / ratioW;
            double h = canvasHeight / ratioH;
            double baseSize = w > h ? h : w;
            int initW = (int) (ratioW * baseSize);
            int initH = (int) (ratioH * baseSize);

            // 実際のキャンバスのサイズで割る
            double canvasRatioW = (double) initW / (double) canvasWidth;
            double canvasRatioH = (double) initH / (double) canvasHeight;

            Log.i("###########ratio", canvasRatioW + "," + canvasRatioH);
            if (canvasRatioW > canvasRatioH) {
                displayWidth = canvasWidth;
                displayHeight = (int) (initH * (1.0 / canvasRatioW));
            } else {
                displayWidth = (int) (initW * (1.0 / canvasRatioH));
                displayHeight = canvasHeight;
            }

            Log.i("###########d", displayWidth + "," + displayHeight);

            float scaleX = (float) displayWidth / (float) VIEW_WIDTH;
            float scaleY = (float) displayHeight / (float) VIEW_HEIGHT;

            Log.i("#########last", scaleX + "," + scaleY);
            scale = scaleX > scaleY ? scaleY : scaleX;
            canvas.scale(scale, scale);

            if (canvasRatioW > canvasRatioH) {
                marginLeft = 0;
                marginTop = (int) ((canvasHeight - (int) (VIEW_HEIGHT * scale)) / 2 / scale);
            } else {
                marginLeft = (int) ((canvasWidth - (int) (VIEW_WIDTH * scale)) / 2 / scale);
                marginTop = 0;
            }
            PAINT.setColor(Color.BLACK);
            canvas.drawRect(0, 0, getWidth(), getHeight(), PAINT);
        }
    }

    private static int calcLcm(int m, int n) {
        int min = m < n ? m : n;
        int result = 1;
        for (int i = 2; i < min; i++) {
            if (m % i == 0 && n % i == 0) {
                result = result * i;
                m = m / i;
                n = n / i;
                i--;
            }
        }
        return result;
    }
}
