package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;

/**
 * Created by hp on 16-Sep-17.
 */

public class ExplosionAssets {
    public DelayedRemovalArray<TextureRegion> explosions;
    public ExplosionAssets(TextureAtlas atlas){
        explosions = new DelayedRemovalArray<TextureRegion>();
        explosions.add(atlas.findRegion(Constants.EXPLOSION_SMALL));
        explosions.add(atlas.findRegion(Constants.EXPLOSION_MEDIUM));
        explosions.add(atlas.findRegion(Constants.EXPLOSION_LARGE));
    }
}
