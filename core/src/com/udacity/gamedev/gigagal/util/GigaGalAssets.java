package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GigaGalAssets{

    public final TextureRegion standingright;
    public final Array<TextureRegion> gigaGalSprites;
    private Array<TextureRegion> copier = new Array<TextureRegion>();

    public GigaGalAssets(TextureAtlas atlas){
        standingright = atlas.findRegion(Constants.STANDING_RIGHT);

        copier.add(standingright);
        copier.add(atlas.findRegion(Constants.JUMP_RIGHT));
        copier.add(atlas.findRegion(Constants.STANDING_LEFT));
        copier.add(atlas.findRegion(Constants.JUMP_LEFT));

        copier.add(atlas.findRegion(Constants.WALK_1_RIGHT));
        copier.add(atlas.findRegion(Constants.WALK_2_RIGHT));
        copier.add(atlas.findRegion(Constants.WALK_3_RIGHT));

        copier.add(atlas.findRegion(Constants.WALK_1_LEFT));
        copier.add(atlas.findRegion(Constants.WALK_2_LEFT));
        copier.add(atlas.findRegion(Constants.WALK_3_LEFT));

        gigaGalSprites = copier;
    }
}
