package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 16-Sep-17.
 */

public class Bullet {
    public TextureRegion region;
    public Vector2 position;
    public float velocity;
    boolean isFacingLeft;

    public Bullet(float left, float top, boolean isFacingLeft){
        region = Assets.instance.bulletAssets.region;
        position = new Vector2(left, top);
        this.isFacingLeft = isFacingLeft;
    }

    public void render(SpriteBatch batch){
        update();
        batch.draw(region.getTexture(),
                position.x,
                position.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                0.5f,
                0.5f,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                isFacingLeft,
                false);
    }

    public  void update(){
        position.x += velocity * Gdx.graphics.getDeltaTime();
    }
}
