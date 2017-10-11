package com.udacity.gamedev.gigagal.Overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 10-Oct-17.
 */

public class GameOverOverlay {
    public static void render(SpriteBatch batch, BitmapFont font, Level level){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        font.setColor(Color.RED);
        font.draw(batch, "GAME OVER", Constants.WORLD_SIZE / 2 + 20, Constants.WORLD_SIZE / 2 + 20);
        font.draw(batch, "SCORE" + level.score, Constants.WORLD_SIZE + 20, Constants.WORLD_SIZE - 20);
     }
}
