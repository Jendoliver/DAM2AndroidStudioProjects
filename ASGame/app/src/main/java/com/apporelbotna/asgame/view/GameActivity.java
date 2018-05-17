package com.apporelbotna.asgame.view;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.bonk.engine.model.GameEngine;
import com.apporelbotna.asgame.bonk.view.GameView;

public class GameActivity extends AppCompatActivity
{
    private GameEngine gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // FULL SCREEN GAME, NO ACTION BAR (theme also must be some "no-actionbar")
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Volume controls to control the music and effects volume for the game
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // GameView as the content view for the application
        GameView gameView = new GameView(this);
        setContentView(gameView);

        // Create the engine
        gameEngine = new GameEngine(this, gameView);
        gameEngine.start();

        loadGame();
    }

    // Delegate methods to GameEngine

    @Override
    public void onResume()
    {
        super.onResume();
        loadGame();
        gameEngine.resume();
    }

    @Override
    public void onPause()
    {
        saveGame();
        gameEngine.pause();
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        gameEngine.stop();
    }

    // For keyboard input
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        return (gameEngine == null) || gameEngine.onKeyEvent(event);
    }

    public void loadGame()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String game = prefs.getString("game", "");

        if(game != null && !game.isEmpty())
            gameEngine.loadGame(game);
    }

    public void saveGame()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("game", gameEngine.saveGame());
        editor.apply();
    }
}
