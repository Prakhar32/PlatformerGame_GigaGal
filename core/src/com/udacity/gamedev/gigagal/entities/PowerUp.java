package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 16-Sep-17.
 */

public class PowerUp {
    public TextureRegion region;
    public Vector2 position;
    public int bulletCount;

    public PowerUp(Vector2 position){
        region = Assets.instance.powerUpAssets.powerup;
        this.position = new Vector2(position.x, position.y);
        bulletCount = Constants.BULLET_PER_POWERUP;
    }

    public void render(SpriteBatch batch){
        batch.draw(region.getTexture(),
                position.x,
                position.y,
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
                false);
    }

    public void BulletFired(){
        bulletCount--;
    }
}
