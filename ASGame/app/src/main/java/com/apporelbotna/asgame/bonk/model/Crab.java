package com.apporelbotna.asgame.bonk.model;

import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;
import com.apporelbotna.asgame.bonk.engine.model.entity.Enemy;

public class Crab extends Enemy
{
    private int x0, x1, incX;

    public Crab(BitmapSet bitmapSet, int x0, int x1, int y)
    {
        super(bitmapSet, x0, y - 5);
        this.x0 = x0;
        this.x1 = x1;
        this.incX = 1;
    }

    private static final int[][] ANIMATIONS = new int[][]{
            new int[]{16, 17, 16, 17, 16, 17, 18, 19, 18, 19, 18, 19}
    };

    @Override
    public int[][] getAnimations()
    {
        return ANIMATIONS;
    }

    @Override
    public void updatePhysics(int delta)
    {
        this.x += incX;
        if (x <= x0) incX = 1;
        if (x >= x1) incX = -1;
    }

    @Override
    public void updateCollisionRect()
    {
        int top = y + 8 - ((sprite < 6) ? 8 : 0);
        int bottom = y + 22 + ((sprite >= 6) ? 8 : 0);
        collisionRect.set(x, top, x + 32, bottom);
    }

}