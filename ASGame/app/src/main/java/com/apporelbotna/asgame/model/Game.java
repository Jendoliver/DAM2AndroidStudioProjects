package com.apporelbotna.asgame.model;

/**
 * Created by Jandol on 02/13/2018.
 */

public class Game
{
    private int id;
    private String name;
    private String description;
    private String imagePath;

    public Game() { }

    public Game(int id, String name, String description, String imagePath)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
