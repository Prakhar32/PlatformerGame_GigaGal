package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;

/**
 * Created by hp on 05-Oct-17.
 */

public class ExitPortal {
    public TextureRegion region;
    public float left;
    public float bottom;
    Animation exit;
    float startTime;

    public ExitPortal(Vector2 platform){
        this.left = platform.x;
        this.bottom = platform.y;
        startTime = TimeUtils.nanoTime() / (float) 1e+9;
        exit = new Animation(0.1f, Assets.instance.exitPortalAssets.ExitPortalSprites, Animation.PlayMode.LOOP);
    }

    public void render(SpriteBatch batch){
        getRegion();
        batch.draw(region.getTexture(),
                left,
                bottom,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                0.7f,
                0.7f,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public void getRegion(){
        float currentTime = TimeUtils.nanoTime() / (float) 1e+9;
        float timeElasped = currentTime - startTime;
        region = (TextureRegion) exit.getKeyFrame(timeElasped);
    }
}
