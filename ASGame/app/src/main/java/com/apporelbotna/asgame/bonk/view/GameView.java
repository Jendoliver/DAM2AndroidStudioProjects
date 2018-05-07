package com.apporelbotna.asgame.bonk.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.apporelbotna.asgame.bonk.engine.model.GameEngine;

public class GameView extends View
{
    private GameEngine gameEngine;

    public GameView(Context context)
    {
        this(context, null, 0);
    }

    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void setGameEngine(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        if (gameEngine == null) return;
        gameEngine.draw(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")     // accessibility is virtually impossible
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        return (gameEngine == null) || gameEngine.onTouchEvent(motionEvent);
    }
}
