package com.example.game15;

public class GameTimer {
    private long startTime;
    private long stopTime;

    private boolean isStop;

    public GameTimer() {
        reset();
    }

    public void reset(){
        startTime = System.currentTimeMillis();
        isStop = false;
    }

    public long getCurrentTime() {
        if (isStop) return stopTime;
        return System.currentTimeMillis() - startTime;
    }

    public void stop() {
        if (isStop) return;
        isStop = true;
        stopTime = System.currentTimeMillis() - startTime;
    }
}
