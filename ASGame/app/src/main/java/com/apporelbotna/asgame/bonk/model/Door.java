package com.apporelbotna.asgame.bonk.model;

import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;

/**
 * Created by Jandol on 05/07/2018.
 */

public class Door extends com.apporelbotna.asgame.bonk.engine.model.Character
{
    public static final int SCORE_PER_LEVEL = 50;

    private static final int[][] ANIMATIONS = new int[][]{
            new int[]{52}
    };

    public Door(BitmapSet bitmapSet, int x, int y)
    {
        super(bitmapSet, x, y);
        this.sprite = 0;
    }

    @Override
    public void updatePhysics(int delta) { }

    @Override
    public void updateCollisionRect()
    {
        collisionRect.set(x, y, x + 21, y + 32);
    }

    @Override
    public int[][] getAnimations()
    {
        return ANIMATIONS;
    }
}
