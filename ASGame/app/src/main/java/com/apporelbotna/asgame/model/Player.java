package com.apporelbotna.asgame.model;

import java.util.List;

/**
 * Created by Jandol on 02/13/2018.
 */

public class Player
{
    private String name;
    private User user;
    private Game game;
    private int score;

    public Player() { }

    public Player(String name, User user, Game game, int score)
    {
        this.name = name;
        this.user = user;
        this.game = game;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
