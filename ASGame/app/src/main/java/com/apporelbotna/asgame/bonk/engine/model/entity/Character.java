package com.apporelbotna.asgame.bonk.engine.model.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;

// Un Character es IDrawable, PhysicsActor y
public abstract class Character extends PhysicBody implements IDrawable
{
    private transient Paint paint;
    protected int x, y;
    protected transient int state, sprite;
    private transient BitmapSet bitmapSet;

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getState()
    {
        return state;
    }

    public Character() { }

    public Character(BitmapSet bitmapSet)
    {
        this(bitmapSet, 0, 0);
    }

    public Character(BitmapSet bitmapSet, int x, int y)
    {
        this.bitmapSet = bitmapSet;
        this.x = x;
        this.y = y;
        this.state = 0;
        this.sprite = 0;
        this.paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.YELLOW);
        this.collisionRect = new Rect();
    }

    public void setBitmapSet(BitmapSet bitmapSet)
    {
        this.bitmapSet = bitmapSet;
    }

    public int[][] getAnimations()
    {
        return null;
    }

    public void physics(int delta)
    {
        this.updatePhysics(delta);
        updateCollisionRect();
    }

    public Rect getCollisionRect()
    {
        return collisionRect;
    }

    @Override
    public void draw(Canvas canvas)
    {
        try
        {
            int[] animation = getAnimations()[state];
            int bitmap = animation[sprite];
            canvas.drawBitmap(bitmapSet.getBitmap(bitmap), x, y, null);
            sprite++;
            sprite %= animation.length;
            if (collisionRect != null)
                canvas.drawRect(collisionRect, paint);
        } catch (Exception ignored)
        {
        }
    }

    public abstract void updatePhysics(int delta);

    public abstract void updateCollisionRect();
}