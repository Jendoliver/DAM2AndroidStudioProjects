package com.apporelbotna.asgame.bonk.engine.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

// Un Character es Drawable, PhysicsActor y
public abstract class Character implements Drawable, PhysicObject
{
    private Paint paint;
    protected int x, y, state, sprite;
    protected Rect collisionRect;
    private BitmapSet bitmapSet;

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getState()
    {
        return state;
    }

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