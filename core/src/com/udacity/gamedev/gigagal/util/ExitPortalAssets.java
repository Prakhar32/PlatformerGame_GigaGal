package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.DelayedRemovalArray;

/**
 * Created by hp on 05-Oct-17.
 */

public class ExitPortalAssets {
    public DelayedRemovalArray <TextureRegion> ExitPortalSprites;
    ExitPortalAssets(TextureAtlas atlas){
        ExitPortalSprites = new DelayedRemovalArray<TextureRegion>();
        ExitPortalSprites.add(atlas.findRegion(Constants.EXIT_PORTAL_NAME_1));
        ExitPortalSprites.add(atlas.findRegion(Constants.EXIT_PORTAL_NAME_2));
        ExitPortalSprites.add(atlas.findRegion(Constants.EXIT_PORTAL_NAME_3));
        ExitPortalSprites.add(atlas.findRegion(Constants.EXIT_PORTAL_NAME_4));
        ExitPortalSprites.add(atlas.findRegion(Constants.EXIT_PORTAL_NAME_5));
        ExitPortalSprites.add(atlas.findRegion(Constants.EXIT_PORTAL_NAME_6));
    }
}
