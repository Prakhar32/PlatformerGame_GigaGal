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

public class VictoryOverlay {

    public static void render(BitmapFont font, SpriteBatch batch, Level level){
        Gdx.gl.glClearColor(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, Color.BLUE.a);
        font.setColor(Color.YELLOW);
        font.draw(batch, "VICTORY", Constants.WORLD_SIZE / 2 + 20, Constants.WORLD_SIZE / 2 + 20);
        font.draw(batch, "SCORE" + level.score, Constants.WORLD_SIZE + 20, Constants.WORLD_SIZE - 20);
        Gdx.app.log("Victory", "Condn");
    }
}
