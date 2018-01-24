package com.example.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {
    public interface OnKeyEventListener {
        void pushedLeftKey();
        void pushedRightKey();
        void releasedLeftKey();
        void releasedRightKey();
    }

    private OnKeyEventListener onKeyEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this);
        onKeyEventListener = (OnKeyEventListener) gameView;
        setContentView(gameView);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    onKeyEventListener.pushedLeftKey();
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    onKeyEventListener.pushedRightKey();
                    return true;
            }
        }
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    onKeyEventListener.releasedLeftKey();
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    onKeyEventListener.releasedRightKey();
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
