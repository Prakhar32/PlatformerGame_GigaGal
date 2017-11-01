package com.udacity.gamedev.gigagal.Overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 10-Oct-17.
 */

public class VictoryOverlay {
    Vector2 initpos;
    int score;

    public VictoryOverlay(int score){
        this.score = score;
        initpos = new Vector2();
    }

    public void render(BitmapFont font, SpriteBatch batch, int Score){
        font.getData().setScale(0.8f);
        //Gdx.gl.glClearColor(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, Color.BLUE.a);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.setColor(Color.YELLOW);

        TextureRegion region = Assets.instance.gigaGalAssets.standingright;
        /*batch.draw(region.getTexture(),
                0,
                0,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false
        );*/

        //font.draw(batch, "NEXT", 0, Constants.HUD_SIZE);
        font.draw(batch, "VICTORY", initpos.x - 20, initpos.y + 20, 0, Align.center, false);
        font.draw(batch, "SCORE " + Score, initpos.x - 10, initpos.y - 0);
        //font.draw(batch, "DISPLAY", 0, 0);
        Gdx.app.log("Victory", "Condn");
    }

    public void update(Vector2 position){
        initpos.x = position.x;
        initpos.y = position.y;
        Gdx.app.log("Victory Coor", " " + initpos.y);
    }
}
