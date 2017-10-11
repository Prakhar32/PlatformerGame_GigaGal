package com.udacity.gamedev.gigagal.Overlays;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 08-Oct-17.
 */

public class GigaGalHud {
    public static float lives;
    GigaGal gigaGal;
    Level level;

    public GigaGalHud(Level level) {
        lives = 3;
        this.level = level;
        gigaGal = level.getGigaGal();
    }

    public void render(BitmapFont font, SpriteBatch batch){
        font.getData().setScale(0.8f);
        font.draw(batch, Constants.SCORE + " :  " + (int)level.score, 0, Constants.HUD_SIZE - 5);
        font.draw(batch, Constants.AMMO + " :  " + level.getGigaGal().ammo, 0, Constants.HUD_SIZE - 20);
        TextureRegion region = Assets.instance.gigaGalAssets.standingright;

        Vector2 initpos = new Vector2(Constants.HUD_SIZE + 45, Constants.HUD_SIZE - 40);
        for(int i = 0; i < lives; i++){
            batch.draw(region.getTexture(),
                    initpos.x,
                    initpos.y,
                    0,
                    0,
                    region.getRegionWidth(),
                    region.getRegionHeight(),
                    1f,
                    1f,
                    0,
                    region.getRegionX(),
                    region.getRegionY(),
                    region.getRegionWidth(),
                    region.getRegionHeight(),
                    false,
                    false);

            initpos.x -= Constants.SPACING_BETWEEN_LIVES;
        }
    }
}
