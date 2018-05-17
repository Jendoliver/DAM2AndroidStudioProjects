package com.apporelbotna.asgame.bonk.engine.model;

import com.apporelbotna.asgame.bonk.engine.model.entity.Enemy;
import com.apporelbotna.asgame.bonk.model.Coin;
import com.apporelbotna.asgame.bonk.model.Crab;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandol on 05/08/2018.
 */

public class GameData
{
    public int currentLevel;
    public int playerX;
    public int playerY;
    public int playerScore;
    public int playerLives;
    public List<Coin> levelCoins;
    private List<Crab> levelCrabs;

    public GameData(Scene scene)
    {
        currentLevel = scene.getCurrentLevel();
        playerX = scene.getPlayer().getX();
        playerY = scene.getPlayer().getY();
        playerScore = scene.getPlayer().getScore();
        playerLives = scene.getPlayer().getLives();
        levelCoins = scene.getCoins();
        levelCrabs = scene.getCrabs();
    }

    public String toJson()
    {
        return new Gson().toJson(this);
    }

    public static GameData fromJson(String json)
    {
        return new Gson().fromJson(json, GameData.class);
    }

    public List<Enemy> getEnemies()
    {
        List<Enemy> enemies = new ArrayList<>();
        if(levelCrabs != null)
            for(Crab crab : levelCrabs)
                enemies.add(crab);
        return enemies;
    }
}
