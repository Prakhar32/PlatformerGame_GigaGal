package com.udacity.gamedev.gigagal.Overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 10-Oct-17.
 */

public class GameOverOverlay {
    Vector2 position;

    public GameOverOverlay(){
        position = new Vector2();
    }

    public void render(SpriteBatch batch, BitmapFont font, int score){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        font.setColor(Color.RED);
        font.draw(batch, "GAME OVER", position.x - 30, position.y + 25);
        font.draw(batch, "SCORE " + score, position.x - 15, position.y + 5);
     }

     public void update(Vector2 position){
         this.position.x = position.x;
         this.position.y = position.y;
     }
}
