package com.apporelbotna.asgame.bonk.engine.model.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;

/**
 * Created by Jandol on 05/08/2018.
 */

public class Drawable implements IDrawable
{
    private Paint paint;
    private BitmapSet bitmapSet;

    public Drawable(Paint paint, BitmapSet bitmapSet)
    {
        this.paint = paint;
        this.bitmapSet = bitmapSet;
    }

    public Paint getPaint()
    {
        return paint;
    }

    public void setPaint(Paint paint)
    {
        this.paint = paint;
    }

    public BitmapSet getBitmapSet()
    {
        return bitmapSet;
    }

    public void setBitmapSet(BitmapSet bitmapSet)
    {
        this.bitmapSet = bitmapSet;
    }

    @Override
    public void draw(Canvas canvas)
    {

    }
}
