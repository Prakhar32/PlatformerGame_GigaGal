package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 17-Sep-17.
 */

public class Explosion {
    float left;
    float bottom;
    DelayedRemovalArray<TextureRegion> explosion_array;
    Animation explode;
    static TextureRegion region;
    boolean isEnemyDead;
    public static boolean deadHitLeft;
    float startTime;
    boolean animate;
    boolean setVisible;
    float startexplosionTime;

    Explosion(float left, float bottom, boolean isEnemyDead){
        this.left = left;
        this.bottom = bottom;
        explosion_array = Assets.instance.explosionAssets.explosions;
        explode = new Animation(0.2f, explosion_array, Animation.PlayMode.LOOP_PINGPONG);
        this.isEnemyDead = isEnemyDead;
        animate = false;
        setVisible = true;
        startexplosionTime = TimeUtils.nanoTime() / (float) 1e+9;
    }

    public void render(SpriteBatch batch){
        if(setVisible){
            if(TimeUtils.nanoTime() / (float) 1e+9 - startexplosionTime > Constants.EXPLOSION_TIME_DURATION)
                setVisible = false;
        }
        region = getExplosion();
        batch.draw(region.getTexture(),
                left,
                bottom,
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

    public TextureRegion getExplosion(){
        TextureRegion region = null;
        Gdx.app.log("Rendering", "Explosion");
        if(!isEnemyDead){
            region = explosion_array.get(0);animate = false;}
        else{
            region = EnemyDeadExplosion();animate = true;
        }
        return  region;
    }

    public TextureRegion EnemyDeadExplosion(){
        TextureRegion region = null;
        if(!animate){
            startTime = TimeUtils.nanoTime() / (float) 1e+9;animate = true;
        }
        float timeElasped = TimeUtils.nanoTime() / (float) 1e+9 - startTime;
        region = (TextureRegion) explode.getKeyFrame(timeElasped);
        return region;
    }
}
