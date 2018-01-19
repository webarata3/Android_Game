package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    private static final Paint PAINT = new Paint();
    private Droid droid;
    private Arrow leftArrow;
    private Arrow rightArrow;
    private Missile[] missileList;

    private boolean pushLeftArrow;
    private boolean pushRightArrow;

    private final int DROID_SIZE;
    private final int DROID_SPEED;
    private final int ARROW_SIZE;
    private final int MISSILE_SPEED;
    private final int MISSILE_SIZE;
    private final int MESSAGE_FONT_SIZE;
    private final int MESSAGE_HEIGHT;

    private final int MISSILE_NUM = 3;

    private TimerTask timerTask;

    private static final int FPS = 60;

    private boolean gameOver = false;

    private GameTimer gameTimer;
    private long highScore;

    public GameView(Context context) {
        super(context);

        double density = getContext().getResources().getDisplayMetrics().density;
        DROID_SIZE = (int) (density * 50.0);
        DROID_SPEED = (int) (density * 5.0);
        ARROW_SIZE = (int) (density * 50.0);
        MISSILE_SPEED = (int) (density * 5.0);
        MISSILE_SIZE = (int) (density * 5.0);
        MESSAGE_FONT_SIZE = (int) (density * 25.0);
        MESSAGE_HEIGHT = (int) (density * 150.0);

        gameTimer = new GameTimer();
        highScore = 0;

        final Handler handler = new Handler();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
            }
        };
        Timer timer = new Timer(false);
        timer.schedule(timerTask, 0, 1000 / FPS);
    }

    @Override
    public void onDraw(Canvas canvas) {
        initOnDraw(canvas);

        PAINT.setTextSize(50);
        PAINT.setColor(Color.BLUE);
        canvas.drawText(gameTimer.getCurrentTime() + "ms", 10, 100, PAINT);
        PAINT.setTextSize(20);
        canvas.drawText("ハイスコア: " + highScore + "ms", 10, 150, PAINT);

        leftArrow.draw(canvas);
        rightArrow.draw(canvas);

        if (!gameOver) {
            if (pushLeftArrow) {
                droid.moveLeft();
            }
            if (pushRightArrow) {
                droid.moveRight();
            }

            for (Missile missile : missileList) {
                missile.move();
                missile.draw(canvas);
            }
        }
        droid.draw(canvas);

        if (gameOver || droid.hit(missileList)) {
            gameTimer.stop();
            gameOver = true;

            if (gameTimer.getCurrentTime() > highScore) {
                highScore = gameTimer.getCurrentTime();
            }

            for (Missile missile : missileList) {
                missile.draw(canvas);
            }
            droid.drawFire(canvas);
            showRestartMessage(canvas);
        }
    }

    private void showRestartMessage(Canvas canvas) {
        PAINT.setColor(Color.RED);
        PAINT.setTextSize(MESSAGE_FONT_SIZE);
        String restartString = getContext().getString(R.string.restart);
        int width = (int) PAINT.measureText(restartString);
        int x = (canvas.getWidth() - width) / 2;
        canvas.drawText(restartString, x, MESSAGE_HEIGHT, PAINT);
    }

    private void initOnDraw(Canvas canvas) {
        if (droid == null) {
            Bitmap droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droidBitmap = Bitmap.createScaledBitmap(droidBitmap, DROID_SIZE, DROID_SIZE, false);
            Bitmap fireBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
            fireBitmap = Bitmap.createScaledBitmap(fireBitmap, DROID_SIZE, DROID_SIZE, false);
            droid = new Droid(DROID_SPEED, droidBitmap, fireBitmap, canvas.getWidth(), canvas.getHeight());
        }
        if (leftArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap, ARROW_SIZE, ARROW_SIZE, false);
            leftArrow = new Arrow(bitmap, canvas.getWidth(), canvas.getHeight(), Arrow.Direction.LEFT);
        }
        if (rightArrow == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap, ARROW_SIZE, ARROW_SIZE, false);
            rightArrow = new Arrow(bitmap, canvas.getWidth(), canvas.getHeight(), Arrow.Direction.RIGHT);
        }
        if (missileList == null) {
            missileList = new Missile[MISSILE_NUM];
            for (int i = 0; i < missileList.length; i++) {
                missileList[i] = new Missile(MISSILE_SPEED, MISSILE_SIZE, canvas.getWidth(), canvas.getHeight(), i * -150);
            }
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameOver) break;
                if (leftArrow.contains((int) event.getX(), (int) event.getY())) {
                    pushLeftArrow = true;
                } else if (rightArrow.contains((int) event.getX(), (int) event.getY())) {
                    pushRightArrow = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (gameOver && !pushLeftArrow && !pushRightArrow) {
                    droid.init();
                    for (Missile missile : missileList) {
                        missile.initMissileSpeed();
                        missile.initMissile();
                    }
                    gameTimer.reset();
                    gameOver = false;
                    break;
                }
                pushLeftArrow = false;
                pushRightArrow = false;
                break;
        }
        return true;
    }
}
