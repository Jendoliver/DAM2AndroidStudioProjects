package com.apporelbotna.asgame.model.dao;

import android.content.Context;

import com.apporelbotna.asgame.model.Game;
import com.apporelbotna.asgame.model.Player;
import com.apporelbotna.asgame.model.User;

import java.util.List;

/**
 * Created by Jandol on 02/13/2018.
 */

public interface GameAsyncDAO
{
    void getPlayer(int id);
    void getGame(int id);
    void findPlayers(Game game);
    void findGames();
    void findScores(User user);
    void updateUser(User user);
    void updatePlayer(Player player);
    void updatePlayerScore();

    interface WeakReference
    {
        Context getContext();
    }
}
