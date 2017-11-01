package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by hp on 30-Oct-17.
 */

public class ControlAssets {
    public TextureRegion move_left_button;
    public TextureRegion move_right_button;
    public TextureRegion jump_button;
    public TextureRegion shoot_button;

    public ControlAssets(TextureAtlas atlas){
        move_left_button = atlas.findRegion(Constants.MOVE_LEFT_BUTTON);
        move_right_button = atlas.findRegion(Constants.MOVE_RIGHT_BUTTON);
        jump_button = atlas.findRegion(Constants.JUMP_BUTTON);
        shoot_button = atlas.findRegion(Constants.SHOOT_BUTTON);
    }
}
