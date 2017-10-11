package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by hp on 16-Sep-17.
 */

public class PowerUpAssets {
    public TextureRegion powerup;
    PowerUpAssets(TextureAtlas atlas){
        powerup = atlas.findRegion(Constants.POWERUP);
    }
}
