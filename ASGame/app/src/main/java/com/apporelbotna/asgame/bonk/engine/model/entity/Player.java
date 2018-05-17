package com.apporelbotna.asgame.bonk.engine.model.entity;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.apporelbotna.asgame.bonk.engine.model.Audio;
import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;
import com.apporelbotna.asgame.bonk.engine.model.Camera;
import com.apporelbotna.asgame.bonk.engine.model.Input;
import com.apporelbotna.asgame.bonk.engine.model.Scene;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jandol on 04/25/2018.
 */

public abstract class Player extends Character
{
    protected Input input;
    protected Camera camera;
    protected Scene scene;
    protected int score;

    private int maxLives;
    protected int lives;

    public Player(BitmapSet bitmapSet, Input input, Scene scene, int maxLives)
    {
        super(bitmapSet);
        this.input = input;
        this.scene = scene;
        this.maxLives = maxLives;
        lives = maxLives;
    }

    public Input getInput()
    {
        return input;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getMaxLives()
    {
        return maxLives;
    }

    public int getLives()
    {
        return lives;
    }

    public void setLives(int lives)
    {
        this.lives = lives;
    }

    public void resetLives()
    {
        lives = maxLives;
    }

    public Camera getCamera()
    {
        return camera;
    }

    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }

    public boolean processInput(MotionEvent motionEvent, int screenWidth, int screenHeight)
    {
        int act = motionEvent.getActionMasked();
        boolean down = (act == MotionEvent.ACTION_DOWN) ||
                (act == MotionEvent.ACTION_POINTER_DOWN);

        if (input.isInputBlocked()) return true;
        if(input.isPause() && down)
        {
            input.clearPause();
            return true;
        }
        return input.process(motionEvent, screenWidth, screenHeight);
    }

    public boolean processInput(KeyEvent keyEvent)
    {
        if (input.isInputBlocked()) return true;
        if(input.isPause() && keyEvent.getAction() != KeyEvent.ACTION_UP)
        {
            input.clearPause();
            return true;
        }
        return input.process(keyEvent);
    }

    public void loseLive()
    {
        lives -= 1;
        if(lives < 1)
        {
            Audio.getInstance().stopMusic();
            Audio.getInstance().gameOver();
            new Timer().schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    Audio.getInstance().startMusic();
                }
            }, 4000);

            outOfLives();
        }
        else
            Audio.getInstance().die();
    }

    public abstract void outOfLives();

}
