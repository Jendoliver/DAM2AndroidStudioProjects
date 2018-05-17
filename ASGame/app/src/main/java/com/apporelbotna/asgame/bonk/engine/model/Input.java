package com.apporelbotna.asgame.bonk.engine.model;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class Input
{
    private boolean left, right, jump, pause, inputBlocked;

    public void goLeft()
    {
        left = true;
        right = false;
    }

    public void goRight()
    {
        left = false;
        right = true;
    }

    public void stopLR()
    {
        left = right = false;
    }

    public void stop()
    {
        left = right = jump = false;
    }

    public void jump()
    {
        jump = true;
    }

    public void clearJump()
    {
        jump = false;
    }

    public void pause()
    {
        Audio.getInstance().pause();
        pause = true;
    }

    public void clearPause()
    {
        pause = false;
    }

    public boolean isLeft()
    {
        return left;
    }

    public boolean isRight()
    {
        return right;
    }

    public boolean isJump()
    {
        return jump;
    }

    public boolean isJumpL()
    {
        return jump & left;
    }

    public boolean isJumpR()
    {
        return jump & right;
    }

    public boolean isJumpU()
    {
        return jump & !left & !right;
    }

    public boolean isPause()
    {
        return pause;
    }

    public boolean isStoppedLR()
    {
        return !left & !right;
    }

    public boolean isStopped()
    {
        return !left & !right & !jump;
    }

    public boolean isInputBlocked()
    {
        return inputBlocked;
    }

    public void block()
    {
        stop();
        inputBlocked = true;
    }

    public void unblock()
    {
        inputBlocked = false;
    }

    // needed for keyboard input
    private boolean keyboard;

    public void setKeyboard(boolean keyboard)
    {
        this.keyboard = keyboard;
    }

    public boolean isKeyboard()
    {
        return keyboard;
    }

    public boolean process(MotionEvent motionEvent, int screenWidth, int screenHeight)
    {
        int act = motionEvent.getActionMasked();
        int i = motionEvent.getActionIndex();
        boolean down = (act == MotionEvent.ACTION_DOWN) ||
                (act == MotionEvent.ACTION_POINTER_DOWN);
        boolean touching = (act != MotionEvent.ACTION_UP) &&
                (act != MotionEvent.ACTION_POINTER_UP) &&
                (act != MotionEvent.ACTION_CANCEL);
        int x = (int) (motionEvent.getX(i)) * 100 / screenWidth;
        int y = (int) (motionEvent.getY(i)) * 100 / screenHeight;
        if ((y > 75) && (x < 40))
        {
            if (!touching) stopLR();
            else if (x < 20) goLeft();            // LEFT
            else goRight();                       // RIGHT
        }
        else if ((y > 75) && (x > 80))
        {
            if (down) jump();                     // JUMP
        }
        else if((x > 35) && (x < 60) && (y > 0) && (y < 10))
        {
            if (down) pause();
        }
        return true;
    }

    public boolean process(KeyEvent keyEvent)
    {
        boolean down = (keyEvent.getAction() == KeyEvent.ACTION_DOWN);
        if (!down) return true;
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.KEYCODE_Z:
                goLeft();
                break;
            case KeyEvent.KEYCODE_X:
                goRight();
                break;
            case KeyEvent.KEYCODE_M:
                jump();
                break;
            case KeyEvent.KEYCODE_P:
                pause();
                break;
            default:
                return false;
        }
        setKeyboard(true);
        return true;
    }
}
