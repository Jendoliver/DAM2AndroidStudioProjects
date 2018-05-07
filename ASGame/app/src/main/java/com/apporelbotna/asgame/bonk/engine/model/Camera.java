package com.apporelbotna.asgame.bonk.engine.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * Created by Jandol on 04/25/2018.
 */

public class Camera
{
    public static final int SCALED_HEIGHT = 16 * 16;       // 16 rows of scene (16px each tile)

    // Update screen offsets to always have Bonk visible
    private int offsetX , offsetY;
    private int screenWidth, screenHeight;
    private int scaledWidth;

    private float scale;

    private Scene scene;
    private Paint guiPaint;
    private Paint guiPaintKeys;
    private Paint guiPaintPause;
    private Paint guiPaintPauseBtn;
    private Paint guiPaintScore;

    public Camera(Scene scene)
    {
        this.scene = scene;

        guiPaint = new Paint();
        guiPaint.setColor(Color.GRAY);
        guiPaint.setTextSize(10);

        guiPaintPause = new Paint();
        guiPaintPause.setTextSize(20);
        guiPaintPause.setStrokeWidth(10);
        guiPaintPause.setColor(Color.WHITE);

        guiPaintPauseBtn = new Paint();
        guiPaintPauseBtn.setTextSize(5);
        guiPaintPauseBtn.setStrokeWidth(5);
        guiPaintPauseBtn.setColor(Color.BLACK);

        guiPaintScore = new Paint();
        guiPaintScore.setTextSize(3);
        guiPaintPauseBtn.setStrokeWidth(3);
        guiPaintScore.setColor(Color.BLACK);

        guiPaintKeys = new Paint();
        guiPaintKeys.setColor(Color.argb(20, 0, 0, 0));
    }

    // TODO create GUI class and delegate
    public void drawGUI(Canvas canvas, boolean paused)
    {
        canvas.scale(scale * scaledWidth / 100, scale * SCALED_HEIGHT / 100);

        if(paused)
        {
            canvas.drawRect(0, 0, 100, 100, guiPaintKeys);
            canvas.drawText("PAUSE", 30, 40, guiPaintPause);
            return;
        }

        canvas.drawText("Score: " + String.valueOf(scene.getPlayer().getScore()), 2, 4, guiPaintScore);
        canvas.drawText("Lives: " + String.valueOf(scene.getPlayer().getLives()), 80, 4, guiPaintScore);

        canvas.drawText("PAUSE", 40, 5, guiPaintPauseBtn);

        canvas.drawRect(1, 76, 19, 99, guiPaintKeys);
        canvas.drawText("«", 8, 92, guiPaint);

        canvas.drawRect(21, 76, 39, 99, guiPaintKeys);
        canvas.drawText("»", 28, 92, guiPaint);

        canvas.drawRect(81, 76, 99, 99, guiPaintKeys);
        canvas.drawText("^", 88, 92, guiPaint);
    }

    public void updateOffsets()
    {
        if (scaledWidth * SCALED_HEIGHT == 0) return;
        int x = scene.getPlayer().getX();
        int y = scene.getPlayer().getY();

        // OFFSET X (100 scaled-pixels margin)
        offsetX = Math.max(offsetX, x - scaledWidth + 124);     // 100 + Bonk Width (24)
        offsetX = Math.min(offsetX, scene.getWidth() - scaledWidth - 1);
        offsetX = Math.min(offsetX, x - 100);
        offsetX = Math.max(offsetX, 0);

        // OFFSET Y (50 scaled-pixels margin)
        offsetY = Math.max(offsetY, y - SCALED_HEIGHT + 82);     // 50 + Bonk Height (32)
        offsetY = Math.min(offsetY, scene.getHeight() - SCALED_HEIGHT - 1);
        offsetY = Math.min(offsetY, y - 50);
        offsetY = Math.max(offsetY, 0);
    }

    public void refreshScaleFactor(Canvas canvas)
    {
        // Refresh scale factor if screen has changed sizes
        if (canvas.getWidth() * canvas.getHeight() != screenWidth * screenHeight)
        {
            screenWidth = canvas.getWidth();
            screenHeight = canvas.getHeight();
            if (screenWidth * screenHeight == 0) return; // 0 px on screen (not fully loaded)
            // New Scaling factor
            scale = (float) screenHeight / SCALED_HEIGHT;
            scaledWidth = (int) (screenWidth / scale);
        }

        // --- FIRST DRAW ROUND (scaled)
        canvas.save();
        canvas.scale(scale, scale);
        canvas.translate(-offsetX, -offsetY);
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScaledWidth()
    {
        return scaledWidth;
    }

    public int getOffsetX()
    {
        return offsetX;
    }

    public int getOffsetY()
    {
        return offsetY;
    }
}
