package com.apporelbotna.asgame.bonk.model;

import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;
import com.apporelbotna.asgame.bonk.engine.model.entity.Character;

public class Coin extends Character
{
    public static final transient int SCORE_PER_COIN = 10;

    public Coin(BitmapSet bitmapSet, int x, int y)
    {
        super(bitmapSet, x, y);
        this.sprite = (int) (Math.random() * 5);
    }

    private static final int[][] ANIMATIONS = new int[][]{
            new int[]{29, 30, 31, 32, 33}
    };

    @Override
    public int[][] getAnimations()
    {
        return ANIMATIONS;
    }

    @Override
    public void updatePhysics(int delta)
    {
    }

    @Override
    public void updateCollisionRect()
    {
        collisionRect.set(x, y, x + 12, y + 12);
    }
}
