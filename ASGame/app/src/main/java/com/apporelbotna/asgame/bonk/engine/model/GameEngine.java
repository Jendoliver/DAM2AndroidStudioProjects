package com.apporelbotna.asgame.bonk.engine.model;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.apporelbotna.asgame.bonk.model.Bonk;
import com.apporelbotna.asgame.bonk.view.GameView;

public class GameEngine
{
    private static final int UPDATE_DELAY = 50;             // 50ms             => 20 physics/sec
    private static final int INVALIDATES_PER_UPDATE = 2;    // 2 * 50ms = 100ms => 10 redraws/sec

    private Context context;
    private GameView gameView;
    private Handler handler;
    private Scene scene;

    public GameEngine(Context context, GameView gameView)
    {
        this.context = context;
        // Initialize everything
        Audio.create(context);

        // Relate to the game view
        this.gameView = gameView;
        gameView.setGameEngine(this);

        // Load Scene
        BitmapSet bitmapSet = new BitmapSet(context);
        scene = new Scene(context, bitmapSet);
        scene.loadFirstLevel();

        // Create Bonk
        scene.setPlayer(new Bonk(bitmapSet, new Input(), scene, Bonk.INITIAL_X, Bonk.INITIAL_Y));
        scene.getPlayer().setCamera(new Camera(scene));

        // Program the Handler for engine refresh (physics et al)
        handler = new Handler();
        Runnable runnable = new Runnable()
        {
            private long last = 0;
            private int count = 0;

            @Override
            public void run()
            {
                handler.postDelayed(this, UPDATE_DELAY);
                // Delta time between calls
                if (last == 0) last = System.currentTimeMillis();
                long now = System.currentTimeMillis();
                int delta = (int) (now - last);
                last = now;
                physics(delta);
                if (++count % INVALIDATES_PER_UPDATE == 0)
                {
                    GameEngine.this.gameView.invalidate();
                    count = 0;
                }
            }
        };
        handler.postDelayed(runnable, UPDATE_DELAY);
    }

    // For activity start
    public void start()
    {
        Audio.getInstance().startMusic();
    }

    // For activity stop
    public void stop()
    {
        Audio.getInstance().stopMusic();
    }

    // For activity pause
    public void pause()
    {
        Audio.getInstance().stopMusic();
    }

    // For activity resume
    public void resume()
    {
        Audio.getInstance().startMusic();
    }

    // Attend user input
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        if (scene.getScreenHeight() * scene.getScreenWidth() == 0) return true;
        return scene.getPlayer().processInput(motionEvent, scene.getScreenWidth(), scene.getScreenHeight());
    }

    // Testing with keyboard
    public boolean onKeyEvent(KeyEvent keyEvent)
    {
        return scene.getPlayer().processInput(keyEvent);
    }

    // Perform physics on all game objects
    private void physics(int delta)
    {
        // Other game objects' physics
        scene.physics(delta);
    }

    // Screen redraw
    public void draw(Canvas canvas)
    {
        if (scene == null) return;

        scene.refreshScaleFactor(canvas);

        // Background
        scene.draw(
                canvas,
                scene.getOffsetX(),
                scene.getOffsetY(),
                scene.getScaledWidth(),
                Camera.SCALED_HEIGHT);

        // --- SECOND DRAW ROUND (no-scaled)
        canvas.restore();

        scene.drawGUI(canvas);
    }

    public String saveGame()
    {
        return new GameData(scene).toJson();
    }

    public void loadGame(String json)
    {
        GameData gameData = GameData.fromJson(json);
        final BitmapSet bitmapSet = new BitmapSet(context);
        gameData.levelCoins.forEach(c -> c.setBitmapSet(bitmapSet));
        gameData.getEnemies().forEach(e -> e.setBitmapSet(bitmapSet));

        scene.setCurrentLevel(gameData.currentLevel);
        scene.getPlayer().setX(gameData.playerX);
        scene.getPlayer().setY(gameData.playerY);
        scene.getPlayer().setScore(gameData.playerScore);
        scene.getPlayer().setLives(gameData.playerLives);
        scene.setCoins(gameData.levelCoins);
        scene.setEnemies(gameData.getEnemies());
    }

}
