package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by hp on 16-Sep-17.
 */

public class BulletAssets {
    public TextureRegion region;
    BulletAssets(TextureAtlas atlas){
        region = atlas.findRegion(Constants.BULLET);
    }
}
