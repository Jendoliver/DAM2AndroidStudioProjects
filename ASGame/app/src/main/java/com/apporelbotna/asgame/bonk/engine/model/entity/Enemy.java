package com.apporelbotna.asgame.bonk.engine.model.entity;

import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;

abstract public class Enemy extends Character
{
    public Enemy(BitmapSet bitmapSet, int x, int y)
    {
        super(bitmapSet, x, y);
    }
}
