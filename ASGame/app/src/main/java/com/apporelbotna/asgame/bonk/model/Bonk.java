package com.apporelbotna.asgame.bonk.model;

import android.graphics.Rect;

import com.apporelbotna.asgame.bonk.engine.model.Audio;
import com.apporelbotna.asgame.bonk.engine.model.BitmapSet;
import com.apporelbotna.asgame.bonk.engine.model.entity.Enemy;
import com.apporelbotna.asgame.bonk.engine.model.Input;
import com.apporelbotna.asgame.bonk.engine.model.entity.Player;
import com.apporelbotna.asgame.bonk.engine.model.Scene;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bonk extends Player
{
    private static final int ORIGINAL_SPEED = 2;

    private Timer boostTimer; // TODO change to javax.swing.Timer to remove pause bug

    public static final int INITIAL_X = 100;
    public static final int INITIAL_Y = 0;
    public static final int MAX_LIVES = 3;

    private static final int[][] ANIMATIONS = new int[][]{
            {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 14, 14, 14, 14, 13}, // 0: standing by
            {6, 7, 8, 9},                         // 1: walking left
            {0, 1, 2, 3},                         // 2: walking right
            {47, 48, 49, 50, 51, 50, 49, 48},     // 3: dead
            {10},                                 // 4: Jumping left
            {4},                                  // 5: Jumping right
            {11},                                 // 6: Falling left
            {5},                                  // 7: Falling right
            {15},                                // 8: Jumping/falling front
    };

    private static final int[] NEW_STATES = {
            4, 8, 5,
            1, 0, 2,
            6, 8, 7
    };

    private Scene scene;
    private int vx;         // vel-X is 1 or 2 (boosted velocity)
    private int vy;
    private boolean isJumping;
    private boolean isDying;

    private static final int MAX_FALLING_VELOCITY = 4;
    private static final int JUMP_VELOCITY = -8;

    private static final int PAD_LEFT = 2;
    private static final int PAD_TOP = 0;
    private static final int COLLISION_BOX_WIDTH = 20;
    private static final int COLLISION_BOX_HEIGHT = 32;

    public Bonk(BitmapSet bitmapSet, Input input, Scene scene, int x, int y)
    {
        super(bitmapSet, input, scene, MAX_LIVES);
        this.scene = scene;
        this.respawnAt(x, y);
        boostTimer = new Timer();
    }
    @Override
    public Rect getCollisionRect()
    {
        return (state == 3) ? null : collisionRect;
    }

    @Override
    public int[][] getAnimations()
    {
        return ANIMATIONS;
    }

    @Override
    public void outOfLives()
    {
        score = 0;
        lives = getMaxLives();
        scene.loadFirstLevel();
    }

    public void boost()
    {
        if(vx != ORIGINAL_SPEED)
        {
            boostTimer.cancel();
            boostTimer.purge();
        }
        vx = ORIGINAL_SPEED * 2;
        boostTimer = new Timer();
        boostTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                vx = ORIGINAL_SPEED;
            }
        }, Booster.TIME_IN_MILLIS);
    }

    private void changeState(int state)
    {
        if (this.state == state) return;
        this.state = state;
        this.sprite = 0;
    }

    public void die()
    {
        boostTimer.cancel();
        boostTimer.purge();
        isDying = true;
        input.block();
        changeState(3);
        loseLive();

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                respawnAt(INITIAL_X, INITIAL_Y);
                input.unblock();
            }
        }, 1000);

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                isDying = false;
            }
        }, 2000);

    }

    private void respawnAt(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.vx = ORIGINAL_SPEED;
    }

    @Override
    public void updatePhysics(int delta)
    {
        // If died, no physics
        if (state == 3) return;

        // Analyze user input
        int vx = 0;
        if (input.isLeft())
        {
            vx = -this.vx;
        } else if (input.isRight())
        {
            vx = this.vx;
        }
        if (input.isJump())
        {
            if (!isJumping)
            {
                vy = JUMP_VELOCITY;
                isJumping = true;
            }
            input.clearJump();
        }
        if (input.isKeyboard())
        {
            input.setKeyboard(false);
        }

        // 1) detect wall to right
        int newX = x + vx;
        int newY = y;
        if (vx > 0)
        {
            int col = (newX + PAD_LEFT + COLLISION_BOX_WIDTH) / 16;
            int r1 = (newY + PAD_TOP) / 16;
            int r2 = (newY + PAD_TOP + COLLISION_BOX_HEIGHT - 1) / 16;
            for (int row = r1; row <= r2; row++)
            {
                if (scene.isWall(row, col))
                {
                    newX = col * 16 - PAD_LEFT - COLLISION_BOX_WIDTH - 1;
                    break;
                }
            }
        }
        // 2) detect wall to left
        if (vx < 0)
        {
            int col = (newX + PAD_LEFT) / 16;
            int r1 = (newY + PAD_TOP) / 16;
            int r2 = (newY + PAD_TOP + COLLISION_BOX_HEIGHT - 1) / 16;
            for (int row = r1; row <= r2; row++)
            {
                if (scene.isWall(row, col))
                {
                    newX = (col + 1) * 16 - PAD_LEFT;
                    break;
                }
            }
        }

        // 3) detect ground
        // physics (try fall and detect ground)
        vy++;
        if (vy > MAX_FALLING_VELOCITY) vy = MAX_FALLING_VELOCITY;
        newY = y + vy;
        if (vy >= 0)
        {
            int c1 = (newX + PAD_LEFT) / 16;
            int c2 = (newX + PAD_LEFT + COLLISION_BOX_WIDTH) / 16;
            int row = (newY + PAD_TOP + COLLISION_BOX_HEIGHT) / 16;
            for (int col = c1; col <= c2; col++)
            {
                if (scene.isGround(row, col))
                {
                    newY = row * 16 - PAD_TOP - COLLISION_BOX_HEIGHT;
                    vy = 0;
                    isJumping = false;
                    break;
                }
            }
        }
        // 4) detect ceiling
        if (vy < 0)
        {
            int c1 = (newX + PAD_LEFT) / 16;
            int c2 = (newX + PAD_LEFT + COLLISION_BOX_WIDTH) / 16;
            int row = (newY + PAD_TOP) / 16;
            for (int col = c1; col <= c2; col++)
            {
                if (scene.isWall(row, col))
                {
                    newY = (row + 1) * 16 - PAD_TOP;
                    vy = 0;
                    break;
                }
            }
        }

        // 5) detect static characters (can only collide with them if moving, since they don't)
        if (vx != 0 || vy != 0)
        {
            // Door
            if(this.collisionRect.intersect(scene.getDoor().getCollisionRect()))
            {
                Audio.getInstance().nextLevel();
                score += Door.SCORE_PER_LEVEL;
                scene.loadNextLevel();
                respawnAt(INITIAL_X, INITIAL_Y);
                return;
            }

            List<Booster> boosters = scene.getBoosters();
            for(Booster booster : boosters)
            {
                if(this.collisionRect.intersect(booster.getCollisionRect()))
                {
                    boosters.remove(booster);
                    Audio.getInstance().booster();
                    boost();
                    break;
                }
            }

            List<Coin> coins = scene.getCoins();
            for(Coin coin : coins)
            {
                if(this.collisionRect.intersect(coin.getCollisionRect()))
                {
                    coins.remove(coin);
                    Audio.getInstance().coin();
                    score += Coin.SCORE_PER_COIN;
                    break;
                }
            }
        }

        // 6) detect enemies
        if(!isDying)
        {
            List<Enemy> enemies = scene.getEnemies();
            for (Enemy enemy : enemies)
            {
                if (this.collisionRect.intersect(enemy.getCollisionRect()))
                {
                    this.die();
                    break;
                }
            }
        }

        // apply resulting physics
        x = newX;
        y = newY;

        // screen limits
        x = Math.max(x, -PAD_LEFT);
        x = Math.min(x, scene.getWidth() - COLLISION_BOX_WIDTH);
        y = Math.min(y, scene.getHeight() - COLLISION_BOX_HEIGHT);

        // state change
        int c = (vx < 0) ? 0 : ((vx == 0) ? 1 : 2);
        int r = (vy < 0) ? 0 : ((vy == 0) ? 1 : 2);
        changeState(NEW_STATES[r * 3 + c]);
    }

    @Override
    public void updateCollisionRect()
    {
        collisionRect.set(
                x + PAD_LEFT,
                y + PAD_TOP,
                x + PAD_LEFT + COLLISION_BOX_WIDTH,
                y + PAD_TOP + COLLISION_BOX_HEIGHT
        );
    }
}