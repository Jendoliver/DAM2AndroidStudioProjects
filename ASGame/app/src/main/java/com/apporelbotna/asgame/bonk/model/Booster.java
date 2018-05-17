package com.apporelbotna.asgame.bonk.model;

import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;
import com.apporelbotna.asgame.bonk.engine.model.entity.Character;

/**
 * Created by Jandol on 05/07/2018.
 */

public class Booster extends Character
{
    public static final int TIME_IN_MILLIS = 10000;

    private static final int[][] ANIMATIONS = new int[][]{
            new int[]{39}
    };

    public Booster(BitmapSet bitmapSet, int x, int y)
    {
        super(bitmapSet, x, y);
        this.sprite = 0;
    }

    @Override
    public void updatePhysics(int delta) { }

    @Override
    public void updateCollisionRect()
    {
        collisionRect.set(x, y, x + 16, y + 16);
    }

    @Override
    public int[][] getAnimations()
    {
        return ANIMATIONS;
    }
}
