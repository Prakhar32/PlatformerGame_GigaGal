package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 23-Aug-17.
 */

public class Platform implements Comparable<Platform>{
    public float width;
    public float length;
    public float bottom;
    public float left;
    NinePatch platform;
    boolean hasLandedOnPlatform;

    public Platform() {
        width = MathUtils.random(Constants.PLATFORM_MINIMUM_WIDTH, Constants.PLATFORM_MAXIMUM_WIDTH);
        length = MathUtils.random(Constants.PLATFORM_MINIMUM_LENGTH, Constants.PLATFORM_MAXIMUM_LENGTH);
        bottom = MathUtils.random(Constants.MINIMUM_PLATFORM_HEIGHT, Constants.MAXIMUM_PLATFORM_HEIGHT);
        left = MathUtils.random(Constants.MINIMUM_PLATFORM_LEFT, Constants.MAXIMUM_PLATFORM_LEFT);
        hasLandedOnPlatform = false;
        //width = 30;length = 30;bottom = 10;left = 10;
    }

    public void render(SpriteBatch batch){
        platform = Assets.instance.platformAssets.platformNinePatch;
        platform.draw(batch, left, bottom , width, length);
    }

    @Override
    public int compareTo(Platform platform) {
        if(this.bottom + this.length> platform.bottom + platform.length)
            return 1;
        else
            return  -1;
    }
}