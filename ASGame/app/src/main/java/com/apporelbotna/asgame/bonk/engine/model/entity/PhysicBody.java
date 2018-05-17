package com.apporelbotna.asgame.bonk.engine.model.entity;

import android.graphics.Rect;

/**
 * Created by Jandol on 04/25/2018.
 */

public abstract class PhysicBody
{
    protected Rect collisionRect;

    abstract void updatePhysics(int delta);
}
