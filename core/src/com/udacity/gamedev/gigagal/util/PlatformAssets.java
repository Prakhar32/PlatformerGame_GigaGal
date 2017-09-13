package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PlatformAssets{
    public final NinePatch platformNinePatch;
    public PlatformAssets(TextureAtlas atlas){
        TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_NAME);
        int edge = Constants.EDGE;
        platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
    }
}
