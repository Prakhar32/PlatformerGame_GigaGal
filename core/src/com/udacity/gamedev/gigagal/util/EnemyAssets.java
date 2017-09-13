package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by hp on 09-Sep-17.
 */

public class EnemyAssets{

    public TextureRegion emeny_sprite;
    public EnemyAssets(TextureAtlas atlas){
        emeny_sprite = atlas.findRegion(Constants.ENEMY_NAME);
    }
}
