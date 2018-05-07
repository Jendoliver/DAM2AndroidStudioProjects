package com.apporelbotna.asgame.bonk.engine.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.SparseIntArray;
import android.widget.Toast;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.bonk.model.Booster;
import com.apporelbotna.asgame.bonk.model.Coin;
import com.apporelbotna.asgame.bonk.model.Crab;
import com.apporelbotna.asgame.bonk.model.Door;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Scene
{
    private static final int[] LEVELS = { R.raw.mini, R.raw.scene, R.raw.scene2 };
    private int currentLevel;

    private String scene[];
    private Context context;
    private Paint paint;
    private BitmapSet bitmapSet;

    private int sceneWidth, sceneHeight;
    private SparseIntArray CHARS;
    private String GROUND, WALLS;
    private int WATERLEVEL, SKY, WATERSKY, WATER;

    // TODO change to list of drawables
    private Player player;
    private Door door;
    private List<Coin> coins;
    private List<Enemy> enemies;
    private List<Booster> boosters;

    public Scene(Context context, BitmapSet bitmapSet)
    {
        paint = new Paint();
        CHARS = new SparseIntArray();
        WATERLEVEL = 999; // rayika del aguika
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        boosters = new ArrayList<>();
        this.context = context;
        this.bitmapSet = bitmapSet;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public int getScreenHeight()
    {
        return player.getCamera().getScreenHeight();
    }

    public int getScreenWidth()
    {
        return player.getCamera().getScreenWidth();
    }

    public int getOffsetX()
    {
        return player.getCamera().getOffsetX();
    }

    public int getOffsetY()
    {
        return player.getCamera().getOffsetY();
    }

    public int getScaledWidth()
    {
        return player.getCamera().getScaledWidth();
    }

    public boolean isPaused()
    {
        return player.getInput().isPause();
    }

    public void loadNextLevel()
    {
        currentLevel++;
        if(currentLevel < LEVELS.length)
            loadFromFile(LEVELS[currentLevel]);
    }

    public void loadFirstLevel()
    {
        currentLevel = 0;
        loadFromFile(LEVELS[0]);
    }

    public void loadFromFile(int resource)
    {
        coins.clear();
        enemies.clear();
        door = null;

        InputStream res = context.getResources().openRawResource(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(res));
        List<String> lines = new ArrayList<>();
        try
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (line.isEmpty()) continue;
                line = line.trim();
                if (!line.contains("=")) continue;                  // NO VALID LINE
                if (line.startsWith("=")) continue;                 // COMMENT
                String[] parts = line.split("=", 2);
                String cmd = parts[0].trim();
                String args = parts[1].trim();
                String[] parts2;
                switch (cmd)
                {
                    case "SCENE":
                        lines.add(args);
                        break;
                    case "CHARS":
                        parts2 = args.split(" ");
                        for (String def : parts2)
                        {
                            String[] item = def.split("=");
                            if (item.length != 2) continue;
                            char c = item[0].trim().charAt(0);
                            int idx = Integer.parseInt(item[1].trim());
                            CHARS.put(c, idx);
                        }
                        break;
                    case "GROUND":
                        GROUND = args;
                        break;
                    case "WALLS":
                        WALLS = args;
                        break;
                    case "WATER":
                        parts2 = args.split(",");
                        if (parts2.length != 4) continue;
                        WATERLEVEL = Integer.parseInt(parts2[0].trim());
                        SKY = Integer.parseInt(parts2[1].trim());
                        WATERSKY = Integer.parseInt(parts2[2].trim());
                        WATER = Integer.parseInt(parts2[3].trim());
                        break;
                    case "COIN":
                        parts2 = args.split(",");
                        if (parts2.length != 2) continue;
                        int coinX = Integer.parseInt(parts2[0].trim()) * 16;
                        int coinY = Integer.parseInt(parts2[1].trim()) * 16;
                        Coin coin = new Coin(bitmapSet, coinX, coinY);
                        coins.add(coin);
                        break;
                    case "CRAB":
                        parts2 = args.split(",");
                        if (parts2.length != 3) continue;
                        int crabX0 = Integer.parseInt(parts2[0].trim()) * 16;
                        int crabX1 = Integer.parseInt(parts2[1].trim()) * 16;
                        int crabY = Integer.parseInt(parts2[2].trim()) * 16;
                        Crab crab = new Crab(bitmapSet, crabX0, crabX1, crabY);
                        enemies.add(crab);
                        break;
                    case "DOOR":
                        parts2 = args.split(",");
                        if (parts2.length != 2) continue;
                        int doorX = Integer.parseInt(parts2[0].trim()) * 16;
                        int doorY = Integer.parseInt(parts2[1].trim()) * 16;
                        door = new Door(bitmapSet, doorX, doorY);
                        break;
                    case "BOOSTER":
                        parts2 = args.split(",");
                        if (parts2.length != 2) continue;
                        int boosterX = Integer.parseInt(parts2[0].trim()) * 16;
                        int boosterY = Integer.parseInt(parts2[1].trim()) * 16;
                        boosters.add(new Booster(bitmapSet, boosterX, boosterY));
                        break;
                }
            }
            scene = lines.toArray(new String[0]);
            reader.close();
            sceneHeight = scene.length;
            sceneWidth = scene[0].length();
        } catch (IOException e)
        {
            Toast.makeText(context, "Error loading scene:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean isGround(int r, int c)
    {
        if (r < 0) return false;
        if (r >= sceneHeight) return false;
        if (c < 0) return false;
        if (c >= sceneWidth) return false;
        char sc = scene[r].charAt(c);
        return (GROUND.indexOf(sc) != -1);
    }

    public boolean isWall(int r, int c)
    {
        if (r < 0) return false;
        if (r >= sceneHeight) return false;
        if (c < 0) return false;
        if (c >= sceneWidth) return false;
        char sc = scene[r].charAt(c);
        return (WALLS.indexOf(sc) != -1);
    }

    public boolean isCoin(int r, int c)
    {
        if (r < 0) return false;
        if (r >= sceneHeight) return false;
        if (c < 0) return false;
        if (c >= sceneWidth) return false;
        char sc = scene[r].charAt(c);
        return (WALLS.indexOf(sc) != -1);
    }

    public int getSceneWidth()
    {
        return sceneWidth;
    }

    public int getSceneHeight()
    {
        return sceneHeight;
    }

    public int getWaterLevel()
    {
        return WATERLEVEL;
    }

    public int getWidth()
    {
        return sceneWidth * 16;
    }

    public int getHeight()
    {
        return sceneHeight * 16;
    }

    public List<Coin> getCoins()
    {
        return coins;
    }

    public List<Enemy> getEnemies()
    {
        return enemies;
    }

    public List<Booster> getBoosters()
    {
        return boosters;
    }

    public Door getDoor()
    {
        return door;
    }

    // Scene physics
    void physics(int delta)
    {
        if (isPaused()) return;
        player.physics(delta);
        door.physics(delta);
        for (Enemy enemy : enemies) enemy.physics(delta);
        for (Coin coin : coins) coin.physics(delta);
        for (Booster booster : boosters) booster.physics(delta);
        player.getCamera().updateOffsets();
    }

    // Scene draw
    void draw(Canvas canvas, int offsetX, int offsetY, int screenWidth, int screenHeight)
    {
        if (scene == null) return;

        // Compute which tiles will be drawn
        int l = Math.max(0, offsetX / 16);
        int r = Math.min(scene[0].length(), offsetX / 16 + screenWidth / 16 + 2);
        int t = Math.max(0, offsetY / 16);
        int b = Math.min(scene.length, offsetY / 16 + screenHeight / 16 + 2);

        // Do the x-y loops over the visible scene
        for (int y = t; y < b; y++)
        {

            // Compute the background index (sky / water)
            int bgIdx = SKY;
            if (y == WATERLEVEL) bgIdx = WATERSKY;
            else if (y > WATERLEVEL) bgIdx = WATER;
            Bitmap bgBitmap = bitmapSet.getBitmap(bgIdx);

            for (int x = l; x < r; x++)
            {
                // Draw the background tile
                canvas.drawBitmap(bgBitmap, x * 16, y * 16, paint);

                // Compute the bitmap index for the current tile
                char c = scene[y].charAt(x);
                int index = CHARS.get(c);
                if (index == SKY) continue;
                Bitmap bitmap = bitmapSet.getBitmap(index);
                canvas.drawBitmap(bitmap, x * 16, y * 16, paint);
            }
        }

        player.draw(canvas);
        door.draw(canvas);
        for (Coin coin : coins) coin.draw(canvas);
        for (Enemy enemy : enemies) enemy.draw(canvas);
        for (Booster booster : boosters) booster.draw(canvas);
    }

    public void drawGUI(Canvas canvas)
    {
        player.getCamera().drawGUI(canvas, isPaused());
    }

    public void refreshScaleFactor(Canvas canvas)
    {
        player.getCamera().refreshScaleFactor(canvas);
    }
}
